package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCopyActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetCopiedEvent extends PriceSetTabEvent<PriceSetCopiedEventHandler> {
    public static final Type<PriceSetCopiedEventHandler> TYPE = new Type<PriceSetCopiedEventHandler>();

    private List<PriceSetNameDto> priceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;
    private String originalPriceSetName;

    public PriceSetCopiedEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetCopyActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
        this.priceSet = response.getPriceSet();
        this.currentPriceSetNameIndex = response.getCurrentPriceSetNameIndex();
        this.originalPriceSetName = response.getOriginalPriceSetName();
    }

    @Override
    public Type<PriceSetCopiedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PriceSetCopiedEventHandler handler) {
        handler.onPriceSetCopied(this);

        trackEvent(originalPriceSetName);
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