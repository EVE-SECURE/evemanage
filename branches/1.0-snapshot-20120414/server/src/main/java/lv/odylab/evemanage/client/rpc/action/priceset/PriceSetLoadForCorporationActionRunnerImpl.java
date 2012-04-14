package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

public class PriceSetLoadForCorporationActionRunnerImpl implements PriceSetLoadForCorporationActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadForCorporationActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadForCorporationActionResponse execute(PriceSetLoadForCorporationAction action) throws Exception {
        PriceSetDto priceSetDto = clientFacade.getCorporationPriceSet(action.getPriceSetID());

        PriceSetLoadForCorporationActionResponse response = new PriceSetLoadForCorporationActionResponse();
        response.setPriceSet(priceSetDto);
        return response;
    }
}
