package lv.odylab.evemanage.client.event.error;

import com.google.gwt.event.shared.EventHandler;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.TrackableGwtEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class ErrorEvent<T extends EventHandler> extends TrackableGwtEvent<T> {
    private String errorMessage;

    protected ErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, constants.errorToken(), 0L);

        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}