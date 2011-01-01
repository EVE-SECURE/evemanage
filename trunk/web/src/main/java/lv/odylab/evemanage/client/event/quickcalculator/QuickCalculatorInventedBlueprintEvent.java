package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorInventBlueprintActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorInventedBlueprintEvent extends QuickCalculatorTabEvent<QuickCalculatorInventedBlueprintEventHandler> {
    public static final Type<QuickCalculatorInventedBlueprintEventHandler> TYPE = new Type<QuickCalculatorInventedBlueprintEventHandler>();

    private Long[] pathNodes;
    private InventedBlueprintDto inventedBlueprint;

    public QuickCalculatorInventedBlueprintEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorInventBlueprintActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodes = response.getPathNodes();
        this.inventedBlueprint = response.getInventedBlueprint();
    }

    @Override
    public Type<QuickCalculatorInventedBlueprintEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public InventedBlueprintDto getInventedBlueprint() {
        return inventedBlueprint;
    }

    @Override
    protected void dispatch(QuickCalculatorInventedBlueprintEventHandler handler) {
        handler.onInventedBlueprint(this);

        trackEvent();
    }
}
