package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

public class PriceSetSaveActionRunnerImpl implements PriceSetSaveActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetSaveActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetSaveActionResponse execute(PriceSetSaveAction action) throws Exception {
        PriceSetDto priceSet = action.getPriceSet();
        CharacterNameDto attachedCharacterName = priceSet.getAttachedCharacterName();
        Long attachedCharacterID = null;
        if (attachedCharacterName != null) {
            attachedCharacterID = attachedCharacterName.getId();
        }
        clientFacade.savePriceSet(priceSet.getId(), priceSet.getItems(), priceSet.getSharingLevel(), attachedCharacterID);

        PriceSetSaveActionResponse response = new PriceSetSaveActionResponse();
        response.setPriceSet(clientFacade.getPriceSet(priceSet.getId()));
        return response;
    }
}
