package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintAddActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintAddedEvent extends BlueprintsTabEvent<BlueprintAddedEventHandler> {
    public static final Type<BlueprintAddedEventHandler> TYPE = new Type<BlueprintAddedEventHandler>();
    private BlueprintDto blueprint;

    public BlueprintAddedEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintAddActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprint = response.getBlueprint();
    }

    @Override
    public Type<BlueprintAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    public BlueprintDto getBlueprint() {
        return blueprint;
    }

    @Override
    protected void dispatch(BlueprintAddedEventHandler handler) {
        handler.onBlueprintAdded(this);

        trackEvent(blueprint.getItemTypeName());
    }
}