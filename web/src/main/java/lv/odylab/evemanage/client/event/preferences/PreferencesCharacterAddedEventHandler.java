package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesCharacterAddedEventHandler extends EventHandler {

    void onCharacterAdded(PreferencesCharacterAddedEvent event);

}