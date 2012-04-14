package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.BlueprintItemTreeProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.BlueprintItemTreeProcessorResult;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.CalculationItemTreeProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.processor.CalculationItemTreeProcessorResult;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;

import java.math.BigDecimal;
import java.util.Map;

public class CalculationProcessor {
    private final CalculationItemTreeProcessor calculationItemTreeProcessor;
    private final BlueprintItemTreeProcessor blueprintItemTreeProcessor;

    @Inject
    public CalculationProcessor(CalculationItemTreeProcessor calculationItemTreeProcessor, BlueprintItemTreeProcessor blueprintItemTreeProcessor) {
        this.calculationItemTreeProcessor = calculationItemTreeProcessor;
        this.blueprintItemTreeProcessor = blueprintItemTreeProcessor;
    }

    public CalculationProcessorResult process(Long quantity, CalculationItemTree calculationItemTree, BlueprintItemTree blueprintItemTree, Map<Long, CalculationPriceItemDto> existingTypeIdToCalculationPriceSetItemMap, Map<Long, Integer> typeIdToSkillLevelMap) {
        CalculationItemTreeProcessorResult calculationItemTreeProcessorResult = calculationItemTreeProcessor.process(quantity, calculationItemTree, blueprintItemTree, existingTypeIdToCalculationPriceSetItemMap, typeIdToSkillLevelMap);
        BlueprintItemTreeProcessorResult blueprintItemTreeProcessorResult = blueprintItemTreeProcessor.process(quantity, calculationItemTree, typeIdToSkillLevelMap, blueprintItemTree);

        BigDecimal pricePerUnit = calculationItemTreeProcessorResult.getPricePerUnit().add(blueprintItemTreeProcessorResult.getPricePerUnit());
        BigDecimal totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
        CalculationProcessorResult calculationProcessorResult = new CalculationProcessorResult();
        calculationProcessorResult.setTotalPrice(totalPrice);
        calculationProcessorResult.setPricePerUnit(pricePerUnit);
        calculationProcessorResult.setTypeIdToCalculationPriceSetItemMap(calculationItemTreeProcessorResult.getTypeIdToCalculationPriceSetItemMap());
        return calculationProcessorResult;
    }
}
