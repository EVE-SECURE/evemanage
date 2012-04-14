package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetLoadedEventHandler extends EventHandler {

    void onPriceSetLoaded(PriceSetLoadedEvent event);

}
