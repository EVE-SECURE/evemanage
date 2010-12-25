package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;

public class SkillsForCalculationSection implements QuickCalculatorTabPresenter.SkillsForCalculationSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private Label skillsSectionLabel;
    private FlexTable skillLevelTable;

    @Inject
    public SkillsForCalculationSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        skillsSectionLabel = new Label(messages.skills());
        skillsSectionLabel.addStyleName(resources.css().tabHeadingText());
        skillLevelTable = new FlexTable();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(skillsSectionLabel);
        container.add(skillLevelTable);
    }
}
