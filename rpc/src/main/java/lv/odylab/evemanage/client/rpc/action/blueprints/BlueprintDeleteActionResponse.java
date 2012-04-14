package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;

public class BlueprintDeleteActionResponse implements Response {
    private Long blueprintID;

    public Long getBlueprintID() {
        return blueprintID;
    }

    public void setBlueprintID(Long blueprintID) {
        this.blueprintID = blueprintID;
    }
}