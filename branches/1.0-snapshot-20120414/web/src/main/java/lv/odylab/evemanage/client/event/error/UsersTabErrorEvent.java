package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class UsersTabErrorEvent extends ErrorEvent<UsersTabErrorEventHandler> {
    public static final Type<UsersTabErrorEventHandler> TYPE = new Type<UsersTabErrorEventHandler>();

    public UsersTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<UsersTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UsersTabErrorEventHandler handler) {
        handler.onUsersTabError(this);

        trackEvent(getErrorMessage());
    }
}