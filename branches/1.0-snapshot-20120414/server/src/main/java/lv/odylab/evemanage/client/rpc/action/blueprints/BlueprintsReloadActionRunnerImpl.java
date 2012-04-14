package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.util.List;

public class BlueprintsReloadActionRunnerImpl implements BlueprintsReloadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintsReloadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintsReloadActionResponse execute(BlueprintsReloadAction action) throws Exception {
        List<BlueprintDto> blueprints = clientFacade.getBlueprints();
        List<CharacterNameDto> attachedCharacterNames = clientFacade.getAvailableAttachedCharacterNames();
        List<SharingLevel> sharingLevels = clientFacade.getAvailableSharingLevels();

        BlueprintsReloadActionResponse response = new BlueprintsReloadActionResponse();
        response.setBlueprints(blueprints);
        response.setAttachedCharacterNames(attachedCharacterNames);
        response.setSharingLevels(sharingLevels);
        return response;
    }
}
