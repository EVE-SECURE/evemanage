package lv.odylab.evemanage.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageErrorConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.oracle.BlueprintTypeSuggestOracle;
import lv.odylab.evemanage.client.oracle.TypeSuggestOracle;
import lv.odylab.evemanage.client.presenter.ContentPresenter;
import lv.odylab.evemanage.client.presenter.FooterPresenter;
import lv.odylab.evemanage.client.presenter.LoginPresenter;
import lv.odylab.evemanage.client.presenter.tab.AboutTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.BlueprintsTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.DashboardTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.PriceSetTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.blueprint.BlueprintDetailsPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationProcessor;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceAsync;
import lv.odylab.evemanage.client.tracking.GoogleAnalyticsTrackingManagerImpl;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.view.ContentView;
import lv.odylab.evemanage.client.view.FooterView;
import lv.odylab.evemanage.client.view.LoginView;
import lv.odylab.evemanage.client.view.tab.AboutTabView;
import lv.odylab.evemanage.client.view.tab.BlueprintsTabView;
import lv.odylab.evemanage.client.view.tab.DashboardTabView;
import lv.odylab.evemanage.client.view.tab.PreferencesTabView;
import lv.odylab.evemanage.client.view.tab.PriceSetTabView;
import lv.odylab.evemanage.client.view.tab.QuickCalculatorTabView;
import lv.odylab.evemanage.client.view.tab.blueprint.BlueprintDetailsView;

public class EveManageModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(TrackingManager.class).to(GoogleAnalyticsTrackingManagerImpl.class).in(Singleton.class);
        bind(EveManageRemoteServiceAsync.class).in(Singleton.class);
        bind(EveManageConstants.class).in(Singleton.class);
        bind(EveManageResources.class).in(Singleton.class);
        bind(EveManageMessages.class).in(Singleton.class);
        bind(EveManageUrlMessages.class).in(Singleton.class);
        bind(EveManageErrorConstants.class).in(Singleton.class);
        bind(CcpJsMessages.class).in(Singleton.class);
        bind(EveImageUrlProvider.class).in(Singleton.class);
        bind(EveCalculator.class).in(Singleton.class);
        bind(CalculationProcessor.class).in(Singleton.class);

        bind(LoginPresenter.Display.class).to(LoginView.class);
        bind(ContentPresenter.Display.class).to(ContentView.class);
        bind(FooterPresenter.Display.class).to(FooterView.class);
        bind(DashboardTabPresenter.Display.class).to(DashboardTabView.class);
        bind(BlueprintsTabPresenter.Display.class).to(BlueprintsTabView.class);
        bind(BlueprintDetailsPresenter.Display.class).to(BlueprintDetailsView.class);
        bind(PriceSetTabPresenter.Display.class).to(PriceSetTabView.class);
        bind(QuickCalculatorTabPresenter.Display.class).to(QuickCalculatorTabView.class);
        bind(PreferencesTabPresenter.Display.class).to(PreferencesTabView.class);
        bind(AboutTabPresenter.Display.class).to(AboutTabView.class);

        bind(TypeSuggestOracle.class).in(Singleton.class);
        bind(BlueprintTypeSuggestOracle.class).in(Singleton.class);
    }
}
