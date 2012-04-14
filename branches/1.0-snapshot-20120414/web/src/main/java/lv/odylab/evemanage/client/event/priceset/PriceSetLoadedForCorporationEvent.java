package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForCorporationActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetLoadedForCorporationEvent extends PriceSetTabEvent<PriceSetLoadedForCorporationEventHandler> {
    public static final Type<PriceSetLoadedForCorporationEventHandler> TYPE = new Type<PriceSetLoadedForCorporationEventHandler>();

    private PriceSetDto priceSet;

    public PriceSetLoadedForCorporationEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadForCorporationActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSet = response.getPriceSet();
    }

    @Override
    public Type<PriceSetLoadedForCorporationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetLoadedForCorporationEventHandler handler) {
        handler.onPriceSetLoadedForCorporation(this);

        trackEvent();
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }
}
