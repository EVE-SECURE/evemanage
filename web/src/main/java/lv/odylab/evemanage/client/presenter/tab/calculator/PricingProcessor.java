package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PricingProcessor {
    private EveCalculator calculator;

    @Inject
    public PricingProcessor(EveCalculator calculator) {
        this.calculator = calculator;
    }

    public PricingProcessorResult process(Long quantity, CalculationTree calculationTree, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        BigDecimal rootNodePrice = BigDecimal.ZERO;
        Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceSetItemDto>();
        for (CalculationTreeNode node : calculationTree.getNodeMap().values()) {
            rootNodePrice = rootNodePrice.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity, node));
        }
        PricingProcessorResult pricingProcessorResult = new PricingProcessorResult();
        pricingProcessorResult.setTotalPrice(rootNodePrice.multiply(BigDecimal.valueOf(quantity)));
        pricingProcessorResult.setTypeIdToCalculationPriceSetItemMap(typeIdToCalculationPriceSetItemMap);
        return pricingProcessorResult;
    }

    private BigDecimal recursivelyApplyPrices(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, CalculationTreeNode calculationTreeNode) {
        if (calculationTreeNode.isExcludeChildNodesFromCalculation() || calculationTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, parentQuantity, calculationTreeNode);
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<CalculationItemDto> calculationItems = calculationTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            Long quantity = calculateQuantityForCalculationItem(calculationItem);
            calculationItem.setQuantity(quantity);
            calculationItem.setParentQuantity(parentQuantity);
            BigDecimal nodePrice = BigDecimal.ZERO;
            for (CalculationTreeNode node : calculationTreeNode.getNodeMap().values()) {
                nodePrice = nodePrice.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity * parentQuantity, node));
            }
            calculationItem.setPrice(nodePrice);
            totalPrice = totalPrice.add(nodePrice.multiply(BigDecimal.valueOf(calculationItem.getQuantity())).multiply(calculationItem.getDamagePerJob()));
            calculationItem.setTotalPrice(totalPrice);
            BigDecimal totalPriceForParent = totalPrice.multiply(BigDecimal.valueOf(parentQuantity));
            calculationItem.setTotalPriceForParent(totalPriceForParent);
        }
        return totalPrice;
    }

    private BigDecimal applyPricesForLastNode(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, CalculationTreeNode calculationTreeNode) {
        List<CalculationItemDto> calculationItems = calculationTreeNode.getCalculationItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CalculationItemDto calculationItem : calculationItems) {
            CalculationPriceSetItemDto calculationPriceSetItem = getOrCreateCalculationPriceSetItem(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, calculationItem);
            Long quantity = calculateQuantityForCalculationItem(calculationItem);
            calculationItem.setQuantity(quantity);
            calculationItem.setParentQuantity(parentQuantity);
            Long priceSetItemQuantity = calculationPriceSetItem.getQuantity() + quantity * parentQuantity;
            calculationPriceSetItem.setQuantity(priceSetItemQuantity);
            calculationPriceSetItem.setTotalPrice(calculationPriceSetItem.getPrice().multiply(BigDecimal.valueOf(priceSetItemQuantity)).multiply(calculationItem.getDamagePerJob()));
            totalPrice = totalPrice.add(calculationPriceSetItem.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(calculationItem.getDamagePerJob()));
            BigDecimal totalPriceForParent = calculationPriceSetItem.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(parentQuantity)).multiply(calculationItem.getDamagePerJob());
            calculationItem.setPrice(calculationPriceSetItem.getPrice());
            calculationItem.setTotalPrice(totalPrice);
            calculationItem.setTotalPriceForParent(totalPriceForParent);
        }
        return totalPrice;
    }

    private CalculationPriceSetItemDto getOrCreateCalculationPriceSetItem(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap, CalculationItemDto calculationItem) {
        CalculationPriceSetItemDto calculationPriceSetItem = typeIdToCalculationPriceSetItemMap.get(calculationItem.getItemTypeID());
        CalculationPriceSetItemDto existingCalculationPriceSetItem = null;
        if (existingTypeIdToCalculationPriceSetItemMap != null) {
            existingCalculationPriceSetItem = existingTypeIdToCalculationPriceSetItemMap.get(calculationItem.getItemTypeID());
        }
        if (calculationPriceSetItem == null) {
            calculationPriceSetItem = new CalculationPriceSetItemDto();
            calculationPriceSetItem.setItemTypeID(calculationItem.getItemTypeID());
            calculationPriceSetItem.setItemCategoryID(calculationItem.getItemCategoryID());
            calculationPriceSetItem.setItemTypeName(calculationItem.getItemTypeName());
            calculationPriceSetItem.setItemTypeIcon(calculationItem.getItemTypeIcon());
            if (existingCalculationPriceSetItem != null) {
                calculationPriceSetItem.setPrice(existingCalculationPriceSetItem.getPrice());
            } else {
                calculationPriceSetItem.setPrice(calculationItem.getPrice());
            }
            calculationPriceSetItem.setQuantity(0L);
            calculationPriceSetItem.setDamagePerJob(calculationItem.getDamagePerJob());
            calculationPriceSetItem.setTotalPrice(BigDecimal.ZERO);
            typeIdToCalculationPriceSetItemMap.put(calculationItem.getItemTypeID(), calculationPriceSetItem);
        }
        return calculationPriceSetItem;
    }

    private Long calculateQuantityForCalculationItem(CalculationItemDto calculationItem) {
        PathExpression pathExpression = calculationItem.getPathExpression();
        if (pathExpression.isMaterial()) {
            return calculator.calculateMaterialAmount(calculationItem.getPerfectQuantity(), pathExpression.getMeLevel(), calculationItem.getWasteFactor());
        } else {
            return calculationItem.getPerfectQuantity();
        }
    }
}
