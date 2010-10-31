package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;

public class BlueprintDeleteActionRunnerImpl implements BlueprintDeleteActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintDeleteActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintDeleteActionResponse execute(BlueprintDeleteAction action) throws Exception {
        clientFacade.deleteBlueprint(action.getBlueprintID());

        BlueprintDeleteActionResponse response = new BlueprintDeleteActionResponse();
        response.setBlueprintID(action.getBlueprintID());
        return response;
    }
}
