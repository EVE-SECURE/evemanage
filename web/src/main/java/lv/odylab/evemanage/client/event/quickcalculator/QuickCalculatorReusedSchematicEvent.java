package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorReusedSchematicEvent extends QuickCalculatorTabEvent<QuickCalculatorReusedSchematicEventHandler> {
    public static final Type<QuickCalculatorReusedSchematicEventHandler> TYPE = new Type<QuickCalculatorReusedSchematicEventHandler>();

    private Long[] pathNodes;
    private String pathNodesString;

    public QuickCalculatorReusedSchematicEvent(TrackingManager trackingManager, EveManageConstants constants, Long[] pathNodes, String pathNodesString) {
        super(trackingManager, constants, 0L);

        this.pathNodes = pathNodes;
        this.pathNodesString = pathNodesString;
    }

    @Override
    public Type<QuickCalculatorReusedSchematicEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public String getPathNodesString() {
        return pathNodesString;
    }

    @Override
    protected void dispatch(QuickCalculatorReusedSchematicEventHandler handler) {
        handler.onReusedSchematic(this);

        trackEvent();
    }
}
