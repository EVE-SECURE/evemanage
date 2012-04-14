package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseSchematicActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorUsedSchematicEvent extends QuickCalculatorTabEvent<QuickCalculatorUsedSchematicEventHandler> {
    public static final Type<QuickCalculatorUsedSchematicEventHandler> TYPE = new Type<QuickCalculatorUsedSchematicEventHandler>();

    private Long[] pathNodes;
    private UsedSchematicDto usedSchematic;

    public QuickCalculatorUsedSchematicEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorUseSchematicActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.pathNodes = response.getPathNodes();
        this.usedSchematic = response.getUsedSchematic();
    }

    @Override
    public Type<QuickCalculatorUsedSchematicEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public UsedSchematicDto getUsedSchematic() {
        return usedSchematic;
    }

    @Override
    protected void dispatch(QuickCalculatorUsedSchematicEventHandler handler) {
        handler.onUsedSchematic(this);

        trackEvent();
    }
}
