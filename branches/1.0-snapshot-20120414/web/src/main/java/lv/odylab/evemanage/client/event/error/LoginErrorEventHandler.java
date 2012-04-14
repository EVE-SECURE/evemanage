package lv.odylab.evemanage.client.event.error;

import com.google.gwt.event.shared.EventHandler;

public interface LoginErrorEventHandler extends EventHandler {

    void onLoginError(LoginErrorEvent event);

}