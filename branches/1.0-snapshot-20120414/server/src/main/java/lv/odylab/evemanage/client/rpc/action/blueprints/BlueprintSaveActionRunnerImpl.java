package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

public class BlueprintSaveActionRunnerImpl implements BlueprintSaveActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintSaveActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintSaveActionResponse execute(BlueprintSaveAction action) throws Exception {
        BlueprintDto blueprintDto = clientFacade.saveBlueprint(action.getBlueprintID(), action.getItemID(), action.getMeLevel(), action.getPeLevel(), action.getAttachedCharacterID(), action.getSharingLevel());

        BlueprintSaveActionResponse response = new BlueprintSaveActionResponse();
        response.setBlueprint(blueprintDto);
        return response;
    }
}
