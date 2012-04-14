package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetDeleteActionRunnerImpl implements PriceSetDeleteActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetDeleteActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetDeleteActionResponse execute(PriceSetDeleteAction action) throws Exception {
        PriceSetDto deletedPriceSet = clientFacade.getPriceSet(action.getPriceSetID());
        String deletedPriceSetName = deletedPriceSet.getName();

        clientFacade.deletePriceSet(action.getPriceSetID());
        List<PriceSetNameDto> priceSetNames = clientFacade.getPriceSetNames();
        Integer currentPriceSetNameIndex = 0;
        PriceSetDto priceSet = null;
        if (priceSetNames.size() > 0) {
            Long priceSetID = priceSetNames.get(currentPriceSetNameIndex).getPriceSetID();
            priceSet = clientFacade.getPriceSet(priceSetID);
        }

        PriceSetDeleteActionResponse response = new PriceSetDeleteActionResponse();
        response.setPriceSetNames(priceSetNames);
        response.setPriceSet(priceSet);
        response.setCurrentPriceSetNameIndex(currentPriceSetNameIndex);
        response.setDeletedPriceSetName(deletedPriceSetName);
        return response;
    }
}
