package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.TrackableGwtEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class PreferencesTabEvent<T extends EventHandler> extends TrackableGwtEvent<T> {
    protected PreferencesTabEvent(TrackingManager trackingManager, EveManageConstants constants, Long msDuration) {
        super(trackingManager, constants, constants.preferencesToken(), msDuration);
    }
}