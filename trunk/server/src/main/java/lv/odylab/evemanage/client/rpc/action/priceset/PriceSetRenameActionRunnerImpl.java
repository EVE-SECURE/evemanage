package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetRenameActionRunnerImpl implements PriceSetRenameActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetRenameActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetRenameActionResponse execute(PriceSetRenameAction action) throws Exception {
        Long priceSetID = action.getPriceSetID();
        clientFacade.renamePriceSet(priceSetID, action.getPriceSetName());
        PriceSetDto priceSet = clientFacade.getPriceSet(priceSetID);
        List<PriceSetNameDto> priceSetNames = clientFacade.getPriceSetNames();
        Integer currentPriceSetNameIndex = getPriceSetIndexPosition(priceSetNames, priceSet.getName());

        PriceSetRenameActionResponse response = new PriceSetRenameActionResponse();
        response.setPriceSetNames(priceSetNames);
        response.setPriceSet(priceSet);
        response.setCurrentPriceSetNameIndex(currentPriceSetNameIndex);
        return response;
    }

    private Integer getPriceSetIndexPosition(List<PriceSetNameDto> priceSetNames, String currentPriceSetName) {
        for (int i = 0; i < priceSetNames.size(); i++) {
            String priceSetName = priceSetNames.get(i).getPriceSetName();
            if (priceSetName.equals(currentPriceSetName)) {
                return i;
            }
        }
        throw new RuntimeException("Unable to find currentPriceSetName in priceSetNames");
    }
}
