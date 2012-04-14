package lv.odylab.evemanage.client.event.dashboard;

import com.google.gwt.event.shared.EventHandler;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.TrackableGwtEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class DashboardTabEvent<T extends EventHandler> extends TrackableGwtEvent<T> {
    protected DashboardTabEvent(TrackingManager trackingManager, EveManageConstants constants, Long msDuration) {
        super(trackingManager, constants, constants.dashboardToken(), msDuration);
    }
}