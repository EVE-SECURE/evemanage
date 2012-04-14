package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetManyItemsAddedEvent extends PriceSetTabEvent<PriceSetManyItemsAddedEventHandler> {
    public static final Type<PriceSetManyItemsAddedEventHandler> TYPE = new Type<PriceSetManyItemsAddedEventHandler>();

    private List<PriceSetItemDto> priceSetItems;

    public PriceSetManyItemsAddedEvent(TrackingManager trackingManager, EveManageConstants constants, List<PriceSetItemDto> priceSetItems, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetItems = priceSetItems;
    }

    @Override
    public Type<PriceSetManyItemsAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetManyItemsAddedEventHandler handler) {
        handler.onPriceSetManyItemsAdded(this);

        trackEvent(String.valueOf(priceSetItems.size()));
    }

    public List<PriceSetItemDto> getPriceSetItems() {
        return priceSetItems;
    }
}