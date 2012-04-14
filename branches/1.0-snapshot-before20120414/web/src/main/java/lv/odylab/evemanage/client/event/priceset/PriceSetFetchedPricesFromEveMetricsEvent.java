package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveMetricsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetFetchedPricesFromEveMetricsEvent extends PriceSetTabEvent<PriceSetFetchedPricesFromEveMetricsEventHandler> {
    public static final Type<PriceSetFetchedPricesFromEveMetricsEventHandler> TYPE = new Type<PriceSetFetchedPricesFromEveMetricsEventHandler>();

    private List<PriceSetItemDto> priceSetItems;

    public PriceSetFetchedPricesFromEveMetricsEvent(TrackingManager trackingManager, EveManageConstants constants, PriceFetchFromEveMetricsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetItems = response.getPriceSetItems();
    }

    @Override
    public Type<PriceSetFetchedPricesFromEveMetricsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetFetchedPricesFromEveMetricsEventHandler handler) {
        handler.onFetchedPricesFromEveMetrics(this);

        trackEvent(String.valueOf(priceSetItems.size()));
    }

    public List<PriceSetItemDto> getPriceSetItems() {
        return priceSetItems;
    }
}