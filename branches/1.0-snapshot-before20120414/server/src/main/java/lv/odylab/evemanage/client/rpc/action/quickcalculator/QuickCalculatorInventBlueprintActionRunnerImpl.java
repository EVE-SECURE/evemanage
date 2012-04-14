package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;

public class QuickCalculatorInventBlueprintActionRunnerImpl implements QuickCalculatorInventBlueprintActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorInventBlueprintActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorInventBlueprintActionResponse execute(QuickCalculatorInventBlueprintAction action) throws Exception {
        InventedBlueprintDto inventBlueprintDto = clientFacade.inventBlueprint(action.getPathNodes(), action.getBlueprintName());

        QuickCalculatorInventBlueprintActionResponse response = new QuickCalculatorInventBlueprintActionResponse();
        response.setPathNodes(action.getPathNodes());
        response.setInventedBlueprint(inventBlueprintDto);
        return response;
    }
}