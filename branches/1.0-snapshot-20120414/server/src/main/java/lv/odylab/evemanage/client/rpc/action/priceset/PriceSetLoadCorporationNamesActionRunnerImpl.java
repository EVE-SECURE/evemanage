package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetLoadCorporationNamesActionRunnerImpl implements PriceSetLoadCorporationNamesActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadCorporationNamesActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadCorporationNamesActionResponse execute(PriceSetLoadCorporationNamesAction action) throws Exception {
        List<PriceSetNameDto> priceSetNames = clientFacade.getCorporationPriceSetNames();

        PriceSetLoadCorporationNamesActionResponse response = new PriceSetLoadCorporationNamesActionResponse();
        response.setPriceSetNames(priceSetNames);
        return response;
    }
}
