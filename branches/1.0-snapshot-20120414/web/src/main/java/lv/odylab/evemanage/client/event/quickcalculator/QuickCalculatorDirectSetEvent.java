package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorDirectSetActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.shared.CalculationExpression;

import java.util.Map;

public class QuickCalculatorDirectSetEvent extends QuickCalculatorTabEvent<QuickCalculatorDirectSetEventHandler> {
    public static final Type<QuickCalculatorDirectSetEventHandler> TYPE = new Type<QuickCalculatorDirectSetEventHandler>();

    private CalculationExpression calculationExpression;
    private CalculationDto calculation;
    private CalculationDto usedBlueprint;
    private Map<Long[], CalculationDto> pathNodesToCalculationMap;
    private Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap;

    public QuickCalculatorDirectSetEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorDirectSetActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.calculation = response.getCalculation();
        this.usedBlueprint = response.getUsedBlueprint();
        this.calculationExpression = response.getCalculationExpression();
        this.pathNodesToCalculationMap = response.getPathNodesToCalculationMap();
        this.pathNodesToUsedBlueprintMap = response.getPathNodesToUsedBlueprintMap();
    }

    @Override
    public Type<QuickCalculatorDirectSetEventHandler> getAssociatedType() {
        return TYPE;
    }

    public CalculationExpression getCalculationExpression() {
        return calculationExpression;
    }

    public CalculationDto getCalculation() {
        return calculation;
    }

    public CalculationDto getUsedBlueprint() {
        return usedBlueprint;
    }

    public Map<Long[], CalculationDto> getPathNodesToCalculationMap() {
        return pathNodesToCalculationMap;
    }

    public Map<Long[], UsedBlueprintDto> getPathNodesToUsedBlueprintMap() {
        return pathNodesToUsedBlueprintMap;
    }

    @Override
    protected void dispatch(QuickCalculatorDirectSetEventHandler handler) {
        handler.onQuickCalculatorDirectSet(this);

        trackEvent(calculation.getBlueprintTypeName());
    }
}