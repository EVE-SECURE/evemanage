package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetDeletedEventHandler extends EventHandler {

    void onPriceSetDeleted(PriceSetDeletedEvent event);

}