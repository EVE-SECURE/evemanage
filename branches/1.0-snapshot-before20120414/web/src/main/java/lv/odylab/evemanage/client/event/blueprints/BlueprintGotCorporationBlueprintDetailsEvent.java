package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintGetDetailsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintGotCorporationBlueprintDetailsEvent extends BlueprintsTabEvent<BlueprintGotCorporationBlueprintDetailsEventHandler> {
    public static final Type<BlueprintGotCorporationBlueprintDetailsEventHandler> TYPE = new Type<BlueprintGotCorporationBlueprintDetailsEventHandler>();

    private Long blueprintID;
    private BlueprintDetailsDto details;

    public BlueprintGotCorporationBlueprintDetailsEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintGetDetailsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprintID = response.getBlueprintID();
        this.details = response.getDetails();
    }

    @Override
    public Type<BlueprintGotCorporationBlueprintDetailsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getBlueprintID() {
        return blueprintID;
    }

    public BlueprintDetailsDto getDetails() {
        return details;
    }

    @Override
    protected void dispatch(BlueprintGotCorporationBlueprintDetailsEventHandler handler) {
        handler.onGotCorporationBlueprintDetails(this);

        trackEvent();
    }
}