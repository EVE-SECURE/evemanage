package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsImportActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintsImportedEvent extends BlueprintsTabEvent<BlueprintsImportedEventHandler> {
    public static final Type<BlueprintsImportedEventHandler> TYPE = new Type<BlueprintsImportedEventHandler>();

    public BlueprintsImportedEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintsImportActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);
    }

    @Override
    public Type<BlueprintsImportedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BlueprintsImportedEventHandler handler) {
        handler.onBlueprintsImported(this);

        trackEvent();
    }
}