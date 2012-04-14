package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.shared.CalculationExpression;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Deprecated
public class QuickCalculatorDirectSetActionRunnerImpl implements QuickCalculatorDirectSetActionRunner {
    private final EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorDirectSetActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorDirectSetActionResponse execute(QuickCalculatorDirectSetAction action) throws Exception {
        CalculationExpression calculationExpression = action.getCalculationExpression();
        CalculationDto calculationDto = clientFacade.getQuickCalculationForExpression(calculationExpression);

        Map<Long[], UsedBlueprintDto> pathNodesToCalculationDtoMap = new TreeMap<Long[], UsedBlueprintDto>(new LongArrayComparator());
        for (Map.Entry<String, Integer> entry : calculationExpression.getBlueprintPathToMeLevelMap().entrySet()) {
            String pathNodeString = entry.getKey();
            Integer meLevel = entry.getValue();
            Integer peLevel = calculationExpression.getBlueprintPathToPeLevelMap().get(pathNodeString);
            String[] pathNodesAsStringArray = pathNodeString.split("\\/");
            Long[] pathNodes = new Long[pathNodesAsStringArray.length + 1];
            pathNodes[0] = calculationDto.getProductTypeID();
            for (int i = 0; i < pathNodesAsStringArray.length; i++) {
                String pathNodeAsString = pathNodesAsStringArray[i];
                pathNodes[i + 1] = Long.valueOf(pathNodeAsString);
            }
            UsedBlueprintDto calculationDtoForNode = clientFacade.useBlueprint(pathNodes, pathNodes[pathNodes.length - 1]);
            calculationDtoForNode.setMaterialLevel(meLevel);
            calculationDtoForNode.setProductivityLevel(peLevel);
            pathNodesToCalculationDtoMap.put(pathNodes, calculationDtoForNode);
        }

        QuickCalculatorDirectSetActionResponse response = new QuickCalculatorDirectSetActionResponse();
        response.setCalculationExpression(calculationExpression);
        response.setUsedBlueprint(calculationDto);
        response.setPathNodesToUsedBlueprintMap(new LinkedHashMap<Long[], UsedBlueprintDto>(pathNodesToCalculationDtoMap));
        return response;
    }
}