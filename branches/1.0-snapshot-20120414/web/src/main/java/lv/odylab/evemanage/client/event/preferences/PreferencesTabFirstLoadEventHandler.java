package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesTabFirstLoadEventHandler extends EventHandler {

    void onPreferencesTabFirstLoad(PreferencesTabFirstLoadEvent event);

}