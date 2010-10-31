package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PricingProcessor {
    private EveCalculator calculator;

    @Inject
    public PricingProcessor(EveCalculator calculator) {
        this.calculator = calculator;
    }

    public PricingProcessorResult process(Long quantity, CalculationTree calculationTree) {
        List<String> rootNodePrices = new ArrayList<String>();
        Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceSetItemDto>();
        for (CalculationTreeNode node : calculationTree.getNodeMap().values()) {
            rootNodePrices.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, null, quantity, node));
        }
        PricingProcessorResult pricingProcessorResult = new PricingProcessorResult();
        pricingProcessorResult.setTotalPrice(calculator.multiply(quantity, calculator.sum(rootNodePrices)));
        pricingProcessorResult.setTypeIdToCalculationPriceSetItemMap(typeIdToCalculationPriceSetItemMap);
        return pricingProcessorResult;
    }


    public PricingProcessorResult process(Long quantity, CalculationTree calculationTree, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        List<String> rootNodePrices = new ArrayList<String>();
        Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceSetItemDto>();
        for (CalculationTreeNode node : calculationTree.getNodeMap().values()) {
            rootNodePrices.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity, node));
        }
        PricingProcessorResult pricingProcessorResult = new PricingProcessorResult();
        pricingProcessorResult.setTotalPrice(calculator.multiply(quantity, calculator.sum(rootNodePrices)));
        pricingProcessorResult.setTypeIdToCalculationPriceSetItemMap(typeIdToCalculationPriceSetItemMap);
        return pricingProcessorResult;
    }

    private String recursivelyApplyPrices(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, CalculationTreeNode calculationTreeNode) {
        if (calculationTreeNode.isExcludeChildNodesFromCalculation() || calculationTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, parentQuantity, calculationTreeNode);
        }

        CalculationItemDto calculationItem = calculationTreeNode.getCalculationItem();
        Long quantity = calculateQuantityForCalculationItem(calculationItem);
        calculationItem.setQuantity(quantity);
        calculationItem.setParentQuantity(parentQuantity);
        List<String> nodePrices = new ArrayList<String>();
        for (CalculationTreeNode node : calculationTreeNode.getNodeMap().values()) {
            String nodePrice = recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity * parentQuantity, node);
            nodePrices.add(nodePrice);
        }
        String nodeSummedPrice = calculator.sum(nodePrices);
        calculationItem.setPrice(nodeSummedPrice);
        String totalPrice = calculator.multiply(calculationItem.getQuantity(), nodeSummedPrice);
        calculationItem.setTotalPrice(totalPrice);
        String totalPriceForParent = calculator.multiply(parentQuantity * quantity, nodeSummedPrice);
        calculationItem.setTotalPriceForParent(totalPriceForParent);
        return totalPrice;
    }

    private String applyPricesForLastNode(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, CalculationTreeNode calculationTreeNode) {
        CalculationItemDto calculationItem = calculationTreeNode.getCalculationItem();
        CalculationPriceSetItemDto calculationPriceSetItem = getOrCreateCalculationPriceSetItem(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, calculationItem);
        Long quantity = calculateQuantityForCalculationItem(calculationItem);
        calculationItem.setQuantity(quantity);
        calculationItem.setParentQuantity(parentQuantity);
        Long priceSetItemQuantity = calculationPriceSetItem.getQuantity() + quantity * parentQuantity;
        calculationPriceSetItem.setQuantity(priceSetItemQuantity);
        calculationPriceSetItem.setTotalPrice(calculator.multiply(priceSetItemQuantity, calculationPriceSetItem.getPrice()));
        String totalPrice = calculator.multiply(quantity, calculationPriceSetItem.getPrice());
        String totalPriceForParent = calculator.multiply(parentQuantity * quantity, calculationPriceSetItem.getPrice());
        calculationItem.setPrice(calculationPriceSetItem.getPrice());
        calculationItem.setTotalPrice(totalPrice);
        calculationItem.setTotalPriceForParent(totalPriceForParent);
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
            calculationPriceSetItem.setTotalPrice("0.0");
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
