package lv.odylab.evemanage.client.event.error;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorTabErrorEvent extends ErrorEvent<QuickCalculatorTabErrorEventHandler> {
    public static final Type<QuickCalculatorTabErrorEventHandler> TYPE = new Type<QuickCalculatorTabErrorEventHandler>();

    public QuickCalculatorTabErrorEvent(TrackingManager trackingManager, EveManageConstants constants, String errorMessage) {
        super(trackingManager, constants, errorMessage);
    }

    @Override
    public Type<QuickCalculatorTabErrorEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(QuickCalculatorTabErrorEventHandler handler) {
        handler.onQuickCalculatorTabError(this);

        trackEvent(getErrorMessage());
    }
}