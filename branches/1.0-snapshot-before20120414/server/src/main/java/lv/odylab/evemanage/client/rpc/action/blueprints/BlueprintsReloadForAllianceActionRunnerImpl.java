package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

import java.util.List;

public class BlueprintsReloadForAllianceActionRunnerImpl implements BlueprintsReloadForAllianceActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintsReloadForAllianceActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintsReloadForAllianceActionResponse execute(BlueprintsReloadForAllianceAction action) throws Exception {
        List<BlueprintDto> blueprints = clientFacade.getAllianceBlueprints();

        BlueprintsReloadForAllianceActionResponse response = new BlueprintsReloadForAllianceActionResponse();
        response.setBlueprints(blueprints);
        return response;
    }
}
