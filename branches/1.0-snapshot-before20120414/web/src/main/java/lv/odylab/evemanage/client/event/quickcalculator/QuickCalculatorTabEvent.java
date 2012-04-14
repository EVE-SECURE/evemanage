package lv.odylab.evemanage.client.event.quickcalculator;

import com.google.gwt.event.shared.EventHandler;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.TrackableGwtEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class QuickCalculatorTabEvent<T extends EventHandler> extends TrackableGwtEvent<T> {
    protected QuickCalculatorTabEvent(TrackingManager trackingManager, EveManageConstants constants, Long msDuration) {
        super(trackingManager, constants, constants.quickCalculatorToken(), msDuration);
    }
}
