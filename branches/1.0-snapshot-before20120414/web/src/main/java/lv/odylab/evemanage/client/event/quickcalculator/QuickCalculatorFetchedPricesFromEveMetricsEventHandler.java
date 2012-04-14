package lv.odylab.evemanage.client.event.quickcalculator;

import com.google.gwt.event.shared.EventHandler;

public interface QuickCalculatorFetchedPricesFromEveMetricsEventHandler extends EventHandler {

    void onFetchedPricesFromEveMetrics(QuickCalculatorFetchedPricesFromEveMetricsEvent event);

}
