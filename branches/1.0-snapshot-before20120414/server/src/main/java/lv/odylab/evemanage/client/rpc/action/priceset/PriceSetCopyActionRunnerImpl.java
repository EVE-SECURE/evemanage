package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetCopyActionRunnerImpl implements PriceSetCopyActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetCopyActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetCopyActionResponse execute(PriceSetCopyAction action) throws Exception {
        PriceSetDto originalPriceSet = clientFacade.getPriceSet(action.getPriceSetID());
        PriceSetDto newPriceSet = clientFacade.createPriceSet(action.getPriceSetName());
        Long attachedCharacterID = null;
        CharacterNameDto attachedCharacterName = originalPriceSet.getAttachedCharacterName();
        if (attachedCharacterName != null) {
            attachedCharacterID = attachedCharacterName.getId();
        }
        clientFacade.savePriceSet(newPriceSet.getId(), originalPriceSet.getItems(), originalPriceSet.getSharingLevel(), attachedCharacterID);

        PriceSetDto priceSet = clientFacade.getPriceSet(newPriceSet.getId());
        List<PriceSetNameDto> priceSetNames = clientFacade.getPriceSetNames();
        Integer currentPriceSetNameIndex = getPriceSetIndexPosition(priceSetNames, priceSet.getName());

        PriceSetCopyActionResponse response = new PriceSetCopyActionResponse();
        response.setPriceSetNames(priceSetNames);
        response.setPriceSet(priceSet);
        response.setCurrentPriceSetNameIndex(currentPriceSetNameIndex);
        response.setOriginalPriceSetName(originalPriceSet.getName());
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
