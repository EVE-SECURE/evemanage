package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class QuickCalculatorDirectSetActionRunnerImpl implements QuickCalculatorDirectSetActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorDirectSetActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorDirectSetActionResponse execute(QuickCalculatorDirectSetAction action) throws Exception {
        CalculationExpression calculationExpression = action.getCalculationExpression();
        CalculationDto calculationDto = clientFacade.getQuickCalculationForExpression(calculationExpression);

        Map<Long[], CalculationDto> pathNodesToCalculationDtoMap = new TreeMap<Long[], CalculationDto>(new LongArrayComparator());
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
            CalculationDto calculationDtoForNode = clientFacade.getQuickCalculation(pathNodes, pathNodes[pathNodes.length - 1]);
            calculationDtoForNode.setMaterialLevel(meLevel);
            calculationDtoForNode.setProductivityLevel(peLevel);
            pathNodesToCalculationDtoMap.put(pathNodes, calculationDtoForNode);
        }

        QuickCalculatorDirectSetActionResponse response = new QuickCalculatorDirectSetActionResponse();
        response.setCalculationExpression(calculationExpression);
        response.setCalculation(calculationDto);
        response.setPathNodesToCalculationMap(new LinkedHashMap<Long[], CalculationDto>(pathNodesToCalculationDtoMap));
        return response;
    }
}