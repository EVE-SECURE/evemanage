package lv.odylab.evemanage.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.event.EventBus;
import lv.odylab.evemanage.client.event.FooterActionCallback;
import lv.odylab.evemanage.client.event.footer.GotVersionsEvent;
import lv.odylab.evemanage.client.event.footer.GotVersionsEventHandler;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.footer.GetVersionsAction;
import lv.odylab.evemanage.client.rpc.action.footer.GetVersionsActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class FooterPresenter implements Presenter, GotVersionsEventHandler {

    public interface Display extends AttachableDisplay {

        void setVersions(String eveManageVersion, String eveDbVersion);

    }

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageConstants constants;
    private Display display;

    @Inject
    public FooterPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageMessages messages, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.display = display;

        eventBus.addHandler(GotVersionsEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        display.attach(container);
        doGetVersions();
    }

    @Override
    public void onGotVersions(GotVersionsEvent event) {
        display.setVersions(event.getEveManageVersion(), event.getEveDbVersion());
    }

    private void doGetVersions() {
        GetVersionsAction action = new GetVersionsAction();
        rpcService.execute(action, new FooterActionCallback<GetVersionsActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(GetVersionsActionResponse response) {
                eventBus.fireEvent(new GotVersionsEvent(response));
            }
        });
    }
}
