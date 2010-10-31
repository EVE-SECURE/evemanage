package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.Map;

public class QuickCalculatorFetchedPricesFromEveMetricsEvent extends QuickCalculatorTabEvent<QuickCalculatorFetchedPricesFromEveMetricsEventHandler> {
    public static final Type<QuickCalculatorFetchedPricesFromEveMetricsEventHandler> TYPE = new Type<QuickCalculatorFetchedPricesFromEveMetricsEventHandler>();

    private Map<Long, String> typeIdToPriceMap;

    public QuickCalculatorFetchedPricesFromEveMetricsEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorFetchPricesFromEveMetricsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.typeIdToPriceMap = response.getTypeIdToPriceMap();
    }

    @Override
    public Type<QuickCalculatorFetchedPricesFromEveMetricsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Map<Long, String> getTypeIdToPriceMap() {
        return typeIdToPriceMap;
    }

    @Override
    protected void dispatch(QuickCalculatorFetchedPricesFromEveMetricsEventHandler handler) {
        handler.onFetchedPricesFromEveMetrics(this);

        trackEvent();
    }
}
