package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;

public class QuickCalculatorUseBlueprintActionRunnerImpl implements QuickCalculatorUseBlueprintActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorUseBlueprintActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorUseBlueprintActionResponse execute(QuickCalculatorUseBlueprintAction action) throws Exception {
        UsedBlueprintDto usedBlueprintDto = clientFacade.useBlueprint(action.getPathNodes(), action.getBlueprintName());

        QuickCalculatorUseBlueprintActionResponse response = new QuickCalculatorUseBlueprintActionResponse();
        response.setPathNodes(action.getPathNodes());
        response.setUsedBlueprint(usedBlueprintDto);
        return response;
    }
}