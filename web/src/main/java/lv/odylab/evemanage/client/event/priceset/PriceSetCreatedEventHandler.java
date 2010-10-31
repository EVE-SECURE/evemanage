package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetCreatedEventHandler extends EventHandler {

    void onPriceSetCreated(PriceSetCreatedEvent event);

}
