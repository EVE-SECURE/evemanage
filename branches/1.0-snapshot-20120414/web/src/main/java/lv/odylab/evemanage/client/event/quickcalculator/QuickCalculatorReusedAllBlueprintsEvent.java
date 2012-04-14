package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorReusedAllBlueprintsEvent extends QuickCalculatorTabEvent<QuickCalculatorReusedAllBlueprintsEventHandler> {
    public static final Type<QuickCalculatorReusedAllBlueprintsEventHandler> TYPE = new Type<QuickCalculatorReusedAllBlueprintsEventHandler>();

    public QuickCalculatorReusedAllBlueprintsEvent(TrackingManager trackingManager, EveManageConstants constants) {
        super(trackingManager, constants, 0L);
    }

    @Override
    public Type<QuickCalculatorReusedAllBlueprintsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(QuickCalculatorReusedAllBlueprintsEventHandler handler) {
        handler.onReusedAllBlueprints(this);

        trackEvent();
    }
}
