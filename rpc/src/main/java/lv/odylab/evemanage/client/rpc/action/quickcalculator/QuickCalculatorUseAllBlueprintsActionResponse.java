package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;

import java.util.Map;

public class QuickCalculatorUseAllBlueprintsActionResponse implements Response {
    private Map<Long[], CalculationDto> pathNodesToCalculationMap;

    public Map<Long[], CalculationDto> getPathNodesToCalculationMap() {
        return pathNodesToCalculationMap;
    }

    public void setPathNodesToCalculationMap(Map<Long[], CalculationDto> pathNodesToCalculationMap) {
        this.pathNodesToCalculationMap = pathNodesToCalculationMap;
    }
}