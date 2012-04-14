package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;

public class BlueprintGetDetailsActionResponse implements Response {
    private Long blueprintID;
    private BlueprintDetailsDto details;

    public Long getBlueprintID() {
        return blueprintID;
    }

    public void setBlueprintID(Long blueprintID) {
        this.blueprintID = blueprintID;
    }

    public BlueprintDetailsDto getDetails() {
        return details;
    }

    public void setDetails(BlueprintDetailsDto details) {
        this.details = details;
    }
}