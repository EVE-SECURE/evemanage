package lv.odylab.evemanage.client.event.blueprints;

import com.google.gwt.event.shared.EventHandler;

public interface BlueprintsReloadedForCorporationEventHandler extends EventHandler {

    void onBlueprintsReloadedForCorporation(BlueprintsReloadedForCorporationEvent event);

}