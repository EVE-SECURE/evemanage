package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;

import java.util.HashMap;
import java.util.Map;

public class QuickCalculatorUseAllBlueprintsActionRunnerImpl implements QuickCalculatorUseAllBlueprintsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorUseAllBlueprintsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorUseAllBlueprintsActionResponse execute(QuickCalculatorUseAllBlueprintsAction action) throws Exception {
        Map<Long[], CalculationDto> pathNodesToCalculationDtoMap = new HashMap<Long[], CalculationDto>();
        for (Map.Entry<Long[], String> mapEntry : action.getPathNodesToBlueprintNameMap().entrySet()) {
            pathNodesToCalculationDtoMap.put(mapEntry.getKey(), clientFacade.getQuickCalculation(mapEntry.getKey(), mapEntry.getValue()));
        }
        QuickCalculatorUseAllBlueprintsActionResponse response = new QuickCalculatorUseAllBlueprintsActionResponse();
        response.setPathNodesToCalculationMap(pathNodesToCalculationDtoMap);
        return response;
    }
}