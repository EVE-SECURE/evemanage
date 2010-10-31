package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetLoadNamesActionRunnerImpl implements PriceSetLoadNamesActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadNamesActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadNamesActionResponse execute(PriceSetLoadNamesAction action) throws Exception {
        List<PriceSetNameDto> priceSetNames = clientFacade.getPriceSetNames();

        PriceSetLoadNamesActionResponse response = new PriceSetLoadNamesActionResponse();
        response.setPriceSetNames(priceSetNames);
        return response;
    }
}
