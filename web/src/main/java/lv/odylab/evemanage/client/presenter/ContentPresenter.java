package lv.odylab.evemanage.client.presenter;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.event.login.LoginEvent;
import lv.odylab.evemanage.client.event.login.LoginEventHandler;
import lv.odylab.evemanage.client.presenter.tab.AboutTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.BlueprintsTabPresenterAsync;
import lv.odylab.evemanage.client.presenter.tab.DashboardTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenterAsync;
import lv.odylab.evemanage.client.presenter.tab.PriceSetTabPresenterAsync;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenterAsync;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.ArrayList;
import java.util.List;

public class ContentPresenter implements Presenter, ValueChangeHandler<String>, LoginEventHandler {

    public interface Display extends AttachableDisplay {

        void setTabs(List<String> tabNames);

        void setSelectedTab(Integer selectedTabIndex);

        HasSelectionHandlers<Integer> getTabSelectionHandlers();

        HasWidgets getDashboardTabContainer();

        HasWidgets getBlueprintsTabContainer();

        HasWidgets getPriceSetTabContainer();

        HasWidgets getQuickCalculatorTabContainer();

        HasWidgets getPreferencesTabContainer();

        HasWidgets getAboutTabContainer();

    }

    private TrackingManager trackingManager;
    private EveManageConstants constants;
    private Display display;

    private DashboardTabPresenter dashboardTabPresenter;
    private BlueprintsTabPresenterAsync blueprintsTabPresenter;
    private PriceSetTabPresenterAsync priceSetTabPresenter;
    private QuickCalculatorTabPresenterAsync quickCalculatorTabPresenter;
    private PreferencesTabPresenterAsync preferencesTabPresenter;
    private AboutTabPresenter aboutTabPresenter;

    private List<String> currentTabNames;
    private HasWidgets container;

    @Inject
    public ContentPresenter(EventBus eventBus,
                            TrackingManager trackingManager,
                            EveManageConstants constants,
                            Display display,
                            DashboardTabPresenter dashboardTabPresenter,
                            BlueprintsTabPresenterAsync blueprintsTabPresenter,
                            PriceSetTabPresenterAsync priceSetTabPresenter,
                            QuickCalculatorTabPresenterAsync quickCalculatorTabPresenter,
                            PreferencesTabPresenterAsync preferencesTabPresenter,
                            AboutTabPresenter aboutTabPresenter) {
        this.trackingManager = trackingManager;
        this.constants = constants;
        this.display = display;
        this.dashboardTabPresenter = dashboardTabPresenter;
        this.blueprintsTabPresenter = blueprintsTabPresenter;
        this.priceSetTabPresenter = priceSetTabPresenter;
        this.quickCalculatorTabPresenter = quickCalculatorTabPresenter;
        this.preferencesTabPresenter = preferencesTabPresenter;
        this.aboutTabPresenter = aboutTabPresenter;

        History.addValueChangeHandler(this);
        eventBus.addHandler(LoginEvent.TYPE, this);
    }

    @Override
    public void go(HasWidgets container) {
        this.container = container;
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (constants.dashboardToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            dashboardTabPresenter.go(display.getDashboardTabContainer());
        } else if (constants.blueprintsToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            blueprintsTabPresenter.go(display.getBlueprintsTabContainer());
        } else if (constants.priceSetToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            priceSetTabPresenter.go(display.getPriceSetTabContainer());
        } else if (constants.quickCalculatorToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            quickCalculatorTabPresenter.go(display.getQuickCalculatorTabContainer());
        } else if (constants.preferencesToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            preferencesTabPresenter.go(display.getPreferencesTabContainer());
        } else if (constants.aboutToken().equals(token)) {
            display.setSelectedTab(currentTabNames.indexOf(token));
            aboutTabPresenter.go(display.getAboutTabContainer());
        }
        trackingManager.trackPageView(constants.googleAnalyticsAccount(), token);
    }

    @Override
    public void onLogin(LoginEvent event) {
        container.clear();
        display.attach(container);

        LoginDto loginDto = event.getLoginDto();
        currentTabNames = createCurrentTabNames(loginDto);
        display.setTabs(currentTabNames);
        bind();

        String token = History.getToken();
        if ("".equals(token)) {
            if (loginDto.isLoggedIn()) {
                History.newItem(constants.dashboardToken());
            } else {
                History.newItem(constants.aboutToken());
            }
        } else {
            History.fireCurrentHistoryState();
        }
    }

    private List<String> createCurrentTabNames(LoginDto loginDto) {
        List<String> tabNames = new ArrayList<String>();

        if (loginDto.isLoggedIn()) {
            tabNames.add(constants.dashboardToken());
            tabNames.add(constants.blueprintsToken());
            tabNames.add(constants.priceSetToken());
            if (loginDto.isAdmin()) {
                // nothing for now
            }
            tabNames.add(constants.preferencesToken());
        }
        tabNames.add(constants.quickCalculatorToken());
        tabNames.add(constants.aboutToken());

        return tabNames;
    }

    private void bind() {
        display.getTabSelectionHandlers().addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                Integer selectedTabIndex = event.getSelectedItem();
                History.newItem(currentTabNames.get(selectedTabIndex));
            }
        });
    }
}
