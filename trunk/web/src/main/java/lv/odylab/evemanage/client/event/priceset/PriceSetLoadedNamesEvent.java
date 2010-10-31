package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadNamesActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetLoadedNamesEvent extends PriceSetTabEvent<PriceSetLoadedNamesEventHandler> {
    public static final Type<PriceSetLoadedNamesEventHandler> TYPE = new Type<PriceSetLoadedNamesEventHandler>();

    private List<PriceSetNameDto> priceSetNames;

    public PriceSetLoadedNamesEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadNamesActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
    }

    @Override
    public Type<PriceSetLoadedNamesEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    @Override
    protected void dispatch(PriceSetLoadedNamesEventHandler handler) {
        handler.onPriceSetLoadedNames(this);

        trackEvent();
    }
}
