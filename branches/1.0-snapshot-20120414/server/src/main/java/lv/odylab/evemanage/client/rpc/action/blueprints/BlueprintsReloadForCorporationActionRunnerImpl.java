package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;

import java.util.List;

public class BlueprintsReloadForCorporationActionRunnerImpl implements BlueprintsReloadForCorporationActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintsReloadForCorporationActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintsReloadForCorporationActionResponse execute(BlueprintsReloadForCorporationAction action) throws Exception {
        List<BlueprintDto> blueprints = clientFacade.getCorporationBlueprints();

        BlueprintsReloadForCorporationActionResponse response = new BlueprintsReloadForCorporationActionResponse();
        response.setBlueprints(blueprints);
        return response;
    }
}
