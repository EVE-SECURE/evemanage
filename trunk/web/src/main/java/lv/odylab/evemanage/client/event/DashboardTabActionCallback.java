package lv.odylab.evemanage.client.event;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.error.DashboardTabErrorEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class DashboardTabActionCallback<T> extends ActionCallback<T> {
    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageConstants constants;

    public DashboardTabActionCallback(EventBus eventBus, TrackingManager trackingManager, EveManageConstants constants) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.constants = constants;
    }

    @Override
    public void onFailure(Throwable throwable) {
        eventBus.fireEvent(new DashboardTabErrorEvent(trackingManager, constants, throwable.getMessage()));
    }
}