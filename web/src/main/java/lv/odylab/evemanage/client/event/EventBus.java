package lv.odylab.evemanage.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public interface EventBus {

    <H extends EventHandler> HandlerRegistration addHandler(GwtEvent.Type<H> type, H handler);

    void fireEvent(GwtEvent<?> event);

}
