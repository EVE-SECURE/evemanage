package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorStoppedUsingSchematicEvent extends QuickCalculatorTabEvent<QuickCalculatorStoppedUsingSchematicEventHandler> {
    public static final Type<QuickCalculatorStoppedUsingSchematicEventHandler> TYPE = new Type<QuickCalculatorStoppedUsingSchematicEventHandler>();

    private Long[] pathNodes;
    private String pathNodesString;

    public QuickCalculatorStoppedUsingSchematicEvent(TrackingManager trackingManager, EveManageConstants constants, Long[] pathNodes, String pathNodesString) {
        super(trackingManager, constants, 0L);

        this.pathNodes = pathNodes;
        this.pathNodesString = pathNodesString;
    }

    @Override
    public Type<QuickCalculatorStoppedUsingSchematicEventHandler> getAssociatedType() {
        return TYPE;
    }

    public String getPathNodesString() {
        return pathNodesString;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    @Override
    protected void dispatch(QuickCalculatorStoppedUsingSchematicEventHandler handler) {
        handler.onStoppedUsingSchematic(this);

        trackEvent();
    }
}
