package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetFetchedPricesFromEveCentralEventHandler extends EventHandler {

    void onFetchedPricesFromEveCentral(PriceSetFetchedPricesFromEveCentralEvent event);

}