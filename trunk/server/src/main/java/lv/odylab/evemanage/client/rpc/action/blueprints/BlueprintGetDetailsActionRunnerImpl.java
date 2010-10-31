package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;

public class BlueprintGetDetailsActionRunnerImpl implements BlueprintGetDetailsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintGetDetailsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintGetDetailsActionResponse execute(BlueprintGetDetailsAction action) throws Exception {
        BlueprintDetailsDto detailsDto = clientFacade.getBlueprintDetails(action.getBlueprintTypeID());

        BlueprintGetDetailsActionResponse response = new BlueprintGetDetailsActionResponse();
        response.setBlueprintID(action.getBlueprintID());
        response.setDetails(detailsDto);
        return response;
    }
}
