package lv.odylab.evemanage.client.event.blueprints;

import com.google.gwt.event.shared.EventHandler;

public interface BlueprintSavedEventHandler extends EventHandler {

    void onBlueprintSaved(BlueprintSavedEvent event);

}