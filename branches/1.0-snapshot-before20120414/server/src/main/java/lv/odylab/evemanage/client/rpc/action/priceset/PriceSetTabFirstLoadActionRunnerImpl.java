package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.util.List;

public class PriceSetTabFirstLoadActionRunnerImpl implements PriceSetTabFirstLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetTabFirstLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetTabFirstLoadActionResponse execute(PriceSetTabFirstLoadAction action) throws Exception {
        List<PriceSetNameDto> priceSetNames = clientFacade.getPriceSetNames();
        List<PriceSetNameDto> corporationPriceSetNames = clientFacade.getCorporationPriceSetNames();
        List<PriceSetNameDto> alliancePriceSetNames = clientFacade.getAlliancePriceSetNames();
        Integer currentPriceSetNameIndex = 0;
        PriceSetDto priceSet = null;
        if (priceSetNames.size() > 0) {
            Long priceSetID = priceSetNames.get(currentPriceSetNameIndex).getPriceSetID();
            priceSet = clientFacade.getPriceSet(priceSetID);
        }
        List<CharacterNameDto> attachedCharacterNames = clientFacade.getAvailableAttachedCharacterNames();
        List<SharingLevel> sharingLevels = clientFacade.getAvailableSharingLevels();

        PriceSetTabFirstLoadActionResponse response = new PriceSetTabFirstLoadActionResponse();
        response.setPriceSetNames(priceSetNames);
        response.setCorporationPriceSetNames(corporationPriceSetNames);
        response.setAlliancePriceSetNames(alliancePriceSetNames);
        response.setPriceSet(priceSet);
        response.setCurrentPriceSetNameIndex(currentPriceSetNameIndex);
        response.setAttachedCharacterNames(attachedCharacterNames);
        response.setSharingLevels(sharingLevels);
        return response;
    }
}
