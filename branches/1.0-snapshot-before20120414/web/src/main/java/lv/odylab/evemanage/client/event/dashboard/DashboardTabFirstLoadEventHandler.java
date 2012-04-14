package lv.odylab.evemanage.client.event.dashboard;

import com.google.gwt.event.shared.EventHandler;

public interface DashboardTabFirstLoadEventHandler extends EventHandler {

    void onDashboardTabFirstLoad(DashboardTabFirstLoadEvent event);

}