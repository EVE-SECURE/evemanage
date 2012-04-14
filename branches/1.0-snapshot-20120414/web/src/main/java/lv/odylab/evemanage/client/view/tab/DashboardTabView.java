package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.DashboardTabPresenter;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;
import lv.odylab.evemanage.client.widget.EveCorporationInfoLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;

public class DashboardTabView implements DashboardTabPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

    private HorizontalPanel headerPanel;
    private Label headerLabel;

    @Inject
    public DashboardTabView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.dashboard());
        headerLabel.addStyleName(resources.css().tabHeaderText());
    }

    @Override
    public void attach(HasWidgets container) {
        headerPanel.add(headerLabel);
        container.add(headerPanel);

        FlexTable welcomeTable = new FlexTable();
        welcomeTable.setWidget(0, 0, new HTML(messages.welcomeToEveManage() + "."));
        container.add(welcomeTable);

        Label tabsLabel = new Label(messages.tabNavigation());
        tabsLabel.addStyleName(resources.css().tabHeadingText());
        container.add(tabsLabel);

        FlexTable tabsTable = new FlexTable();
        FlexTable.FlexCellFormatter tabsTableFlexCellFormatter = tabsTable.getFlexCellFormatter();
        tabsTable.setWidget(1, 0, new Image(resources.dashboardTabIcon()));
        tabsTable.setWidget(1, 1, new HTML(messages.tabDashboard() + "."));
        tabsTable.setWidget(2, 0, new Image(resources.blueprintsTabIcon()));
        tabsTable.setWidget(2, 1, new HTML(messages.tabBlueprints() + "."));
        tabsTable.setWidget(3, 0, new Image(resources.priceSetTabIcon()));
        tabsTable.setWidget(3, 1, new HTML(messages.tabPriceSets() + "."));
        tabsTable.setWidget(4, 0, new Image(resources.preferencesTabIcon()));
        tabsTable.setWidget(4, 1, new HTML(messages.tabPreferences() + "."));
        tabsTable.setWidget(5, 0, new Image(resources.quickCalculatorTabIcon()));
        tabsTable.setWidget(5, 1, new HTML(messages.tabQuickCalculator() + "."));
        tabsTable.setWidget(6, 0, new Image(resources.aboutTabIcon()));
        tabsTable.setWidget(6, 1, new HTML(messages.tabAbout() + "."));
        tabsTableFlexCellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(5, 0, HasVerticalAlignment.ALIGN_TOP);
        tabsTableFlexCellFormatter.setVerticalAlignment(6, 0, HasVerticalAlignment.ALIGN_TOP);
        container.add(tabsTable);

        Label igbIntegrationLabel = new Label(messages.igbIntegration());
        igbIntegrationLabel.addStyleName(resources.css().tabHeadingText());
        container.add(igbIntegrationLabel);

        FlexTable igbIntegrationTable = new FlexTable();
        FlexTable.FlexCellFormatter igbIntegrationTableFlexCellFormatter = igbIntegrationTable.getFlexCellFormatter();
        igbIntegrationTable.setWidget(0, 0, new HTML(messages.igbExamples() + ":"));
        igbIntegrationTableFlexCellFormatter.setColSpan(0, 0, 5);
        Image crystallineCarbonideArmorPlateImage = new Image(resources.dashboardCrystallineCarbonideArmorPlateImage());
        crystallineCarbonideArmorPlateImage.setTitle("Crystalline Carbonide Armor Plate");
        crystallineCarbonideArmorPlateImage.addStyleName(resources.css().image32());
        EveItemInfoLink crystallineCarbonideArmorPlateItemInfoLink = new EveItemInfoLink(ccpJsMessages, crystallineCarbonideArmorPlateImage, 11545L);
        Image crystallineCarbonideImage = new Image(resources.dashboardCrystallineCarbonideImage());
        crystallineCarbonideImage.setTitle("Crystalline Carbonide");
        crystallineCarbonideImage.addStyleName(resources.css().image32());
        EveItemInfoLink crystallineCarbonideItemInfoLink = new EveItemInfoLink(ccpJsMessages, crystallineCarbonideImage, 16670L);
        Image tritaniumImage = new Image(resources.dashboardTritaniumImage());
        tritaniumImage.setTitle("Tritanium");
        tritaniumImage.addStyleName(resources.css().image16());
        EveItemInfoLink tritaniumImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, tritaniumImage, 34L);
        Image pyeriteImage = new Image(resources.dashboardPyeriteImage());
        pyeriteImage.setTitle("Pyerite");
        pyeriteImage.addStyleName(resources.css().image16());
        EveItemInfoLink pyeriteImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, pyeriteImage, 35L);
        igbIntegrationTable.setWidget(1, 0, crystallineCarbonideArmorPlateItemInfoLink);
        igbIntegrationTable.setWidget(1, 1, crystallineCarbonideItemInfoLink);
        igbIntegrationTable.setWidget(1, 2, tritaniumImageItemInfoLink);
        igbIntegrationTable.setWidget(1, 3, pyeriteImageItemInfoLink);
        igbIntegrationTable.setWidget(1, 4, new HTML(messages.igbClickingOnIcons() + "."));
        igbIntegrationTableFlexCellFormatter.setWidth(1, 2, "16px");
        igbIntegrationTableFlexCellFormatter.setWidth(1, 3, "16px");
        Image crystallineCarbonideArmorPlateBlueprintImage = new Image(resources.dashboardCrystallineCarbonideArmorPlateBlueprintImage());
        crystallineCarbonideArmorPlateBlueprintImage.setTitle("Crystalline Carbonide Armor Plate Blueprint");
        crystallineCarbonideArmorPlateBlueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink crystallineCarbonideArmorPlateBlueprintItemInfoLink = new EveItemInfoLink(ccpJsMessages, crystallineCarbonideArmorPlateBlueprintImage, 17323L, 2066929088L);
        Image damageControlBlueprintImage = new Image(resources.dashboardDamageControlBlueprintImage());
        damageControlBlueprintImage.setTitle("Damage Control I Blueprint");
        damageControlBlueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink damageControlBlueprintItemInfoLink = new EveItemInfoLink(ccpJsMessages, damageControlBlueprintImage, 2047L);
        igbIntegrationTable.setWidget(2, 0, crystallineCarbonideArmorPlateBlueprintItemInfoLink);
        igbIntegrationTable.setWidget(2, 1, damageControlBlueprintItemInfoLink);
        igbIntegrationTable.setWidget(2, 2, new HTML(messages.igbBlueprint() + "."));
        igbIntegrationTableFlexCellFormatter.setColSpan(2, 2, 3);
        Image corporationImage = new Image(resources.dashboardCorporationImage());
        corporationImage.setTitle("Odylab Research");
        corporationImage.addStyleName(resources.css().image32());
        EveCorporationInfoLink corporationItemInfoLink = new EveCorporationInfoLink(constants, urlMessages, ccpJsMessages, corporationImage, 1214825692L);
        Image characterImage = new Image(resources.dashboardCharacterImage());
        characterImage.setTitle("pcydo");
        characterImage.addStyleName(resources.css().image32());
        EveCharacterInfoLink characterItemInfoLink = new EveCharacterInfoLink(ccpJsMessages, characterImage, 515300277L);
        igbIntegrationTable.setWidget(3, 0, corporationItemInfoLink);
        igbIntegrationTable.setWidget(3, 1, characterItemInfoLink);
        igbIntegrationTable.setWidget(3, 2, new HTML(messages.igbClickingOnCorporation() + "."));
        igbIntegrationTableFlexCellFormatter.setColSpan(3, 2, 3);
        igbIntegrationTable.setWidget(4, 0, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, "Rifter", 587L));
        igbIntegrationTable.setWidget(4, 1, new HTML(messages.igbClickingOnLinks() + "."));
        igbIntegrationTableFlexCellFormatter.setColSpan(4, 0, 2);
        igbIntegrationTableFlexCellFormatter.setColSpan(4, 1, 3);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_TOP);
        igbIntegrationTableFlexCellFormatter.setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_TOP);
        container.add(igbIntegrationTable);

        Label contributeLabel = new Label(messages.contribute());
        contributeLabel.addStyleName(resources.css().tabHeadingText());
        container.add(contributeLabel);

        FlexTable contributeTable = new FlexTable();
        contributeTable.setWidget(0, 0, new HTML(messages.contributeBug() + "."));
        contributeTable.setWidget(1, 0, new HTML(messages.contributeFeature() + "."));
        container.add(contributeTable);
    }
}
