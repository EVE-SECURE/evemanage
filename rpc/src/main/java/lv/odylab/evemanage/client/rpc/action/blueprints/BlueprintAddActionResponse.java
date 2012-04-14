package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

public class BlueprintAddActionResponse implements Response {
    private BlueprintDto blueprint;

    public BlueprintDto getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(BlueprintDto blueprint) {
        this.blueprint = blueprint;
    }
}