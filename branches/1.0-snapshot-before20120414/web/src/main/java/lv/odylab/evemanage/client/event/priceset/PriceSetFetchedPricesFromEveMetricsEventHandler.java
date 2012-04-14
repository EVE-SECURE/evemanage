package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetFetchedPricesFromEveMetricsEventHandler extends EventHandler {

    void onFetchedPricesFromEveMetrics(PriceSetFetchedPricesFromEveMetricsEvent event);

}