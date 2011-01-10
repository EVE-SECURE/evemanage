package lv.odylab.evemanage.client.presenter.tab.calculator.processor;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTreeNode;
import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
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

    public BigDecimal recursivelyApplyPrices(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, Map<Long, Integer> typeIdToSkillLevelMap, BlueprintItemTreeNode blueprintItemTreeNode) {
        if (blueprintItemTreeNode.isExcludeNodeCalculation() || blueprintItemTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, parentQuantity, typeIdToSkillLevelMap, blueprintItemTreeNode);
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
            nodePrice = nodePrice.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity * parentQuantity, typeIdToSkillLevelMap, node));
        }
        blueprintItem.setPrice(nodePrice);
        totalPrice = totalPrice.add(nodePrice.multiply(BigDecimal.valueOf(blueprintItem.getQuantity())));
        blueprintItem.setTotalPrice(totalPrice);
        BigDecimal totalPriceForParent = totalPrice.multiply(BigDecimal.valueOf(parentQuantity));
        blueprintItem.setTotalPriceForParent(totalPriceForParent);
        return totalPrice;
    }

    private BigDecimal applyPricesForLastNode(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, Map<Long, Integer> typeIdToSkillLevelMap, BlueprintItemTreeNode blueprintItemTreeNode) {
        BlueprintItemDto blueprintItem = blueprintItemTreeNode.getBlueprintItem();
        BlueprintUse blueprintUse = blueprintItem.getBlueprintUse();
        Long quantity = calculateQuantity(blueprintItem, parentQuantity);
        Integer maxProductionLimit = blueprintItem.getMaxProductionLimit();
        Long runs = calculateRuns(blueprintUse, parentQuantity, maxProductionLimit);
        blueprintItem.setQuantity(quantity);
        blueprintItem.setParentQuantity(parentQuantity);
        blueprintItem.setRuns(runs);
        BigDecimal price = calculateBlueprintPrice(blueprintItem.getBlueprintUse(), blueprintItem.getPrice());
        RationalNumber correctiveMultiplier = new RationalNumber(1L);
        if (maxProductionLimit != null) {
            correctiveMultiplier = calculator.calculateBlueprintQuantityCorrectiveMultiplier(parentQuantity, quantity, maxProductionLimit);
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
