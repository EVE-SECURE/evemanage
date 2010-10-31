package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetItemAddedEventHandler extends EventHandler {

    void onPriceSetItemAdded(PriceSetItemAddedEvent event);

}