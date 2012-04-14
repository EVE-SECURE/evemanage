package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.BlueprintItemTreeProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.CalculationItemTreeProcessor;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.shared.RationalNumber;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class CalculationProcessor {
    private final CalculationItemTreeProcessor calculationItemTreeProcessor;
    private final BlueprintItemTreeProcessor blueprintItemTreeProcessor;

    @Inject
    public CalculationProcessor(CalculationItemTreeProcessor calculationItemTreeProcessor, BlueprintItemTreeProcessor blueprintItemTreeProcessor) {
        this.calculationItemTreeProcessor = calculationItemTreeProcessor;
        this.blueprintItemTreeProcessor = blueprintItemTreeProcessor;
    }

    public CalculationProcessorResult process(Long quantity, CalculationItemTree calculationItemTree, BlueprintItemTree blueprintItemTree, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Map<Long, Integer> typeIdToSkillLevelMap) {
        BigDecimal rootNodePrice = BigDecimal.ZERO;
        Integer productionEfficiencySkillLevel = getProductionEfficiencySkillLevel(typeIdToSkillLevelMap);
        Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceItemDto>();
        for (CalculationItemTreeNode node : calculationItemTree.getNodeMap().values()) {
            rootNodePrice = rootNodePrice.add(calculationItemTreeProcessor.recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity, new RationalNumber(1L), productionEfficiencySkillLevel, node));
        }
        for (BlueprintItemTreeNode node : blueprintItemTree.getNodeMap().values()) {
            rootNodePrice = rootNodePrice.add(blueprintItemTreeProcessor.recursivelyApplyPrices(typeIdToCalculationPriceSetItemMap, existingTypeIdToCalculationPriceSetItemMap, quantity, typeIdToSkillLevelMap, node));
        }
        CalculationProcessorResult calculationProcessorResult = new CalculationProcessorResult();
        calculationProcessorResult.setTotalPrice(rootNodePrice.multiply(BigDecimal.valueOf(quantity)));
        calculationProcessorResult.setPricePerUnit(rootNodePrice);
        calculationProcessorResult.setTypeIdToCalculationPriceSetItemMap(typeIdToCalculationPriceSetItemMap);
        return calculationProcessorResult;
    }

    private Integer getProductionEfficiencySkillLevel(Map<Long, Integer> typeIdToSkillLevelMap) {
        Integer skillLevel = typeIdToSkillLevelMap.get(3388L); // Production Efficiency
        return skillLevel == null ? 5 : skillLevel;
    }
}
