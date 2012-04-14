package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;

public class QuickCalculatorUseSchematicActionResponse implements Response {
    private Long[] pathNodes;
    private UsedSchematicDto usedSchematic;

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(Long[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    public UsedSchematicDto getUsedSchematic() {
        return usedSchematic;
    }

    public void setUsedSchematic(UsedSchematicDto usedSchematic) {
        this.usedSchematic = usedSchematic;
    }
}