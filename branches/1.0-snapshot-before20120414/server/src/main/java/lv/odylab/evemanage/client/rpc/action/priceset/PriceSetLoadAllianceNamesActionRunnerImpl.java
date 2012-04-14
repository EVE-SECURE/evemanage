package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetLoadAllianceNamesActionRunnerImpl implements PriceSetLoadAllianceNamesActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetLoadAllianceNamesActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetLoadAllianceNamesActionResponse execute(PriceSetLoadAllianceNamesAction action) throws Exception {
        List<PriceSetNameDto> priceSetNames = clientFacade.getAlliancePriceSetNames();

        PriceSetLoadAllianceNamesActionResponse response = new PriceSetLoadAllianceNamesActionResponse();
        response.setPriceSetNames(priceSetNames);
        return response;
    }
}
