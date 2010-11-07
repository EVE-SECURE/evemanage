package lv.odylab.evemanage.application;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Names;
import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.appengine.aspect.Caching;
import lv.odylab.appengine.aspect.CachingAspect;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.appengine.aspect.LoggingAspect;
import lv.odylab.eveapi.EveApiFacade;
import lv.odylab.eveapi.EveApiFacadeImpl;
import lv.odylab.evecentralapi.EveCentralApiFacade;
import lv.odylab.evecentralapi.EveCentralApiFacadeImpl;
import lv.odylab.evemanage.application.admin.ClearCacheServlet;
import lv.odylab.evemanage.application.background.apikey.StartApiKeyUpdateCronServlet;
import lv.odylab.evemanage.application.background.apikey.UpdateApiKeyTaskLauncher;
import lv.odylab.evemanage.application.background.apikey.UpdateApiKeyTaskServlet;
import lv.odylab.evemanage.application.background.blueprint.AddBlueprintTaskLauncher;
import lv.odylab.evemanage.application.background.blueprint.AddBlueprintTaskServlet;
import lv.odylab.evemanage.application.background.blueprint.UpdateBlueprintTaskLauncher;
import lv.odylab.evemanage.application.background.blueprint.UpdateBlueprintTaskServlet;
import lv.odylab.evemanage.application.background.priceset.UpdatePriceSetTaskLauncher;
import lv.odylab.evemanage.application.background.priceset.UpdatePriceSetTaskServlet;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintAddActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintAddActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintDeleteActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintDeleteActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintGetDetailsActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintGetDetailsActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintSaveActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintSaveActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsImportActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsImportActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForAllianceActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForAllianceActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForCorporationActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForCorporationActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsTabFirstLoadActionRunner;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsTabFirstLoadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.footer.GetVersionsActionRunner;
import lv.odylab.evemanage.client.rpc.action.footer.GetVersionsActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.login.LoginActionRunner;
import lv.odylab.evemanage.client.rpc.action.login.LoginActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddApiKeyActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddApiKeyActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddCharacterActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddCharacterActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteApiKeyActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteApiKeyActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteCharacterActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteCharacterActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSetMainCharacterActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSetMainCharacterActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesTabFirstLoadActionRunner;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesTabFirstLoadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveCentralActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveCentralActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveMetricsActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceFetchFromEveMetricsActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetAddItemActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetAddItemActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCopyActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCopyActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCreateActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetCreateActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetDeleteActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetDeleteActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAllianceNamesActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadAllianceNamesActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadCorporationNamesActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadCorporationNamesActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForAllianceActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForAllianceActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForCorporationActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadForCorporationActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadNamesActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetLoadNamesActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetRenameActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetRenameActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetSaveActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetSaveActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetTabFirstLoadActionRunner;
import lv.odylab.evemanage.client.rpc.action.priceset.PriceSetTabFirstLoadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveCentralActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorFetchPricesFromEveMetricsActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseAllBlueprintsActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionRunner;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorUseBlueprintActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestBlueprintTypeActionRunner;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestBlueprintTypeActionRunnerImpl;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestTypeActionRunner;
import lv.odylab.evemanage.client.rpc.action.suggest.SuggestTypeActionRunnerImpl;
import lv.odylab.evemanage.domain.EveManageObjectifyFactory;
import lv.odylab.evemanage.integration.eveapi.EveApiGateway;
import lv.odylab.evemanage.integration.eveapi.EveApiGatewayImpl;
import lv.odylab.evemanage.integration.evecentralapi.EveCentralApiGateway;
import lv.odylab.evemanage.integration.evecentralapi.EveCentralApiGatewayImpl;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.EveDbGatewayImpl;
import lv.odylab.evemanage.integration.evemetricsapi.EveMetricsApiGateway;
import lv.odylab.evemanage.integration.evemetricsapi.EveMetricsApiGatewayImpl;
import lv.odylab.evemanage.security.EveManageSecurityManager;
import lv.odylab.evemanage.security.EveManageSecurityManagerImpl;
import lv.odylab.evemanage.security.HashCalculator;
import lv.odylab.evemanage.security.HashCalculatorImpl;
import lv.odylab.evemanage.security.PrivateKeyProvider;
import lv.odylab.evemanage.security.PublicKeyProvider;
import lv.odylab.evemanage.service.blueprint.BlueprintManagementService;
import lv.odylab.evemanage.service.blueprint.BlueprintManagementServiceImpl;
import lv.odylab.evemanage.service.calculation.CalculationService;
import lv.odylab.evemanage.service.calculation.CalculationServiceImpl;
import lv.odylab.evemanage.service.eve.CharacterSynchronizationService;
import lv.odylab.evemanage.service.eve.CharacterSynchronizationServiceImpl;
import lv.odylab.evemanage.service.eve.EveApiDataService;
import lv.odylab.evemanage.service.eve.EveApiDataServiceImpl;
import lv.odylab.evemanage.service.eve.EveManagementService;
import lv.odylab.evemanage.service.eve.EveManagementServiceImpl;
import lv.odylab.evemanage.service.eve.EveSynchronizationService;
import lv.odylab.evemanage.service.eve.EveSynchronizationServiceImpl;
import lv.odylab.evemanage.service.eve.EveUpdateService;
import lv.odylab.evemanage.service.eve.EveUpdateServiceImpl;
import lv.odylab.evemanage.service.priceset.PriceSetManagementService;
import lv.odylab.evemanage.service.priceset.PriceSetManagementServiceImpl;
import lv.odylab.evemanage.service.user.UserManagementService;
import lv.odylab.evemanage.service.user.UserManagementServiceImpl;
import lv.odylab.evemanage.service.user.UserSynchronizationService;
import lv.odylab.evemanage.service.user.UserSynchronizationServiceImpl;
import lv.odylab.evemetricsapi.EveMetricsApiFacade;
import lv.odylab.evemetricsapi.EveMetricsApiFacadeImpl;

import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.not;

public class EveManageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectifyFactory.class).to(EveManageObjectifyFactory.class).in(Singleton.class);
        bind(PublicKey.class).toProvider(PublicKeyProvider.class).in(Singleton.class);
        bind(PrivateKey.class).toProvider(PrivateKeyProvider.class).in(Singleton.class);
        bind(EveManageSecurityManager.class).to(EveManageSecurityManagerImpl.class).in(Singleton.class);
        bind(HashCalculator.class).to(HashCalculatorImpl.class).in(Singleton.class);

        bind(EveManageApplicationFacade.class).to(EveManageApplicationFacadeImpl.class).in(Singleton.class);
        bind(EveManageClientFacade.class).to(EveManageClientFacadeImpl.class).in(Singleton.class);
        bind(EveManageDtoMapper.class).to(EveManageDtoMapperImpl.class).in(Singleton.class);
        bind(EveManageRemoteServiceImpl.class).in(Singleton.class);

        bind(StartApiKeyUpdateCronServlet.class).in(Singleton.class);
        bind(UpdateApiKeyTaskLauncher.class).in(Singleton.class);
        bind(UpdateApiKeyTaskServlet.class).in(Singleton.class);
        bind(AddBlueprintTaskServlet.class).in(Singleton.class);
        bind(AddBlueprintTaskLauncher.class).in(Singleton.class);
        bind(UpdateBlueprintTaskLauncher.class).in(Singleton.class);
        bind(UpdateBlueprintTaskServlet.class).in(Singleton.class);
        bind(UpdatePriceSetTaskLauncher.class).in(Singleton.class);
        bind(UpdatePriceSetTaskServlet.class).in(Singleton.class);
        bind(ClearCacheServlet.class).in(Singleton.class);

        bind(UserManagementService.class).to(UserManagementServiceImpl.class).in(Singleton.class);
        bind(UserSynchronizationService.class).to(UserSynchronizationServiceImpl.class).in(Singleton.class);

        bind(BlueprintManagementService.class).to(BlueprintManagementServiceImpl.class).in(Singleton.class);
        bind(PriceSetManagementService.class).to(PriceSetManagementServiceImpl.class).in(Singleton.class);

        bind(EveManagementService.class).to(EveManagementServiceImpl.class).in(Singleton.class);
        bind(EveApiDataService.class).to(EveApiDataServiceImpl.class).in(Singleton.class);
        bind(EveSynchronizationService.class).to(EveSynchronizationServiceImpl.class).in(Singleton.class);
        bind(CharacterSynchronizationService.class).to(CharacterSynchronizationServiceImpl.class).in(Singleton.class);
        bind(EveUpdateService.class).to(EveUpdateServiceImpl.class).in(Singleton.class);

        bind(CalculationService.class).to(CalculationServiceImpl.class).in(Singleton.class);

        bind(EveApiFacade.class).to(EveApiFacadeImpl.class).in(Singleton.class);
        bind(EveApiGateway.class).to(EveApiGatewayImpl.class).in(Singleton.class);
        bind(EveCentralApiFacade.class).to(EveCentralApiFacadeImpl.class).in(Singleton.class);
        bind(EveCentralApiGateway.class).to(EveCentralApiGatewayImpl.class).in(Singleton.class);
        bind(EveMetricsApiFacade.class).to(EveMetricsApiFacadeImpl.class).in(Singleton.class);
        bind(EveMetricsApiGateway.class).to(EveMetricsApiGatewayImpl.class).in(Singleton.class);
        bind(EveDbGateway.class).to(EveDbGatewayImpl.class).in(Singleton.class);

        bind(LoginActionRunner.class).to(LoginActionRunnerImpl.class).in(Singleton.class);
        bind(GetVersionsActionRunner.class).to(GetVersionsActionRunnerImpl.class).in(Singleton.class);

        bind(BlueprintsTabFirstLoadActionRunner.class).to(BlueprintsTabFirstLoadActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintAddActionRunner.class).to(BlueprintAddActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintSaveActionRunner.class).to(BlueprintSaveActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintDeleteActionRunner.class).to(BlueprintDeleteActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintGetDetailsActionRunner.class).to(BlueprintGetDetailsActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintsImportActionRunner.class).to(BlueprintsImportActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintsReloadActionRunner.class).to(BlueprintsReloadActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintsReloadForCorporationActionRunner.class).to(BlueprintsReloadForCorporationActionRunnerImpl.class).in(Singleton.class);
        bind(BlueprintsReloadForAllianceActionRunner.class).to(BlueprintsReloadForAllianceActionRunnerImpl.class).in(Singleton.class);

        bind(PriceSetTabFirstLoadActionRunner.class).to(PriceSetTabFirstLoadActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetAddItemActionRunner.class).to(PriceSetAddItemActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetCopyActionRunner.class).to(PriceSetCopyActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetCreateActionRunner.class).to(PriceSetCreateActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetDeleteActionRunner.class).to(PriceSetDeleteActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadActionRunner.class).to(PriceSetLoadActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadNamesActionRunner.class).to(PriceSetLoadNamesActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetRenameActionRunner.class).to(PriceSetRenameActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetSaveActionRunner.class).to(PriceSetSaveActionRunnerImpl.class).in(Singleton.class);
        bind(PriceFetchFromEveCentralActionRunner.class).to(PriceFetchFromEveCentralActionRunnerImpl.class).in(Singleton.class);
        bind(PriceFetchFromEveMetricsActionRunner.class).to(PriceFetchFromEveMetricsActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadForCorporationActionRunner.class).to(PriceSetLoadForCorporationActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadForAllianceActionRunner.class).to(PriceSetLoadForAllianceActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadCorporationNamesActionRunner.class).to(PriceSetLoadCorporationNamesActionRunnerImpl.class).in(Singleton.class);
        bind(PriceSetLoadAllianceNamesActionRunner.class).to(PriceSetLoadAllianceNamesActionRunnerImpl.class).in(Singleton.class);

        bind(PreferencesTabFirstLoadActionRunner.class).to(PreferencesTabFirstLoadActionRunnerImpl.class).in(Singleton.class);
        bind(PreferencesAddCharacterActionRunner.class).to(PreferencesAddCharacterActionRunnerImpl.class).in(Singleton.class);
        bind(PreferencesDeleteCharacterActionRunner.class).to(PreferencesDeleteCharacterActionRunnerImpl.class).in(Singleton.class);
        bind(PreferencesSetMainCharacterActionRunner.class).to(PreferencesSetMainCharacterActionRunnerImpl.class).in(Singleton.class);
        bind(PreferencesAddApiKeyActionRunner.class).to(PreferencesAddApiKeyActionRunnerImpl.class).in(Singleton.class);
        bind(PreferencesDeleteApiKeyActionRunner.class).to(PreferencesDeleteApiKeyActionRunnerImpl.class).in(Singleton.class);

        bind(QuickCalculatorTabFirstLoadActionRunner.class).to(QuickCalculatorTabFirstLoadActionRunnerImpl.class).in(Singleton.class);
        bind(QuickCalculatorSetActionRunner.class).to(QuickCalculatorSetActionRunnerImpl.class).in(Singleton.class);
        bind(QuickCalculatorUseBlueprintActionRunner.class).to(QuickCalculatorUseBlueprintActionRunnerImpl.class).in(Singleton.class);
        bind(QuickCalculatorUseAllBlueprintsActionRunner.class).to(QuickCalculatorUseAllBlueprintsActionRunnerImpl.class).in(Singleton.class);
        bind(QuickCalculatorFetchPricesFromEveCentralActionRunner.class).to(QuickCalculatorFetchPricesFromEveCentralActionRunnerImpl.class).in(Singleton.class);
        bind(QuickCalculatorFetchPricesFromEveMetricsActionRunner.class).to(QuickCalculatorFetchPricesFromEveMetricsActionRunnerImpl.class).in(Singleton.class);

        bind(SuggestBlueprintTypeActionRunner.class).to(SuggestBlueprintTypeActionRunnerImpl.class).in(Singleton.class);
        bind(SuggestTypeActionRunner.class).to(SuggestTypeActionRunnerImpl.class).in(Singleton.class);

        CachingAspect cachingAspect = new CachingAspect();
        LoggingAspect loggingAspect = new LoggingAspect();
        requestInjection(cachingAspect);
        bindInterceptor(any(), annotatedWith(Caching.class), cachingAspect);
        bindInterceptor(annotatedWith(Caching.class), any().and((Matcher<? super Object>) not(annotatedWith(Caching.class))), cachingAspect);
        bindInterceptor(any(), annotatedWith(Logging.class), loggingAspect);
        bindInterceptor(annotatedWith(Logging.class), any().and((Matcher<? super Object>) not(annotatedWith(Logging.class))), loggingAspect);

        try {
            Names.bindProperties(binder(), loadProperties());
        } catch (Exception e) {
            binder().addError(e);
        }
    }

    private Properties loadProperties() throws Exception {
        Properties properties = new Properties();
        URL url = getClass().getResource("/evemanage.properties");
        properties.load(url.openStream());
        return properties;
    }
}
