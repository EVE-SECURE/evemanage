package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(BlueprintGetDetailsActionRunner.class)
public class BlueprintGetDetailsAction implements Action<BlueprintGetDetailsActionResponse> {
    private Long blueprintID;
    private Long blueprintTypeID;

    public Long getBlueprintID() {
        return blueprintID;
    }

    public void setBlueprintID(Long blueprintID) {
        this.blueprintID = blueprintID;
    }

    public Long getBlueprintTypeID() {
        return blueprintTypeID;
    }

    public void setBlueprintTypeID(Long blueprintTypeID) {
        this.blueprintTypeID = blueprintTypeID;
    }
}