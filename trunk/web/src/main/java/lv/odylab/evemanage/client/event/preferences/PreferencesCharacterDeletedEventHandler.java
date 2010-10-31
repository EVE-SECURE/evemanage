package lv.odylab.evemanage.client.event.preferences;

import com.google.gwt.event.shared.EventHandler;

public interface PreferencesCharacterDeletedEventHandler extends EventHandler {

    void onCharacterDeleted(PreferencesCharacterDeletedEvent event);

}