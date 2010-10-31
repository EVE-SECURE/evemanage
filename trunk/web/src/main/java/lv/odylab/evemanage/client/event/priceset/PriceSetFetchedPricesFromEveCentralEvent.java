package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveCentralActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetFetchedPricesFromEveCentralEvent extends PriceSetTabEvent<PriceSetFetchedPricesFromEveCentralEventHandler> {
    public static final Type<PriceSetFetchedPricesFromEveCentralEventHandler> TYPE = new Type<PriceSetFetchedPricesFromEveCentralEventHandler>();

    private List<PriceSetItemDto> priceSetItems;

    public PriceSetFetchedPricesFromEveCentralEvent(TrackingManager trackingManager, EveManageConstants constants, PriceFetchFromEveCentralActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetItems = response.getPriceSetItems();
    }

    @Override
    public Type<PriceSetFetchedPricesFromEveCentralEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetFetchedPricesFromEveCentralEventHandler handler) {
        handler.onFetchedPricesFromEveCentral(this);

        trackEvent(String.valueOf(priceSetItems.size()));
    }

    public List<PriceSetItemDto> getPriceSetItems() {
        return priceSetItems;
    }
}