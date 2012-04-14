package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;

public class DirectLinkSection implements QuickCalculatorTabPresenter.DirectLinkSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

    private Label directLinkSectionLabel;
    private FlexTable directLinkTable;
    private VerticalPanel directLinkPanel;
    private Button createDirectLinkButton;

    @Inject
    public DirectLinkSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

        directLinkSectionLabel = new Label(messages.directLinks());
        directLinkSectionLabel.addStyleName(resources.css().tabHeadingText());
        directLinkTable = new FlexTable();
        directLinkPanel = new VerticalPanel();
        createDirectLinkButton = new Button(messages.createDirectLink());
        createDirectLinkButton.setEnabled(false);
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(directLinkSectionLabel);
        directLinkTable.setWidget(0, 0, createDirectLinkButton);
        directLinkTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        directLinkTable.setWidget(0, 1, new Label(messages.sorryTemporaryDisabled()));
        directLinkTable.setWidget(0, 2, directLinkPanel);
        container.add(directLinkTable);
    }

    @Override
    public Button getCreateDirectLinkButton() {
        return createDirectLinkButton;
    }

    @Override
    public void createDirectLink() {
        /*String blueprintTypeName = enterBlueprintSuggestBox.getText();
        String meLevel = editableCalculation.getMeTextBox().getText();
        String peLevel = editableCalculation.getPeTextBox().getText();
        String quantity = editableCalculation.getQuantityTextBox().getText();
        StringBuilder directUrlNameStringBuilder = new StringBuilder(blueprintTypeName);
        directUrlNameStringBuilder.append(", ME:").append(meLevel).append(", PE:").append(peLevel).append(", Q:").append(quantity);

        CalculationExpression calculationExpression = new CalculationExpression();
        calculationExpression.setBlueprintTypeName(blueprintTypeName);
        calculationExpression.setMeLevel(Integer.valueOf(meLevel));
        calculationExpression.setPeLevel(Integer.valueOf(peLevel));
        calculationExpression.setQuantity(Long.valueOf(quantity));
        calculationTree.populateCalculationExpressionWithBlueprintInformation(calculationExpression);
        calculationTree.populateCalculationExpressionWithPriceInformation(calculationExpression);
        String url = "#" + constants.quickCalculatorToken() + calculationExpression.getExpression();
        directLinkPanel.insert(new Anchor(directUrlNameStringBuilder.toString(), url), 0);*/
    }
}
