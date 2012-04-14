package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

public class BlueprintAddActionRunnerImpl implements BlueprintAddActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintAddActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintAddActionResponse execute(BlueprintAddAction action) throws Exception {
        String blueprintTypeName = action.getBlueprintTypeName();
        Integer meLevel = action.getMeLevel();
        Integer peLevel = action.getPeLevel();
        BlueprintDto blueprint = clientFacade.createBlueprint(blueprintTypeName, meLevel, peLevel);

        BlueprintAddActionResponse response = new BlueprintAddActionResponse();
        response.setBlueprint(blueprint);
        return response;
    }
}
