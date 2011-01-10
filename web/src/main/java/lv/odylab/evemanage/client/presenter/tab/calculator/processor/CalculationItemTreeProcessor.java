package lv.odylab.evemanage.client.presenter.tab.calculator.processor;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNodeSummary;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.RationalNumber;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CalculationItemTreeProcessor {
    private final EveCalculator calculator;

    @Inject
    public CalculationItemTreeProcessor(EveCalculator calculator) {
        this.calculator = calculator;
    }

    public BigDecimal recursivelyApplyPrices(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, RationalNumber parentQuantityMultiplier, Integer productionEfficiencySkillLevel, CalculationItemTreeNode calculationItemTreeNode) {
        if (calculationItemTreeNode.isExcludeChildNodesFromCalculation() || calculationItemTreeNode.getNodeMap().size() == 0) {
            return applyPricesForLastNode(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, parentQuantity, parentQuantityMultiplier, productionEfficiencySkillLevel, calculationItemTreeNode);
        }

        BigDecimal resultingSum = BigDecimal.ZERO;
        List<CalculationItemDto> calculationItems = calculationItemTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            Long quantity = calculateQuantity(calculationItem, productionEfficiencySkillLevel);
            RationalNumber quantityMultiplier = calculationItem.getQuantityMultiplier();
            RationalNumber quantityAndParentQuantityMultiplierProduct = quantityMultiplier.multiply(parentQuantityMultiplier);
            BigDecimal nodePriceSum = BigDecimal.ZERO;
            for (CalculationItemTreeNode node : calculationItemTreeNode.getNodeMap().values()) {
                nodePriceSum = nodePriceSum.add(recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity * parentQuantity, quantityAndParentQuantityMultiplierProduct, productionEfficiencySkillLevel, node));
            }
            BigDecimal quantityAndDamagePerJobProduct = calculationItem.getDamagePerJob().multiply(BigDecimal.valueOf(quantity));
            BigDecimal priceAndQuantityAndDamagePerJobProduct = nodePriceSum.multiply(quantityAndDamagePerJobProduct);
            BigDecimal totalPrice = quantityMultiplier.multiplyDecimal(priceAndQuantityAndDamagePerJobProduct);
            BigDecimal totalPriceForParent = quantityAndParentQuantityMultiplierProduct.multiply(parentQuantity).multiplyDecimal(priceAndQuantityAndDamagePerJobProduct);

            calculationItem.setQuantity(quantity);
            calculationItem.setParentQuantity(parentQuantity);
            calculationItem.setParentQuantityMultiplier(parentQuantityMultiplier);
            calculationItem.setPrice(nodePriceSum);
            calculationItem.setTotalPrice(totalPrice);
            calculationItem.setTotalPriceForParent(totalPriceForParent);
            resultingSum = resultingSum.add(totalPrice);
        }
        if (calculationItems.size() > 1) {
            CalculationItemTreeNodeSummary calculationItemTreeNodeSummary = calculationItemTreeNode.getSummary();
            RationalNumber quantityMultiplier = calculationItemTreeNodeSummary.getQuantityMultiplier();
            RationalNumber quantityAndParentQuantityMultiplierProduct = quantityMultiplier.multiply(parentQuantityMultiplier);
            for (CalculationItemTreeNode node : calculationItemTreeNode.getNodeMap().values()) {
                recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, calculationItemTreeNodeSummary.getQuantity() * calculationItemTreeNodeSummary.getParentQuantity(), quantityAndParentQuantityMultiplierProduct, productionEfficiencySkillLevel, node);
            }
        }
        return resultingSum;
    }

    private BigDecimal applyPricesForLastNode(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Long parentQuantity, RationalNumber parentQuantityMultiplier, Integer productionEfficiencySkillLevel, CalculationItemTreeNode calculationItemTreeNode) {
        BigDecimal resultingSum = BigDecimal.ZERO;
        List<CalculationItemDto> calculationItems = calculationItemTreeNode.getCalculationItems();
        for (CalculationItemDto calculationItem : calculationItems) {
            CalculationPriceItemDto calculationPriceItem = getOrCreateCalculationPriceSetItem(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, calculationItem);
            BigDecimal price = calculationPriceItem.getPrice();
            Long quantity = calculateQuantity(calculationItem, productionEfficiencySkillLevel);
            RationalNumber quantityMultiplier = calculationItem.getQuantityMultiplier();
            RationalNumber quantityAndParentQuantityMultiplierProduct = quantityMultiplier.multiply(parentQuantityMultiplier);
            BigDecimal quantityAndDamagePerJobProduct = calculationItem.getDamagePerJob().multiply(BigDecimal.valueOf(quantity));
            BigDecimal priceAndQuantityAndDamagePerJobProduct = price.multiply(quantityAndDamagePerJobProduct);
            BigDecimal totalPrice = quantityMultiplier.multiplyDecimal(priceAndQuantityAndDamagePerJobProduct);
            BigDecimal totalPriceForParent = quantityAndParentQuantityMultiplierProduct.multiply(parentQuantity).multiplyDecimal(priceAndQuantityAndDamagePerJobProduct);

            calculationItem.setQuantity(quantity);
            calculationItem.setQuantityMultiplier(quantityMultiplier);
            calculationItem.setParentQuantity(parentQuantity);
            calculationItem.setParentQuantityMultiplier(parentQuantityMultiplier);
            calculationItem.setPrice(price);
            calculationItem.setTotalPrice(totalPrice);
            calculationItem.setTotalPriceForParent(totalPriceForParent);

            Long quantityAndParentQuantityProduct = quantity * parentQuantity;
            Long priceSetItemQuantity = calculationPriceItem.getQuantity() + quantityAndParentQuantityProduct;
            RationalNumber Z_multiplier = calculationPriceItem.getQuantityMultiplier();
            if (!parentQuantityMultiplier.equalOne() || !quantityMultiplier.equalOne()) {
                // Ax + By = (A + B)z
                // A = quantityAndParentQuantityMultiplierProduct
                // x = quantityAndParentQuantityProduct
                // B = calculationPriceItem.getQuantityMultiplier()
                // y = calculationPriceItem.getQuantity()
                // (A+B) = priceSetItemQuantity
                // z = A/(A+B)x + B/(A+B)y
                RationalNumber A_dividedBy_AplusB_times_X = quantityAndParentQuantityMultiplierProduct.multiply(quantityAndParentQuantityProduct).divide(priceSetItemQuantity);
                RationalNumber B_dividedBy_AplusB_times_Y = calculationPriceItem.getQuantityMultiplier().multiply(calculationPriceItem.getQuantity()).divide(priceSetItemQuantity);
                Z_multiplier = A_dividedBy_AplusB_times_X.add(B_dividedBy_AplusB_times_Y);
            }
            calculationPriceItem.setQuantity(priceSetItemQuantity);
            calculationPriceItem.setQuantityMultiplier(Z_multiplier);
            calculationPriceItem.setTotalPrice(Z_multiplier.multiply(priceSetItemQuantity).multiplyDecimal(price.multiply(calculationItem.getDamagePerJob())));
            resultingSum = resultingSum.add(totalPrice);
        }
        return resultingSum;
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
            calculationPriceItem.setQuantityMultiplier(new RationalNumber(1L));
            calculationPriceItem.setDamagePerJob(calculationItem.getDamagePerJob());
            calculationPriceItem.setTotalPrice(BigDecimal.ZERO);
            typeIdToCalculationPriceSetItemMap.put(calculationItem.getItemTypeID(), calculationPriceItem);
        }
        return calculationPriceItem;
    }

    private Long calculateQuantity(CalculationItemDto calculationItem, Integer productionEfficiencySkillLevel) {
        PathExpression pathExpression = calculationItem.getPathExpression();
        if (pathExpression.hasMeFactoring()) {
            return calculator.calculateMaterialAmount(calculationItem.getPerfectQuantity(), pathExpression.getMeLevel(), calculationItem.getWasteFactor(), productionEfficiencySkillLevel);
        } else {
            return calculationItem.getPerfectQuantity();
        }
    }
}
