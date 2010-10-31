package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class LoginErrorEvent extends ErrorEvent<LoginErrorEventHandler> {
    public static final Type<LoginErrorEventHandler> TYPE = new Type<LoginErrorEventHandler>();

    public LoginErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<LoginErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginErrorEventHandler handler) {
        handler.onLoginError(this);

        trackEvent(getErrorMessage());
    }
}