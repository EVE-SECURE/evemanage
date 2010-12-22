package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSavePriceFetchConfigurationActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PreferencesSavedPriceFetchConfigurationEvent extends PreferencesTabEvent<PreferencesSavedPriceFetchConfigurationEventHandler> {
    public static final Type<PreferencesSavedPriceFetchConfigurationEventHandler> TYPE = new Type<PreferencesSavedPriceFetchConfigurationEventHandler>();

    public PreferencesSavedPriceFetchConfigurationEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesSavePriceFetchConfigurationActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);
    }

    @Override
    public Type<PreferencesSavedPriceFetchConfigurationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesSavedPriceFetchConfigurationEventHandler handler) {
        handler.onSavedPriceFetchConfiguration(this);

        trackEvent();
    }
}