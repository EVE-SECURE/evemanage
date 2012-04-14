package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadCorporationNamesActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetLoadedNamesForCorporationEvent extends PriceSetTabEvent<PriceSetLoadedNamesForCorporationEventHandler> {
    public static final Type<PriceSetLoadedNamesForCorporationEventHandler> TYPE = new Type<PriceSetLoadedNamesForCorporationEventHandler>();

    private List<PriceSetNameDto> priceSetNames;

    public PriceSetLoadedNamesForCorporationEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadCorporationNamesActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
    }

    @Override
    public Type<PriceSetLoadedNamesForCorporationEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    @Override
    protected void dispatch(PriceSetLoadedNamesForCorporationEventHandler handler) {
        handler.onPriceSetLoadedNamesForCorporation(this);

        trackEvent();
    }
}
