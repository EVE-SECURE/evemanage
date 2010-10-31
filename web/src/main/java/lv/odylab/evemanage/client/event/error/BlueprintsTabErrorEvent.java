package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintsTabErrorEvent extends ErrorEvent<BlueprintsTabErrorEventHandler> {
    public static final Type<BlueprintsTabErrorEventHandler> TYPE = new Type<BlueprintsTabErrorEventHandler>();

    public BlueprintsTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<BlueprintsTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BlueprintsTabErrorEventHandler handler) {
        handler.onBlueprintsTabError(this);

        trackEvent(getErrorMessage());
    }
}