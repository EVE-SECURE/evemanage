package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintSaveActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintSavedEvent extends BlueprintsTabEvent<BlueprintSavedEventHandler> {
    public static final Type<BlueprintSavedEventHandler> TYPE = new Type<BlueprintSavedEventHandler>();

    private BlueprintDto blueprint;

    public BlueprintSavedEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintSaveActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprint = response.getBlueprint();
    }

    @Override
    public Type<BlueprintSavedEventHandler> getAssociatedType() {
        return TYPE;
    }

    public BlueprintDto getBlueprint() {
        return blueprint;
    }

    @Override
    protected void dispatch(BlueprintSavedEventHandler handler) {
        handler.onBlueprintSaved(this);

        trackEvent();
    }
}