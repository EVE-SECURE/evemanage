package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PreferencesTabErrorEvent extends ErrorEvent<PreferencesTabErrorEventHandler> {
    public static final Type<PreferencesTabErrorEventHandler> TYPE = new Type<PreferencesTabErrorEventHandler>();

    public PreferencesTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<PreferencesTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesTabErrorEventHandler handler) {
        handler.onPreferencesTabError(this);

        trackEvent(getErrorMessage());
    }
}