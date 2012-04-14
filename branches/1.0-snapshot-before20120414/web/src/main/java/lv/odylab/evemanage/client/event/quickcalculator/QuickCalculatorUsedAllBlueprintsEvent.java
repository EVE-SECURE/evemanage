package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.Map;

public class QuickCalculatorUsedAllBlueprintsEvent extends QuickCalculatorTabEvent<QuickCalculatorUsedAllBlueprintsEventHandler> {
    public static final Type<QuickCalculatorUsedAllBlueprintsEventHandler> TYPE = new Type<QuickCalculatorUsedAllBlueprintsEventHandler>();

    private Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap;

    public QuickCalculatorUsedAllBlueprintsEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorUseAllBlueprintsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodesToUsedBlueprintMap = response.getPathNodesToUsedBlueprintMap();
    }

    @Override
    public Type<QuickCalculatorUsedAllBlueprintsEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Map<Long[], UsedBlueprintDto> getPathNodesToUsedBlueprintMap() {
        return pathNodesToUsedBlueprintMap;
    }

    @Override
    protected void dispatch(QuickCalculatorUsedAllBlueprintsEventHandler handler) {
        handler.onUsedAllBlueprints(this);

        trackEvent();
    }
}
