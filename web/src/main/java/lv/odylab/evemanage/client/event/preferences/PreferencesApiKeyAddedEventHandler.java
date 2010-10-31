package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesApiKeyAddedEventHandler extends EventHandler {

    void onApiKeyAdded(PreferencesApiKeyAddedEvent event);

}