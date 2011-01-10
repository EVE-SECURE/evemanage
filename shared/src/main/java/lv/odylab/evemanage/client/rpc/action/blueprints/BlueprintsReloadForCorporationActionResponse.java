package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

import java.util.List;

public class BlueprintsReloadForCorporationActionResponse implements Response {
    private List<BlueprintDto> blueprints;

    public List<BlueprintDto> getBlueprints() {
        return blueprints;
    }

    public void setBlueprints(List<BlueprintDto> blueprints) {
        this.blueprints = blueprints;
    }
}