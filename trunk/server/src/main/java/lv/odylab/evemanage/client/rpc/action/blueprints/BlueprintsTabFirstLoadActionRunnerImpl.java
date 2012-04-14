package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.util.List;

public class BlueprintsTabFirstLoadActionRunnerImpl implements BlueprintsTabFirstLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintsTabFirstLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintsTabFirstLoadActionResponse execute(BlueprintsTabFirstLoadAction action) throws Exception {
        List<BlueprintDto> blueprints = clientFacade.getBlueprints();
        List<CharacterNameDto> attachedCharacterNames = clientFacade.getAvailableAttachedCharacterNames();
        List<String> sharingLevels = clientFacade.getAvailableSharingLevels();
        List<ApiKeyDto> fullApiKeys = clientFacade.getFullApiKeys();

        BlueprintsTabFirstLoadActionResponse response = new BlueprintsTabFirstLoadActionResponse();
        response.setBlueprints(blueprints);
        response.setAttachedCharacterNames(attachedCharacterNames);
        response.setSharingLevels(sharingLevels);
        response.setFullApiKeys(fullApiKeys);
        return response;
    }
}
