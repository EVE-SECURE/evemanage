package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.Map;

public class QuickCalculatorFetchedPricesFromEveCentralEvent extends QuickCalculatorTabEvent<QuickCalculatorFetchedPricesFromEveCentralEventHandler> {
    public static final Type<QuickCalculatorFetchedPricesFromEveCentralEventHandler> TYPE = new Type<QuickCalculatorFetchedPricesFromEveCentralEventHandler>();

    private Map<Long, String> typeIdToPriceMap;

    public QuickCalculatorFetchedPricesFromEveCentralEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorFetchPricesFromEveCentralActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.typeIdToPriceMap = response.getTypeIdToPriceMap();
    }

    @Override
    public Type<QuickCalculatorFetchedPricesFromEveCentralEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Map<Long, String> getTypeIdToPriceMap() {
        return typeIdToPriceMap;
    }

    @Override
    protected void dispatch(QuickCalculatorFetchedPricesFromEveCentralEventHandler handler) {
        handler.onFetchedPricesFromEveCentral(this);

        trackEvent();
    }
}
