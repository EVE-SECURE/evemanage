package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

public class PriceSetLoadActionRunnerImpl implements PriceSetLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadActionResponse execute(PriceSetLoadAction action) throws Exception {
        PriceSetDto priceSetDto = clientFacade.getPriceSet(action.getPriceSetID());

        PriceSetLoadActionResponse response = new PriceSetLoadActionResponse();
        response.setPriceSet(priceSetDto);
        return response;
    }
}
