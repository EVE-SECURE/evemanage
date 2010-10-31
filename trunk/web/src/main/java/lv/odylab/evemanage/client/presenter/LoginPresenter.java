package lv.odylab.evemanage.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.event.EventBus;
import lv.odylab.evemanage.client.event.LoginActionCallback;
import lv.odylab.evemanage.client.event.login.LoginEvent;
import lv.odylab.evemanage.client.event.login.LoginEventHandler;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.rpc.action.login.LoginAction;
import lv.odylab.evemanage.client.rpc.action.login.LoginActionResponse;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class LoginPresenter implements Presenter, LoginEventHandler {

    public interface Display extends AttachableDisplay {

        Widget getSpinnerImage();

        HasText getUserLoginLabel();

        HasText getBannerLabel();

        Anchor getSignInSignOutAnchor();

        Anchor getRussianAnchor();

        Anchor getEnglishAnchor();

        void displayMenu();

        void displayBanner();

    }

    private EventBus eventBus;
    private TrackingManager trackingManager;
    private EveManageRemoteServiceAsync rpcService;
    private EveManageConstants constants;
    private EveManageMessages messages;
    private Display display;

    @Inject
    public LoginPresenter(EventBus eventBus, TrackingManager trackingManager, EveManageRemoteServiceAsync rpcService, EveManageConstants constants, EveManageMessages messages, Display display) {
        this.eventBus = eventBus;
        this.trackingManager = trackingManager;
        this.rpcService = rpcService;
        this.constants = constants;
        this.messages = messages;
        this.display = display;

        eventBus.addHandler(LoginEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        display.attach(container);
        doLogin();
    }

    @Override
    public void onLogin(LoginEvent event) {
        LoginDto loginDto = event.getLoginDto();
        if (loginDto.isLoggedIn()) {
            display.getUserLoginLabel().setText(loginDto.getEmailAddress());
            display.getSignInSignOutAnchor().setHref(loginDto.getLogoutUrl());
            display.getSignInSignOutAnchor().setText(messages.signOut());
        } else {
            display.getUserLoginLabel().setText(messages.notLoggedIn());
            display.getSignInSignOutAnchor().setHref(loginDto.getLoginUrl());
            display.getSignInSignOutAnchor().setText(messages.signIn());
        }
        LocaleInfo currentLocale = LocaleInfo.getCurrentLocale();
        if ("ru".equals(currentLocale.getLocaleName())) {
            display.getEnglishAnchor().setHref(loginDto.getEnglishUrl());
            display.getEnglishAnchor().setVisible(true);
        } else {
            display.getRussianAnchor().setHref(loginDto.getRussianUrl());
            display.getRussianAnchor().setVisible(true);
        }
        display.displayMenu();
        if (loginDto.getBannerMessage() != null) {
            display.getBannerLabel().setText(loginDto.getBannerMessage());
            display.displayBanner();
        }
        display.getSpinnerImage().setVisible(false);
    }

    private void doLogin() {
        LoginAction action = new LoginAction();
        action.setRequestUri(GWT.getHostPageBaseURL());
        action.setLocale(LocaleInfo.getCurrentLocale().getLocaleName());
        display.getSpinnerImage().setVisible(true);
        rpcService.execute(action, new LoginActionCallback<LoginActionResponse>(eventBus, trackingManager, constants) {
            @Override
            public void onSuccess(LoginActionResponse response) {
                eventBus.fireEvent(new LoginEvent(response));
            }

            @Override
            public void onFailure(Throwable throwable) {
                display.getSpinnerImage().setVisible(false);
                super.onFailure(throwable);
            }
        });
    }
}
