package lv.odylab.evemanage.client.presenter.tab;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.error.AsyncLoadingErrorEvent;
import lv.odylab.evemanage.client.gin.EveManageGinjector;
import lv.odylab.evemanage.client.presenter.Presenter;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class BlueprintsTabPresenterAsync implements Presenter {
    private static BlueprintsTabPresenter instance = null;

    private EveManageGinjector ginjector;
    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageConstants constants;

    @Inject
    public BlueprintsTabPresenterAsync(EveManageGinjector ginjector, EventBus eventBus, TrackingManager trackingManager, EveManageConstants constants) {
        this.ginjector = ginjector;
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.constants = constants;
    }

    @Override
    public void go(final HasWidgets container) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override
            public void onSuccess() {
                if (instance == null) {
                    instance = ginjector.getBlueprintsTabPresenter();
                }
                instance.go(container);
            }

            @Override
            public void onFailure(Throwable t) {
                eventBus.fireEvent(new AsyncLoadingErrorEvent(trackingManager, constants, "Failed to async load BlueprintsTabPresenter"));
            }
        });
    }
}
