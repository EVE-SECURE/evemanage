package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorUsedBlueprintEvent extends QuickCalculatorTabEvent<QuickCalculatorUsedBlueprintEventHandler> {
    public static final Type<QuickCalculatorUsedBlueprintEventHandler> TYPE = new Type<QuickCalculatorUsedBlueprintEventHandler>();

    private Long[] pathNodes;
    private UsedBlueprintDto usedBlueprint;

    public QuickCalculatorUsedBlueprintEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorUseBlueprintActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodes = response.getPathNodes();
        this.usedBlueprint = response.getUsedBlueprint();
    }

    @Override
    public Type<QuickCalculatorUsedBlueprintEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public UsedBlueprintDto getUsedBlueprint() {
        return usedBlueprint;
    }

    @Override
    protected void dispatch(QuickCalculatorUsedBlueprintEventHandler handler) {
        handler.onUsedBlueprint(this);

        trackEvent();
    }
}
