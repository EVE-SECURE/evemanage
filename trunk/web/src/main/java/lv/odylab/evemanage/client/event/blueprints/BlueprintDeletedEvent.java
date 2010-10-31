package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintDeleteActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintDeletedEvent extends BlueprintsTabEvent<BlueprintDeletedEventHandler> {
    public static final Type<BlueprintDeletedEventHandler> TYPE = new Type<BlueprintDeletedEventHandler>();

    private Long blueprintID;

    public BlueprintDeletedEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintDeleteActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprintID = response.getBlueprintID();
    }

    @Override
    public Type<BlueprintDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long getBlueprintID() {
        return blueprintID;
    }

    @Override
    protected void dispatch(BlueprintDeletedEventHandler handler) {
        handler.onBlueprintDeleted(this);

        trackEvent();
    }
}