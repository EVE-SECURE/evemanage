package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class AsyncLoadingErrorEvent extends ErrorEvent<AsyncLoadingErrorEventHandler> {
    public static final Type<AsyncLoadingErrorEventHandler> TYPE = new Type<AsyncLoadingErrorEventHandler>();

    public AsyncLoadingErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<AsyncLoadingErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AsyncLoadingErrorEventHandler handler) {
        handler.onAsyncLoadingError(this);

        trackEvent(getErrorMessage());
    }
}