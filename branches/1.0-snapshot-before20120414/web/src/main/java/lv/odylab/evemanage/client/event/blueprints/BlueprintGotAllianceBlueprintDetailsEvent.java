package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintGetDetailsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintGotAllianceBlueprintDetailsEvent extends BlueprintsTabEvent<BlueprintGotAllianceBlueprintDetailsEventHandler> {
    public static final Type<BlueprintGotAllianceBlueprintDetailsEventHandler> TYPE = new Type<BlueprintGotAllianceBlueprintDetailsEventHandler>();

    private Long blueprintID;
    private BlueprintDetailsDto details;

    public BlueprintGotAllianceBlueprintDetailsEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintGetDetailsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprintID = response.getBlueprintID();
        this.details = response.getDetails();
    }

    @Override
    public Type<BlueprintGotAllianceBlueprintDetailsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getBlueprintID() {
        return blueprintID;
    }

    public BlueprintDetailsDto getDetails() {
        return details;
    }

    @Override
    protected void dispatch(BlueprintGotAllianceBlueprintDetailsEventHandler handler) {
        handler.onGotAllianceBlueprintDetails(this);

        trackEvent();
    }
}