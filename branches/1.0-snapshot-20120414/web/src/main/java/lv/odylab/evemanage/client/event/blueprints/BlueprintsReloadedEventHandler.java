package lv.odylab.evemanage.client.event.blueprints;

import com.google.gwt.event.shared.EventHandler;

public interface BlueprintsReloadedEventHandler extends EventHandler {

    void onBlueprintsReloaded(BlueprintsReloadedEvent event);

}