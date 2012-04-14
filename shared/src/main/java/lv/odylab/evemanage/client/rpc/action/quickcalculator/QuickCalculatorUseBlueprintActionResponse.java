package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;

public class QuickCalculatorUseBlueprintActionResponse implements Response {
    private Long[] pathNodes;
    private String pathNodesString;
    private UsedBlueprintDto usedBlueprint;

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(Long[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    public String getPathNodesString() {
        return pathNodesString;
    }

    public void setPathNodesString(String pathNodesString) {
        this.pathNodesString = pathNodesString;
    }

    public UsedBlueprintDto getUsedBlueprint() {
        return usedBlueprint;
    }

    public void setUsedBlueprint(UsedBlueprintDto usedBlueprint) {
        this.usedBlueprint = usedBlueprint;
    }
}