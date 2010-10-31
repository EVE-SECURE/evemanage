package lv.odylab.evemanage.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class TrackableGwtEvent<T extends EventHandler> extends GwtEvent<T> {
    private TrackingManager trackingManager;
    private EveManageConstants constants;
    private String category;
    private Long msDuration;
    private Long msCreation;

    protected TrackableGwtEvent(TrackingManager trackingManager, EveManageConstants constants, String category, Long msDuration) {
        this.trackingManager = trackingManager;
        this.constants = constants;
        this.category = category;
        this.msDuration = msDuration;
        this.msCreation = System.currentTimeMillis();
    }

    protected void trackEvent(String label) {
        // TODO Use getClass().getSimpleName() when GWT will support this (probably in 2.1)
        String className = getClass().getName();
        String shortClassName = className.substring(className.lastIndexOf(".") + 1, className.length());

        Long msAdditional = System.currentTimeMillis() - msCreation;
        trackingManager.trackEvent(constants.googleAnalyticsAccount(), category, shortClassName, label, msDuration.intValue() + msAdditional.intValue());
    }

    protected void trackEvent() {
        trackEvent(null);
    }
}
