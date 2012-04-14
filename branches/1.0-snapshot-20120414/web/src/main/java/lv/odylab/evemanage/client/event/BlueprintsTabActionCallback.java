package lv.odylab.evemanage.client.event;

import com.google.gwt.event.shared.EventBus;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.error.BlueprintsTabErrorEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class BlueprintsTabActionCallback<T> extends ActionCallback<T> {
    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageConstants constants;

    public BlueprintsTabActionCallback(EventBus eventBus, TrackingManager trackingManager, EveManageConstants constants) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.constants = constants;
    }

    @Override
    public void onFailure(Throwable throwable) {
        eventBus.fireEvent(new BlueprintsTabErrorEvent(trackingManager, constants, throwable.getMessage()));
    }
}