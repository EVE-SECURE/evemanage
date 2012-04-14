package lv.odylab.evemanage.client.event.priceset;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAllianceNamesActionResponse;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PriceSetLoadedNamesForAllianceEvent extends PriceSetTabEvent<PriceSetLoadedNamesForAllianceEventHandler> {
    public static final Type<PriceSetLoadedNamesForAllianceEventHandler> TYPE = new Type<PriceSetLoadedNamesForAllianceEventHandler>();

    private List<PriceSetNameDto> priceSetNames;

    public PriceSetLoadedNamesForAllianceEvent(TrackingManager trackingManager, EveManageConstants constants, PriceSetLoadAllianceNamesActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.priceSetNames = response.getPriceSetNames();
    }

    @Override
    public Type<PriceSetLoadedNamesForAllianceEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    @Override
    protected void dispatch(PriceSetLoadedNamesForAllianceEventHandler handler) {
        handler.onPriceSetLoadedNamesForAlliance(this);

        trackEvent();
    }
}
