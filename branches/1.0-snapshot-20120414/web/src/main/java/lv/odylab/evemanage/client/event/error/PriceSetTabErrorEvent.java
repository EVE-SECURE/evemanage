package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetTabErrorEvent extends ErrorEvent<PriceSetTabErrorEventHandler> {
    public static final Type<PriceSetTabErrorEventHandler> TYPE = new Type<PriceSetTabErrorEventHandler>();

    public PriceSetTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<PriceSetTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetTabErrorEventHandler handler) {
        handler.onPriceSetTabError(this);

        trackEvent(getErrorMessage());
    }
}
