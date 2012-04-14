package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesApiKeyDeletedEventHandler extends EventHandler {

    void onApiKeyDeleted(PreferencesApiKeyDeletedEvent event);

}