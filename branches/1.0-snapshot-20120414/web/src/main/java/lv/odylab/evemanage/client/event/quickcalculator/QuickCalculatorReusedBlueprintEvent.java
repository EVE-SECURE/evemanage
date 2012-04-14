package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorReusedBlueprintEvent extends QuickCalculatorTabEvent<QuickCalculatorReusedBlueprintEventHandler> {
    public static final Type<QuickCalculatorReusedBlueprintEventHandler> TYPE = new Type<QuickCalculatorReusedBlueprintEventHandler>();

    private Long[] pathNodes;
    private String pathNodesString;

    public QuickCalculatorReusedBlueprintEvent(TrackingManager trackingManager, EveManageConstants constants, Long[] pathNodes, String pathNodesString) {
        super(trackingManager, constants, 0L);

        this.pathNodes = pathNodes;
        this.pathNodesString = pathNodesString;
    }

    @Override
    public Type<QuickCalculatorReusedBlueprintEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public String getPathNodesString() {
        return pathNodesString;
    }

    @Override
    protected void dispatch(QuickCalculatorReusedBlueprintEventHandler handler) {
        handler.onReusedBlueprint(this);

        trackEvent();
    }
}
