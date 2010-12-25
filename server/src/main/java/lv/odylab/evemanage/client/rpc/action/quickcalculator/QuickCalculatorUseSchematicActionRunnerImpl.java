package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;

public class QuickCalculatorUseSchematicActionRunnerImpl implements QuickCalculatorUseSchematicActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorUseSchematicActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorUseSchematicActionResponse execute(QuickCalculatorUseSchematicAction action) throws Exception {
        UsedSchematicDto usedSchematicDto = clientFacade.useSchematic(action.getPathNodes(), action.getSchematicName());

        QuickCalculatorUseSchematicActionResponse response = new QuickCalculatorUseSchematicActionResponse();
        response.setPathNodes(action.getPathNodes());
        response.setUsedSchematic(usedSchematicDto);
        return response;
    }
}