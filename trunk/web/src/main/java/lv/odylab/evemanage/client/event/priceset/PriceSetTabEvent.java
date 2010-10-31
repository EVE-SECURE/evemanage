package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.TrackableGwtEvent;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public abstract class PriceSetTabEvent<T extends EventHandler> extends TrackableGwtEvent<T> {
    protected PriceSetTabEvent(TrackingManager trackingManager, EveManageConstants constants, Long msDuration) {
        super(trackingManager, constants, constants.priceSetToken(), msDuration);
    }
}
