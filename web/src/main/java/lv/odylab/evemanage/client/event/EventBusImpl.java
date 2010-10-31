package lv.odylab.evemanage.client.event;

import com.google.gwt.event.shared.HandlerManager;

public class EventBusImpl extends HandlerManager implements EventBus {
    public EventBusImpl() {
        super(null);
    }
}
