package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.Map;

public class QuickCalculatorUsedAllBlueprintsEvent extends QuickCalculatorTabEvent<QuickCalculatorUsedAllBlueprintsEventHandler> {
    public static final Type<QuickCalculatorUsedAllBlueprintsEventHandler> TYPE = new Type<QuickCalculatorUsedAllBlueprintsEventHandler>();

    private Map<Long[], CalculationDto> pathNodesToCalculationMap;

    public QuickCalculatorUsedAllBlueprintsEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorUseAllBlueprintsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodesToCalculationMap = response.getPathNodesToCalculationMap();
    }

    @Override
    public Type<QuickCalculatorUsedAllBlueprintsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Map<Long[], CalculationDto> getPathNodesToCalculationMap() {
        return pathNodesToCalculationMap;
    }

    @Override
    protected void dispatch(QuickCalculatorUsedAllBlueprintsEventHandler handler) {
        handler.onUsedAllBlueprints(this);

        trackEvent();
    }
}
