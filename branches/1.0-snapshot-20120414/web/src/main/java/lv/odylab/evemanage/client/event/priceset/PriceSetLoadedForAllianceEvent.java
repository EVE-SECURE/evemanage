package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForAllianceActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetLoadedForAllianceEvent extends PriceSetTabEvent<PriceSetLoadedForAllianceEventHandler> {
    public static final Type<PriceSetLoadedForAllianceEventHandler> TYPE = new Type<PriceSetLoadedForAllianceEventHandler>();

    private PriceSetDto priceSet;

    public PriceSetLoadedForAllianceEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadForAllianceActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSet = response.getPriceSet();
    }

    @Override
    public Type<PriceSetLoadedForAllianceEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetLoadedForAllianceEventHandler handler) {
        handler.onPriceSetLoadedForAlliance(this);

        trackEvent();
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }
}
