package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;

import java.util.Map;

public class QuickCalculatorUseAllBlueprintsActionResponse implements Response {
    private Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap;

    public Map<Long[], UsedBlueprintDto> getPathNodesToUsedBlueprintMap() {
        return pathNodesToUsedBlueprintMap;
    }

    public void setPathNodesToUsedBlueprintMap(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap) {
        this.pathNodesToUsedBlueprintMap = pathNodesToUsedBlueprintMap;
    }
}