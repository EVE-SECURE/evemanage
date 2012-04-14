package lv.odylab.evemanage.client.event.error;

import com.google.gwt.event.shared.EventHandler;

public interface AsyncLoadingErrorEventHandler extends EventHandler {

    void onAsyncLoadingError(AsyncLoadingErrorEvent event);

}