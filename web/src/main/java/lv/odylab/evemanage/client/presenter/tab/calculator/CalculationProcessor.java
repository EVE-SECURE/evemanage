package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.RationalNumberProductExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalculationProcessor {
    private EveCalculator calculator;

    @Inject
    public CalculationProcessor(EveCalculator calculator) {
        this.calculator = calculator;
    }

    public CalculationProcessorResult process(CalculationItemTree calculationItemTree, BlueprintItemTree blueprintItemTree) {
        BigDecimal rootNodePrice = BigDecimal.ZERO;
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceItemDto>();
        for (CalculationItemTreeNode node : calculationItemTree.getNodeMap().values()) {
            rootNodePrice = rootNodePrice.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, null, 1L, null, node));
        }
        CalculationProcessorResult calculationProcessorResult = new CalculationProcessorResult();
        calculationProcessorResult.setTotalPrice(rootNodePrice.multiply(BigDecimal.valueOf(1L)));
        calculationProcessorResult.setTypeIdToCalculationPriceSetItemMap(typeIdToCalculationPriceSetItemMap);
        return calculationProcessorResult;
    }

    private BigDecimal recursivelyApplyPrices(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, RationalNumberProductExpression parentQuantityMultiplier, CalculationItemTreeNode calculationItemTreeNode) {
        if (calculationItemTreeNode.isExcludeChildNodesFromCalculation() || calculationItemTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, parentQuantity, parentQuantityMultiplier, calculationItemTreeNode);
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<CalculationItemDto> calculationItems = calculationItemTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            Long quantity = calculateQuantityForCalculationItem(calculationItem);
            calculationItem.setQuantity(quantity);
            calculationItem.setParentQuantity(parentQuantity);
            calculationItem.setParentQuantityMultiplier(parentQuantityMultiplier);
            RationalNumberProductExpression quantityMultiplier = calculationItem.getQuantityMultiplier();
            BigDecimal nodePrice = BigDecimal.ZERO;
            for (CalculationItemTreeNode node : calculationItemTreeNode.getNodeMap().values()) {
                RationalNumberProductExpression quantityAndParentQuantityMultiplier = quantityMultiplier == null ? null : quantityMultiplier.multiply(parentQuantityMultiplier);
                nodePrice = nodePrice.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity * parentQuantity, quantityAndParentQuantityMultiplier, node));
            }
            calculationItem.setPrice(nodePrice);
            totalPrice = totalPrice.add(nodePrice.multiply(BigDecimal.valueOf(calculationItem.getQuantity())).multiply(calculationItem.getDamagePerJob()));
            calculationItem.setTotalPrice(totalPrice);
            BigDecimal totalPriceForParent = totalPrice.multiply(BigDecimal.valueOf(parentQuantity));
            calculationItem.setTotalPriceForParent(totalPriceForParent);
        }
        return totalPrice;
    }

    private BigDecimal applyPricesForLastNode(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, RationalNumberProductExpression parentQuantityMultiplier, CalculationItemTreeNode calculationItemTreeNode) {
        List<CalculationItemDto> calculationItems = calculationItemTreeNode.getCalculationItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CalculationItemDto calculationItem : calculationItems) {
            CalculationPriceItemDto calculationPriceItem = getOrCreateCalculationPriceSetItem(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, calculationItem);
            Long quantity = calculateQuantityForCalculationItem(calculationItem);
            calculationItem.setQuantity(quantity);
            calculationItem.setParentQuantity(parentQuantity);
            calculationItem.setParentQuantityMultiplier(parentQuantityMultiplier);
            Long priceSetItemQuantity = calculationPriceItem.getQuantity() + quantity * parentQuantity;
            calculationPriceItem.setQuantity(priceSetItemQuantity);
            calculationPriceItem.setTotalPrice(calculationPriceItem.getPrice().multiply(BigDecimal.valueOf(priceSetItemQuantity)).multiply(calculationItem.getDamagePerJob()));
            totalPrice = totalPrice.add(calculationPriceItem.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(calculationItem.getDamagePerJob()));
            BigDecimal totalPriceForParent = calculationPriceItem.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(parentQuantity)).multiply(calculationItem.getDamagePerJob());
            calculationItem.setPrice(calculationPriceItem.getPrice());
            calculationItem.setTotalPrice(totalPrice);
            calculationItem.setTotalPriceForParent(totalPriceForParent);
        }
        return totalPrice;
    }

    private CalculationPriceItemDto getOrCreateCalculationPriceSetItem(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, CalculationItemDto calculationItem) {
        CalculationPriceItemDto calculationPriceItem = typeIdToCalculationPriceSetItemMap.get(calculationItem.getItemTypeID());
        CalculationPriceItemDto existingCalculationPriceItem = null;
        if (existingTypeIdToCalculationPriceSetItemMap != null) {
            existingCalculationPriceItem = existingTypeIdToCalculationPriceSetItemMap.get(calculationItem.getItemTypeID());
        }
        if (calculationPriceItem == null) {
            calculationPriceItem = new CalculationPriceItemDto();
            calculationPriceItem.setItemTypeID(calculationItem.getItemTypeID());
            calculationPriceItem.setItemCategoryID(calculationItem.getItemCategoryID());
            calculationPriceItem.setItemTypeName(calculationItem.getItemTypeName());
            calculationPriceItem.setItemTypeIcon(calculationItem.getItemTypeIcon());
            if (existingCalculationPriceItem != null) {
                calculationPriceItem.setPrice(existingCalculationPriceItem.getPrice());
            } else {
                calculationPriceItem.setPrice(calculationItem.getPrice());
            }
            calculationPriceItem.setQuantity(0L);
            calculationPriceItem.setDamagePerJob(calculationItem.getDamagePerJob());
            calculationPriceItem.setTotalPrice(BigDecimal.ZERO);
            typeIdToCalculationPriceSetItemMap.put(calculationItem.getItemTypeID(), calculationPriceItem);
        }
        return calculationPriceItem;
    }

    private Long calculateQuantityForCalculationItem(CalculationItemDto calculationItem) {
        PathExpression pathExpression = calculationItem.getPathExpression();
        if (pathExpression.hasMeFactoring()) {
            return calculator.calculateMaterialAmount(calculationItem.getPerfectQuantity(), pathExpression.getMeLevel(), calculationItem.getWasteFactor());
        } else {
            return calculationItem.getPerfectQuantity();
        }
    }
}
