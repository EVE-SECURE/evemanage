package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

public class PriceSetLoadForAllianceActionRunnerImpl implements PriceSetLoadForAllianceActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadForAllianceActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadForAllianceActionResponse execute(PriceSetLoadForAllianceAction action) throws Exception {
        PriceSetDto priceSetDto = clientFacade.getAlliancePriceSet(action.getPriceSetID());

        PriceSetLoadForAllianceActionResponse response = new PriceSetLoadForAllianceActionResponse();
        response.setPriceSet(priceSetDto);
        return response;
    }
}
