package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;

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
        Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintDtoMap = new HashMap<Long[], UsedBlueprintDto>();
        for (Map.Entry<Long[], String> mapEntry : action.getPathNodesToBlueprintNameMap().entrySet()) {
            pathNodesToUsedBlueprintDtoMap.put(mapEntry.getKey(), clientFacade.useBlueprint(mapEntry.getKey(), mapEntry.getValue()));
        }
        QuickCalculatorUseAllBlueprintsActionResponse response = new QuickCalculatorUseAllBlueprintsActionResponse();
        response.setPathNodesToUsedBlueprintMap(pathNodesToUsedBlueprintDtoMap);
        return response;
    }
}