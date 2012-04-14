package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

import java.util.Map;

@RunnedBy(QuickCalculatorUseAllBlueprintsActionRunner.class)
public class QuickCalculatorUseAllBlueprintsAction implements Action<QuickCalculatorUseAllBlueprintsActionResponse> {
    private Map<Long[], String> pathNodesToBlueprintNameMap;

    public Map<Long[], String> getPathNodesToBlueprintNameMap() {
        return pathNodesToBlueprintNameMap;
    }

    public void setPathNodesToBlueprintNameMap(Map<Long[], String> pathNodesToBlueprintNameMap) {
        this.pathNodesToBlueprintNameMap = pathNodesToBlueprintNameMap;
    }
}
