package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetSavedEventHandler extends EventHandler {

    void onPriceSetSaved(PriceSetSavedEvent event);

}