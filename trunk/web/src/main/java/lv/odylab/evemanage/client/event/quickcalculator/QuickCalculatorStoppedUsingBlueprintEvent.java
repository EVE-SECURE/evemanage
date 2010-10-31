package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorStoppedUsingBlueprintEvent extends QuickCalculatorTabEvent<QuickCalculatorStoppedUsingBlueprintEventHandler> {
    public static final Type<QuickCalculatorStoppedUsingBlueprintEventHandler> TYPE = new Type<QuickCalculatorStoppedUsingBlueprintEventHandler>();

    private Long[] pathNodes;
    private String pathNodesString;

    public QuickCalculatorStoppedUsingBlueprintEvent(TrackingManager trackingManager, EveManageConstants constants, Long[] pathNodes, String pathNodesString) {
        super(trackingManager, constants, 0L);

        this.pathNodes = pathNodes;
        this.pathNodesString = pathNodesString;
    }

    @Override
    public Type<QuickCalculatorStoppedUsingBlueprintEventHandler> getAssociatedType() {
        return TYPE;
    }

    public String getPathNodesString() {
        return pathNodesString;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    @Override
    protected void dispatch(QuickCalculatorStoppedUsingBlueprintEventHandler handler) {
        handler.onStoppedUsingBlueprint(this);

        trackEvent();
    }
}
