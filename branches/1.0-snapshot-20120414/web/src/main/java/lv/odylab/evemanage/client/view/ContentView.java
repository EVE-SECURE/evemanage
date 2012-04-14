package lv.odylab.evemanage.client.view;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.ContentPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentView implements ContentPresenter.Display {
    private TabPanel tabPanel;
    private Panel dashboardTab;
    private Panel blueprintsTab;
    private Panel priceSetTab;
    private Panel quickCalculatorTab;
    private Panel preferencesTab;
    private Panel aboutTab;
    private Map<String, Panel> tabMap;
    private Map<String, Image> tabIconMap;

    @Inject
    public ContentView(EveManageResources resources, EveManageConstants constants, EveManageMessages messages) {
        tabPanel = new DecoratedTabPanel();
        tabPanel.addStyleName(resources.css().contentPanel());

        dashboardTab = new VerticalPanel();

        Image blueprintsTabSpinnerImage = new Image(resources.spinnerIcon());
        blueprintsTabSpinnerImage.setTitle(messages.loading());
        blueprintsTab = new VerticalPanel();
        blueprintsTab.add(blueprintsTabSpinnerImage);

        Image priceSetTabSpinnerImage = new Image(resources.spinnerIcon());
        priceSetTabSpinnerImage.setTitle(messages.loading());
        priceSetTab = new VerticalPanel();
        priceSetTab.add(priceSetTabSpinnerImage);

        Image quickCalculatorTabSpinnerImage = new Image(resources.spinnerIcon());
        quickCalculatorTabSpinnerImage.setTitle(messages.loading());
        quickCalculatorTab = new VerticalPanel();
        quickCalculatorTab.add(quickCalculatorTabSpinnerImage);

        Image preferencesTabSpinnerImage = new Image(resources.spinnerIcon());
        preferencesTabSpinnerImage.setTitle(messages.loading());
        preferencesTab = new VerticalPanel();
        preferencesTab.add(preferencesTabSpinnerImage);

        aboutTab = new VerticalPanel();

        tabMap = new HashMap<String, Panel>();
        tabMap.put(constants.dashboardToken(), dashboardTab);
        tabMap.put(constants.blueprintsToken(), blueprintsTab);
        tabMap.put(constants.priceSetToken(), priceSetTab);
        tabMap.put(constants.quickCalculatorToken(), quickCalculatorTab);
        tabMap.put(constants.preferencesToken(), preferencesTab);
        tabMap.put(constants.aboutToken(), aboutTab);

        Image dashboardTabIcon = new Image(resources.dashboardTabIcon());
        dashboardTabIcon.setTitle(messages.dashboard());
        Image blueprintsTabIcon = new Image(resources.blueprintsTabIcon());
        blueprintsTabIcon.setTitle(messages.blueprints());
        Image priceSetTabIcon = new Image(resources.priceSetTabIcon());
        priceSetTabIcon.setTitle(messages.priceSets());
        Image quickCalculatorTabIcon = new Image(resources.quickCalculatorTabIcon());
        quickCalculatorTabIcon.setTitle(messages.quickCalculator());
        Image preferencesTabIcon = new Image(resources.preferencesTabIcon());
        preferencesTabIcon.setTitle(messages.preferences());
        Image aboutTabIcon = new Image(resources.aboutTabIcon());
        aboutTabIcon.setTitle(messages.about());

        tabIconMap = new HashMap<String, Image>();
        tabIconMap.put(constants.dashboardToken(), dashboardTabIcon);
        tabIconMap.put(constants.blueprintsToken(), blueprintsTabIcon);
        tabIconMap.put(constants.priceSetToken(), priceSetTabIcon);
        tabIconMap.put(constants.quickCalculatorToken(), quickCalculatorTabIcon);
        tabIconMap.put(constants.preferencesToken(), preferencesTabIcon);
        tabIconMap.put(constants.aboutToken(), aboutTabIcon);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(tabPanel);
    }

    @Override
    public void setTabs(List<String> tabNames) {
        for (String tabName : tabNames) {
            tabPanel.add(tabMap.get(tabName), tabIconMap.get(tabName));
        }
    }

    @Override
    public void setSelectedTab(Integer selectedTabIndex) {
        tabPanel.getTabBar().selectTab(selectedTabIndex);
    }

    @Override
    public HasSelectionHandlers<Integer> getTabSelectionHandlers() {
        return tabPanel.getTabBar();
    }

    @Override
    public HasWidgets getDashboardTabContainer() {
        return dashboardTab;
    }

    @Override
    public HasWidgets getBlueprintsTabContainer() {
        return blueprintsTab;
    }

    @Override
    public HasWidgets getPriceSetTabContainer() {
        return priceSetTab;
    }

    @Override
    public HasWidgets getQuickCalculatorTabContainer() {
        return quickCalculatorTab;
    }

    @Override
    public HasWidgets getPreferencesTabContainer() {
        return preferencesTab;
    }

    @Override
    public HasWidgets getAboutTabContainer() {
        return aboutTab;
    }
}
