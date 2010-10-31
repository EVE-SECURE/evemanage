package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintGetDetailsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintGotBlueprintDetailsEvent extends BlueprintsTabEvent<BlueprintGotBlueprintDetailsEventHandler> {
    public static final Type<BlueprintGotBlueprintDetailsEventHandler> TYPE = new Type<BlueprintGotBlueprintDetailsEventHandler>();

    private Long blueprintID;
    private BlueprintDetailsDto details;

    public BlueprintGotBlueprintDetailsEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintGetDetailsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprintID = response.getBlueprintID();
        this.details = response.getDetails();
    }

    @Override
    public Type<BlueprintGotBlueprintDetailsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getBlueprintID() {
        return blueprintID;
    }

    public BlueprintDetailsDto getDetails() {
        return details;
    }

    @Override
    protected void dispatch(BlueprintGotBlueprintDetailsEventHandler handler) {
        handler.onGotBlueprintDetails(this);

        trackEvent();
    }
}