package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTree;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;

import java.util.HashMap;
import java.util.Map;

public class BlueprintItemTreeSection implements QuickCalculatorTabPresenter.BlueprintItemTreeSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private Label blueprintCostLabel;
    private FlexTable blueprintCostItemTable;

    private BlueprintItemTree blueprintItemTree;
    private Map<CalculationDto, Integer> calculationToBlueprintCostRowMap;

    @Inject
    public BlueprintItemTreeSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        blueprintCostLabel = new Label(messages.blueprintCost());
        blueprintCostLabel.addStyleName(resources.css().tabHeadingText());
        blueprintCostItemTable = new FlexTable();

        blueprintItemTree = new BlueprintItemTree();
        calculationToBlueprintCostRowMap = new HashMap<CalculationDto, Integer>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(blueprintCostLabel);
        container.add(blueprintCostItemTable);
    }

    @Override
    public BlueprintItemTree getBlueprintItemTree() {
        return blueprintItemTree;
    }

    @Override
    public void drawBlueprintItemTree(BlueprintItemTree blueprintItemTree) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public void cleanBlueprintItemTree() {
        blueprintCostItemTable.removeAllRows();
    }

    private void drawBlueprintCostItems() {
        /*drawBlueprintCostItem(calculation);
        for (CalculationDto calculation : pathNodesStringToUsedCalculationMap.values()) {
            drawBlueprintCostItem(calculation);
        }*/
    }

    private void drawBlueprintCostItem(CalculationDto calculation) {
        int index = blueprintCostItemTable.getRowCount();
        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(calculation.getBlueprintTypeID());
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink blueprintImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, calculation.getBlueprintTypeID());
        blueprintCostItemTable.setWidget(index, 0, blueprintImageItemInfoLink);
        blueprintCostItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculation.getBlueprintTypeName(), calculation.getBlueprintTypeID()));
    }
}
