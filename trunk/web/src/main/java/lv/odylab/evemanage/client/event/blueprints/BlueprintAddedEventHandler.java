package lv.odylab.evemanage.client.event.blueprints;

import com.google.gwt.event.shared.EventHandler;

public interface BlueprintAddedEventHandler extends EventHandler {

    void onBlueprintAdded(BlueprintAddedEvent event);

}