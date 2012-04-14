package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class FooterErrorEvent extends ErrorEvent<FooterErrorEventHandler> {
    public static final Type<FooterErrorEventHandler> TYPE = new Type<FooterErrorEventHandler>();

    public FooterErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<FooterErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FooterErrorEventHandler handler) {
        handler.onFooterError(this);

        trackEvent(getErrorMessage());
    }
}