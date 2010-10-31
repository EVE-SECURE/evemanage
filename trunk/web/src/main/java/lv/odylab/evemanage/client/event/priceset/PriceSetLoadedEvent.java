package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetLoadedEvent extends PriceSetTabEvent<PriceSetLoadedEventHandler> {
    public static final Type<PriceSetLoadedEventHandler> TYPE = new Type<PriceSetLoadedEventHandler>();

    private PriceSetDto priceSet;

    public PriceSetLoadedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSet = response.getPriceSet();
    }

    @Override
    public Type<PriceSetLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetLoadedEventHandler handler) {
        handler.onPriceSetLoaded(this);

        trackEvent();
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }
}
