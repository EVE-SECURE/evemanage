package lv.odylab.evemanage.client.event.error;

import com.google.gwt.event.shared.EventHandler;

public interface DashboardTabErrorEventHandler extends EventHandler {

    void onDashboardTabError(DashboardTabErrorEvent event);

}