package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.shared.CalculationExpression;

import java.util.Map;

public class QuickCalculatorDirectSetActionResponse implements Response {
    private CalculationExpression calculationExpression;
    private CalculationDto calculation;
    private Map<Long[], CalculationDto> pathNodesToCalculationMap;

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

    public Map<Long[], CalculationDto> getPathNodesToCalculationMap() {
        return pathNodesToCalculationMap;
    }

    public void setPathNodesToCalculationMap(Map<Long[], CalculationDto> pathNodesToCalculationMap) {
        this.pathNodesToCalculationMap = pathNodesToCalculationMap;
    }
}