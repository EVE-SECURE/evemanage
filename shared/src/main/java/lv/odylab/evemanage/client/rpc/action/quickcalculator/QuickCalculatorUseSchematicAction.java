package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(QuickCalculatorUseSchematicActionRunner.class)
public class QuickCalculatorUseSchematicAction implements Action<QuickCalculatorUseSchematicActionResponse> {
    private Long[] pathNodes;
    private String schematicName;

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(Long[] pathNodes) {
        this.pathNodes = pathNodes;
    }

    public String getSchematicName() {
        return schematicName;
    }

    public void setSchematicName(String schematicName) {
        this.schematicName = schematicName;
    }
}
