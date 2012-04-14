package lv.odylab.evemanage.client.event.blueprints;

import com.google.gwt.event.shared.EventHandler;

public interface BlueprintDeletedEventHandler extends EventHandler {

    void onBlueprintDeleted(BlueprintDeletedEvent event);

}