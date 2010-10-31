package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorUsedBlueprintEvent extends QuickCalculatorTabEvent<QuickCalculatorUsedBlueprintEventHandler> {
    public static final Type<QuickCalculatorUsedBlueprintEventHandler> TYPE = new Type<QuickCalculatorUsedBlueprintEventHandler>();

    private Long[] pathNodes;
    private CalculationDto calculation;

    public QuickCalculatorUsedBlueprintEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorUseBlueprintActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodes = response.getPathNodes();
        this.calculation = response.getCalculation();
    }

    @Override
    public Type<QuickCalculatorUsedBlueprintEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public CalculationDto getCalculation() {
        return calculation;
    }

    @Override
    protected void dispatch(QuickCalculatorUsedBlueprintEventHandler handler) {
        handler.onUsedBlueprint(this);

        trackEvent();
    }
}
