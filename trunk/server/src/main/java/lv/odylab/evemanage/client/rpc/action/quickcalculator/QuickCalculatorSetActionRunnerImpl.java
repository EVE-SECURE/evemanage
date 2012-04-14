package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;

public class QuickCalculatorSetActionRunnerImpl implements QuickCalculatorSetActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorSetActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorSetActionResponse execute(QuickCalculatorSetAction action) throws Exception {
        CalculationDto calculationDto = clientFacade.getQuickCalculation(action.getBlueprintName());
        QuickCalculatorSetActionResponse response = new QuickCalculatorSetActionResponse();
        response.setCalculation(calculationDto);
        return response;
    }
}