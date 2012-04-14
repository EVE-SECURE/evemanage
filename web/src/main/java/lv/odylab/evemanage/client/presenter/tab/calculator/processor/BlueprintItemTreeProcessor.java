package lv.odylab.evemanage.client.presenter.tab.calculator.processor;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNode;
import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.shared.EveCalculator;
import lv.odylab.evemanage.shared.RationalNumber;
import lv.odylab.evemanage.shared.eve.BlueprintUse;

import java.math.BigDecimal;
import java.util.Map;

public class BlueprintItemTreeProcessor {
    private final EveCalculator calculator;

    @Inject
    public BlueprintItemTreeProcessor(EveCalculator calculator) {
        this.calculator = calculator;
    }

    public BlueprintItemTreeProcessorResult process(Long quantity, CalculationItemTree calculationItemTree, Map<Long, Integer> typeIdToSkillLevelMap, BlueprintItemTree blueprintItemTree) {
        BigDecimal pricePerUnit = BigDecimal.ZERO;
        for (BlueprintItemTreeNode node : blueprintItemTree.getNodeMap().values()) {
            pricePerUnit = pricePerUnit.add(recursivelyApplyPrices(quantity, calculationItemTree, typeIdToSkillLevelMap, node));
        }

        BlueprintItemTreeProcessorResult blueprintItemTreeProcessorResult = new BlueprintItemTreeProcessorResult();
        blueprintItemTreeProcessorResult.setPricePerUnit(pricePerUnit);
        return blueprintItemTreeProcessorResult;
    }

    public BigDecimal recursivelyApplyPrices(Long parentQuantity, CalculationItemTree calculationItemTree, Map<Long, Integer> typeIdToSkillLevelMap, BlueprintItemTreeNode blueprintItemTreeNode) {
        if (blueprintItemTreeNode.isExcludeNodeCalculation()) {
            return BigDecimal.ZERO;
        }
        if (blueprintItemTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(parentQuantity, calculationItemTree, typeIdToSkillLevelMap, blueprintItemTreeNode);
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        BlueprintItemDto blueprintItem = blueprintItemTreeNode.getBlueprintItem();
        Long quantity = calculateQuantity(blueprintItem, parentQuantity);
        Integer maxProductionLimit = blueprintItem.getMaxProductionLimit();
        blueprintItem.setQuantity(quantity);
        blueprintItem.setParentQuantity(parentQuantity);
        blueprintItem.setRuns(Long.valueOf(maxProductionLimit));
        RationalNumber correctiveMultiplier = calculator.calculateBlueprintQuantityCorrectiveMultiplier(parentQuantity, quantity, blueprintItem.getMaxProductionLimit());
        blueprintItem.setCorrectiveMultiplier(correctiveMultiplier);
        BigDecimal nodePrice = BigDecimal.ZERO;
        for (BlueprintItemTreeNode node : blueprintItemTreeNode.getNodeMap().values()) {
            nodePrice = nodePrice.add(recursivelyApplyPrices(quantity * parentQuantity, calculationItemTree, typeIdToSkillLevelMap, node));
        }
        blueprintItem.setPrice(nodePrice);
        totalPrice = totalPrice.add(nodePrice.multiply(BigDecimal.valueOf(blueprintItem.getQuantity())));
        blueprintItem.setTotalPrice(totalPrice);
        BigDecimal totalPriceForParent = totalPrice.multiply(BigDecimal.valueOf(parentQuantity));
        blueprintItem.setTotalPriceForParent(totalPriceForParent);
        return totalPrice;
    }

    private BigDecimal applyPricesForLastNode(Long parentQuantity, CalculationItemTree calculationItemTree, Map<Long, Integer> typeIdToSkillLevelMap, BlueprintItemTreeNode blueprintItemTreeNode) {
        Long[] pathNodes = blueprintItemTreeNode.getBlueprintItem().getPathExpression().getPathNodes();
        CalculationItemTreeNode calculationItemTreeNode = calculationItemTree.getNodeByPathNodes(pathNodes);

        Long runsMultiplier = 1L;
        if (calculationItemTreeNode != null) {
            runsMultiplier = calculationItemTreeNode.getSummary().getQuantity();
        }
        BlueprintItemDto blueprintItem = blueprintItemTreeNode.getBlueprintItem();
        BlueprintUse blueprintUse = blueprintItem.getBlueprintUse();
        Long quantity = calculateQuantity(blueprintItem, parentQuantity * runsMultiplier);
        Integer maxProductionLimit = blueprintItem.getMaxProductionLimit();
        Long runs = calculateRuns(blueprintUse, parentQuantity * runsMultiplier, maxProductionLimit);
        blueprintItem.setQuantity(quantity);
        blueprintItem.setParentQuantity(parentQuantity);
        blueprintItem.setRuns(runs);
        BigDecimal price = calculateBlueprintPrice(blueprintItem.getBlueprintUse(), blueprintItem.getPrice());
        RationalNumber correctiveMultiplier = new RationalNumber(1L);
        if (maxProductionLimit != null) {
            correctiveMultiplier = calculator.calculateBlueprintQuantityCorrectiveMultiplier(parentQuantity * runsMultiplier, quantity, maxProductionLimit);
        }
        blueprintItem.setCorrectiveMultiplier(correctiveMultiplier);
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity)).multiply(correctiveMultiplier.evaluate());
        blueprintItem.setTotalPrice(totalPrice);
        blueprintItem.setTotalPriceForParent(totalPrice);
        return totalPrice;
    }

    private Long calculateQuantity(BlueprintItemDto blueprintItem, Long parentQuantity) {
        BlueprintUse blueprintUse = blueprintItem.getBlueprintUse();
        if (BlueprintUse.ORIGINAL.equals(blueprintUse)) {
            return 1L;
        } else if (BlueprintUse.COPY.equals(blueprintUse)) {
            return calculator.calculateBlueprintCopyQuantity(parentQuantity, blueprintItem.getMaxProductionLimit());
        }
        return blueprintItem.getQuantity() * parentQuantity;
    }

    private Long calculateRuns(BlueprintUse blueprintUse, Long parentQuantity, Integer maxProductionLimit) {
        if (BlueprintUse.ORIGINAL.equals(blueprintUse)) {
            return parentQuantity;
        } else if (BlueprintUse.COPY.equals(blueprintUse)) {
            return Long.valueOf(maxProductionLimit);
        }
        return null;
    }

    private BigDecimal calculateBlueprintPrice(BlueprintUse blueprintUse, BigDecimal price) {
        if (BlueprintUse.ORIGINAL.equals(blueprintUse)) {
            return BigDecimal.ZERO;
        } else {
            return price;
        }
    }
}
