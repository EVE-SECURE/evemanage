package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class DashboardTabErrorEvent extends ErrorEvent<DashboardTabErrorEventHandler> {
    public static final Type<DashboardTabErrorEventHandler> TYPE = new Type<DashboardTabErrorEventHandler>();

    public DashboardTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<DashboardTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DashboardTabErrorEventHandler handler) {
        handler.onDashboardTabError(this);

        trackEvent(getErrorMessage());
    }
}