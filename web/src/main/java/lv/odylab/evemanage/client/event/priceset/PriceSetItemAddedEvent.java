package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetAddItemActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetItemAddedEvent extends PriceSetTabEvent<PriceSetItemAddedEventHandler> {
    public static final Type<PriceSetItemAddedEventHandler> TYPE = new Type<PriceSetItemAddedEventHandler>();

    private PriceSetItemDto priceSetItem;

    public PriceSetItemAddedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetAddItemActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetItem = response.getPriceSetItem();
    }

    @Override
    public Type<PriceSetItemAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetItemAddedEventHandler handler) {
        handler.onPriceSetItemAdded(this);

        trackEvent(priceSetItem.getItemTypeName());
    }

    public PriceSetItemDto getPriceSetItem() {
        return priceSetItem;
    }
}
