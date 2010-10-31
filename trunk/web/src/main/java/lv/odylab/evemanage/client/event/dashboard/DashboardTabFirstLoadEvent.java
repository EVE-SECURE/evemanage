package lv.odylab.evemanage.client.event.dashboard;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class DashboardTabFirstLoadEvent extends DashboardTabEvent<DashboardTabFirstLoadEventHandler> {
    public static final Type<DashboardTabFirstLoadEventHandler> TYPE = new Type<DashboardTabFirstLoadEventHandler>();

    public DashboardTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, Long msDuration) {
        super(trackingManager, constants, msDuration);
    }

    @Override
    public Type<DashboardTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DashboardTabFirstLoadEventHandler handler) {
        handler.onDashboardTabFirstLoad(this);
    }
}