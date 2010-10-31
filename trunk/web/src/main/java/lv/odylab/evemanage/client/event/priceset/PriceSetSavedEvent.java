package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetSaveActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PriceSetSavedEvent extends PriceSetTabEvent<PriceSetSavedEventHandler> {
    public static final Type<PriceSetSavedEventHandler> TYPE = new Type<PriceSetSavedEventHandler>();

    private PriceSetDto priceSet;

    public PriceSetSavedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetSaveActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSet = response.getPriceSet();
    }

    @Override
    public Type<PriceSetSavedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetSavedEventHandler handler) {
        handler.onPriceSetSaved(this);

        trackEvent(priceSet.getName());
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }
}