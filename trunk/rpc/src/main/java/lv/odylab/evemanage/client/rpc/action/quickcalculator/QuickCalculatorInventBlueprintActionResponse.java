package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;

public class QuickCalculatorInventBlueprintActionResponse implements Response {
    private Long[] pathNodes;
    private InventedBlueprintDto inventedBlueprint;

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(Long[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    public InventedBlueprintDto getInventedBlueprint() {
        return inventedBlueprint;
    }

    public void setInventedBlueprint(InventedBlueprintDto inventedBlueprint) {
        this.inventedBlueprint = inventedBlueprint;
    }
}