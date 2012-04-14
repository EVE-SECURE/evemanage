package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(QuickCalculatorUseBlueprintActionRunner.class)
public class QuickCalculatorUseBlueprintAction implements Action<QuickCalculatorUseBlueprintActionResponse> {
    private Long[] pathNodes;
    private String pathNodesString;
    private String blueprintName;

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

    public String getBlueprintName() {
        return blueprintName;
    }

    public void setBlueprintName(String blueprintName) {
        this.blueprintName = blueprintName;
    }
}
