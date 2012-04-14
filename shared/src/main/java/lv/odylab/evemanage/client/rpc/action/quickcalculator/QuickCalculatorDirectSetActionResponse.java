package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.shared.CalculationExpression;

import java.util.Map;

public class QuickCalculatorDirectSetActionResponse implements Response {
    private CalculationExpression calculationExpression;
    private CalculationDto calculation;
    private CalculationDto usedBlueprint;
    private Map<Long[], CalculationDto> pathNodesToCalculationMap;
    private Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap;

    public CalculationExpression getCalculationExpression() {
        return calculationExpression;
    }

    public void setCalculationExpression(CalculationExpression calculationExpression) {
        this.calculationExpression = calculationExpression;
    }

    public CalculationDto getCalculation() {
        return calculation;
    }

    public void setCalculation(CalculationDto calculation) {
        this.calculation = calculation;
    }

    public CalculationDto getUsedBlueprint() {
        return usedBlueprint;
    }

    public void setUsedBlueprint(CalculationDto usedBlueprint) {
        this.usedBlueprint = usedBlueprint;
    }

    public Map<Long[], CalculationDto> getPathNodesToCalculationMap() {
        return pathNodesToCalculationMap;
    }

    public void setPathNodesToCalculationMap(Map<Long[], CalculationDto> pathNodesToCalculationMap) {
        this.pathNodesToCalculationMap = pathNodesToCalculationMap;
    }

    public Map<Long[], UsedBlueprintDto> getPathNodesToUsedBlueprintMap() {
        return pathNodesToUsedBlueprintMap;
    }

    public void setPathNodesToUsedBlueprintMap(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap) {
        this.pathNodesToUsedBlueprintMap = pathNodesToUsedBlueprintMap;
    }
}