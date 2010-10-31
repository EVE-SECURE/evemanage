package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorTabFirstLoadEvent extends QuickCalculatorTabEvent<QuickCalculatorTabFirstLoadEventHandler> {
    public static final Type<QuickCalculatorTabFirstLoadEventHandler> TYPE = new Type<QuickCalculatorTabFirstLoadEventHandler>();


    public QuickCalculatorTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorTabFirstLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);
    }

    @Override
    public Type<QuickCalculatorTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(QuickCalculatorTabFirstLoadEventHandler handler) {
        handler.onQuickCalculatorTabFirstLoad(this);

        trackEvent();
    }
}
