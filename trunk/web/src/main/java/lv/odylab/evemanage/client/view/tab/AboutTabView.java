package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.tab.AboutTabPresenter;

public class AboutTabView implements AboutTabPresenter.Display {
    private EveManageResources resources;
    private EveManageMessages messages;

    private HorizontalPanel headerPanel;
    private Label headerLabel;

    @Inject
    public AboutTabView(EveManageResources resources, EveManageMessages messages) {
        this.resources = resources;
        this.messages = messages;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.about());
        headerLabel.addStyleName(resources.css().tabHeaderText());
    }

    @Override
    public void attach(HasWidgets container) {
        headerPanel.add(headerLabel);
        container.add(headerPanel);

        FlexTable welcomeTable = new FlexTable();
        welcomeTable.setWidget(0, 0, new HTML(messages.thisIsEveManage() + "."));
        container.add(welcomeTable);

        Label featuresLabel = new Label(messages.features());
        featuresLabel.addStyleName(resources.css().tabHeadingText());
        container.add(featuresLabel);

        FlexTable featuresTable = new FlexTable();
        featuresTable.setWidget(0, 0, new HTML(messages.featureBlueprints() + "."));
        featuresTable.setWidget(1, 0, new HTML(messages.featurePriceSets() + "."));
        featuresTable.setWidget(2, 0, new HTML(messages.featureApiBased() + "."));
        featuresTable.setWidget(3, 0, new HTML(messages.featureRecursiveCalculations() + "."));
        featuresTable.setWidget(4, 0, new HTML(messages.featureIgbIntegration() + "."));
        featuresTable.setWidget(5, 0, new HTML(messages.featurePowerOfWeb() + "."));
        featuresTable.setWidget(6, 0, new HTML(messages.featureOpenID() + "."));
        featuresTable.setWidget(7, 0, new HTML(messages.featureAppEngine() + "."));
        container.add(featuresTable);

        Label whatNowLabel = new Label(messages.whatNow());
        whatNowLabel.addStyleName(resources.css().tabHeadingText());
        container.add(whatNowLabel);

        FlexTable whatNowTable = new FlexTable();
        whatNowTable.setWidget(0, 0, new HTML(messages.whatNowCalculator() + "."));
        container.add(whatNowTable);
    }
}
