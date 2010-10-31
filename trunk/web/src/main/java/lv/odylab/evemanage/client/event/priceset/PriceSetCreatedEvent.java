package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCreateActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetCreatedEvent extends PriceSetTabEvent<PriceSetCreatedEventHandler> {
    public static final Type<PriceSetCreatedEventHandler> TYPE = new Type<PriceSetCreatedEventHandler>();

    private List<PriceSetNameDto> priceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;

    public PriceSetCreatedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetCreateActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
        this.priceSet = response.getPriceSet();
        this.currentPriceSetNameIndex = response.getCurrentPriceSetNameIndex();
    }

    @Override
    public Type<PriceSetCreatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetCreatedEventHandler handler) {
        handler.onPriceSetCreated(this);

        trackEvent(priceSet.getName());
    }

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public Integer getCurrentPriceSetNameIndex() {
        return currentPriceSetNameIndex;
    }
}
