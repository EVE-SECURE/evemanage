package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorStoppedUsingAllBlueprintsEvent extends QuickCalculatorTabEvent<QuickCalculatorStoppedUsingAllBlueprintsEventHandler> {
    public static final Type<QuickCalculatorStoppedUsingAllBlueprintsEventHandler> TYPE = new Type<QuickCalculatorStoppedUsingAllBlueprintsEventHandler>();

    public QuickCalculatorStoppedUsingAllBlueprintsEvent(TrackingManager trackingManager, EveManageConstants constants) {
        super(trackingManager, constants, 0L);
    }

    @Override
    public Type<QuickCalculatorStoppedUsingAllBlueprintsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(QuickCalculatorStoppedUsingAllBlueprintsEventHandler handler) {
        handler.onStoppedUsingAllBlueprints(this);

        trackEvent();
    }
}
