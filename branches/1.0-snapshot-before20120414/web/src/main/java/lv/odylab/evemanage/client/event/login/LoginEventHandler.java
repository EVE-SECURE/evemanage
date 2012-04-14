package lv.odylab.evemanage.client.event.login;

import com.google.gwt.event.shared.EventHandler;

public interface LoginEventHandler extends EventHandler {

    void onLogin(LoginEvent event);

}
