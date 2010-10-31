package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetDeleteActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetDeletedEvent extends PriceSetTabEvent<PriceSetDeletedEventHandler> {
    public static final Type<PriceSetDeletedEventHandler> TYPE = new Type<PriceSetDeletedEventHandler>();

    private List<PriceSetNameDto> priceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;
    private String deletedPriceSetName;

    public PriceSetDeletedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetDeleteActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
        this.priceSet = response.getPriceSet();
        this.currentPriceSetNameIndex = response.getCurrentPriceSetNameIndex();
        this.deletedPriceSetName = response.getDeletedPriceSetName();
    }

    @Override
    public Type<PriceSetDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetDeletedEventHandler handler) {
        handler.onPriceSetDeleted(this);

        trackEvent(deletedPriceSetName);
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
