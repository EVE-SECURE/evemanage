package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.oracle.BlueprintTypeSuggestOracle;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationTreeNodeSummary;
import lv.odylab.evemanage.client.presenter.tab.calculator.ComputableCalculation;
import lv.odylab.evemanage.client.presenter.tab.calculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.ComputableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculation;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.EditableCalculationPriceSetItem;
import lv.odylab.evemanage.client.presenter.tab.calculator.PricingProcessor;
import lv.odylab.evemanage.client.presenter.tab.calculator.PricingProcessorResult;
import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.DamagePerJobLabel;
import lv.odylab.evemanage.client.widget.EveCentralQuicklookLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.EveMetricsItemPriceLink;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.PriceTextBox;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuickCalculatorTabView implements QuickCalculatorTabPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;
    private PricingProcessor pricingProcessor;
    private EveCalculator calculator;

    private HorizontalPanel headerPanel;
    private Label headerLabel;
    private Image spinnerImage;

    private FlexTable errorMessageTable;
    private Label errorMessageLabel;
    private Image errorImage;

    private FlexTable enterBlueprintTable;
    private Label enterBlueprintNameLabel;
    private SuggestBox enterBlueprintSuggestBox;
    private Button setButton;

    private FlexTable blueprintInfoTable;
    private FlexTable rootCalculationItemTable;
    private FlexTable.FlexCellFormatter rootCalculationItemTableFlexFormatter;
    private HTMLTable.RowFormatter rootCalculationItemTableRowFormatter;

    private Label blueprintsCostLabel;
    private FlexTable blueprintsCostItemTable;

    private Label priceSetLabel;
    private FlexTable priceSetItemTable;

    private FlexTable applyAndFetchPricesTable;
    private Button applyButton;
    private Button fetchEveCentralPricesButton;
    private Button fetchEveMetricsPricesButton;
    private Label notePricesAreTakenFrom;
    private Label noteQuickCalculatorIsIntended;

    private FlexTable directLinkTable;
    private VerticalPanel directLinkPanel;
    private Button createDirectLinkButton;

    private CalculationTree calculationTree;

    private EditableCalculation editableCalculation;
    private ComputableCalculation computableCalculation;
    private Map<String, EditableCalculationItem> pathNodesStringToEditableCalculationItemMap;
    private Map<String, ComputableCalculationItem> pathNodesStringToComputableCalculationItemMap;
    private Map<String, CalculationDto> pathNodesStringToUsedCalculationMap;
    private Map<Long, EditableCalculationPriceSetItem> typeIdToEditableCalculationPriceSetItemMap;
    private Map<Long, ComputableCalculationPriceSetItem> typeIdToComputableCalculationPriceSetItemMap;
    private Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap;

    private List<HandlerRegistration> handlerRegistrations;

    @Inject
    public QuickCalculatorTabView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider, PricingProcessor pricingProcessor, EveCalculator calculator, BlueprintTypeSuggestOracle blueprintTypeSuggestOracle) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;
        this.pricingProcessor = pricingProcessor;
        this.calculator = calculator;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.quickCalculator());
        headerLabel.addStyleName(resources.css().tabHeaderText());
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().tabHeaderSpinner());

        errorMessageTable = new FlexTable();
        errorMessageLabel = new Label();
        errorMessageLabel.addStyleName(resources.css().errorLabel());
        errorImage = new Image(resources.errorIcon());
        errorImage.setTitle(messages.error());

        enterBlueprintTable = new FlexTable();
        enterBlueprintSuggestBox = new SuggestBox(blueprintTypeSuggestOracle);
        enterBlueprintSuggestBox.addStyleName(resources.css().blueprintNameInput());
        enterBlueprintSuggestBox.getTextBox().setEnabled(false);
        enterBlueprintNameLabel = new Label(messages.enterBlueprintName() + ":");
        setButton = new Button(messages.set());
        setButton.setEnabled(false);

        blueprintInfoTable = new FlexTable();
        rootCalculationItemTable = new FlexTable();
        rootCalculationItemTableFlexFormatter = rootCalculationItemTable.getFlexCellFormatter();
        rootCalculationItemTableRowFormatter = rootCalculationItemTable.getRowFormatter();

        blueprintsCostLabel = new Label(messages.blueprintsCost());
        blueprintsCostLabel.addStyleName(resources.css().tabHeadingText());
        blueprintsCostItemTable = new FlexTable();

        priceSetLabel = new Label(messages.prices());
        priceSetLabel.addStyleName(resources.css().tabHeadingText());
        priceSetItemTable = new FlexTable();

        applyAndFetchPricesTable = new FlexTable();
        applyButton = new Button(messages.apply());
        applyButton.setEnabled(false);
        fetchEveCentralPricesButton = new Button(messages.fetchPricesFromEveCentral());
        fetchEveCentralPricesButton.setEnabled(false);
        fetchEveMetricsPricesButton = new Button(messages.orEveMetrics());
        fetchEveMetricsPricesButton.setEnabled(false);
        notePricesAreTakenFrom = new Label(messages.notePricesAreTakenFrom() + ".");
        notePricesAreTakenFrom.addStyleName(resources.css().noteLabel());
        noteQuickCalculatorIsIntended = new Label(messages.noteQuickCalculatorIsIntended() + ".");
        noteQuickCalculatorIsIntended.addStyleName(resources.css().noteLabel());

        directLinkTable = new FlexTable();
        directLinkPanel = new VerticalPanel();
        createDirectLinkButton = new Button("Create direct link");
        createDirectLinkButton.setEnabled(false);

        calculationTree = new CalculationTree();
        computableCalculation = new ComputableCalculation();
        editableCalculation = new EditableCalculation();
        pathNodesStringToEditableCalculationItemMap = new HashMap<String, EditableCalculationItem>();
        pathNodesStringToComputableCalculationItemMap = new HashMap<String, ComputableCalculationItem>();
        pathNodesStringToUsedCalculationMap = new HashMap<String, CalculationDto>();
        typeIdToEditableCalculationPriceSetItemMap = new HashMap<Long, EditableCalculationPriceSetItem>();
        typeIdToComputableCalculationPriceSetItemMap = new HashMap<Long, ComputableCalculationPriceSetItem>();
        existingTypeIdToCalculationPriceSetItemMap = new TreeMap<Long, CalculationPriceSetItemDto>();

        handlerRegistrations = new ArrayList<HandlerRegistration>();
    }

    @Override
    public void attach(HasWidgets container) {
        headerPanel.add(headerLabel);
        headerPanel.add(spinnerImage);
        headerPanel.setCellVerticalAlignment(spinnerImage, HasVerticalAlignment.ALIGN_MIDDLE);
        headerPanel.setCellVerticalAlignment(headerLabel, HasVerticalAlignment.ALIGN_MIDDLE);
        container.add(headerPanel);

        errorMessageTable.setVisible(false);
        errorMessageTable.setWidget(0, 0, errorImage);
        errorMessageTable.setWidget(0, 1, errorMessageLabel);
        container.add(errorMessageTable);

        enterBlueprintTable.setWidget(0, 0, enterBlueprintNameLabel);
        enterBlueprintTable.setWidget(0, 1, enterBlueprintSuggestBox);
        enterBlueprintTable.setWidget(0, 2, setButton);

        container.add(enterBlueprintTable);

        container.add(blueprintInfoTable);
        container.add(rootCalculationItemTable);

        //container.add(blueprintsCostLabel);
        //container.add(blueprintsCostItemTable);

        container.add(priceSetLabel);
        container.add(priceSetItemTable);

        applyAndFetchPricesTable.setWidget(0, 0, applyButton);
        applyAndFetchPricesTable.setWidget(0, 1, fetchEveCentralPricesButton);
        applyAndFetchPricesTable.setWidget(0, 2, fetchEveMetricsPricesButton);
        container.add(applyAndFetchPricesTable);
        container.add(notePricesAreTakenFrom);
        container.add(noteQuickCalculatorIsIntended);

        directLinkTable.setWidget(0, 0, createDirectLinkButton);
        directLinkTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        directLinkTable.setWidget(0, 1, directLinkPanel);
        container.add(directLinkTable);
    }

    @Override
    public Widget getSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public Widget getErrorContainer() {
        return errorMessageTable;
    }

    @Override
    public HasText getErrorMessageLabel() {
        return errorMessageLabel;
    }

    @Override
    public SuggestBox getBlueprintSuggestBox() {
        return enterBlueprintSuggestBox;
    }

    @Override
    public Button getSetButton() {
        return setButton;
    }

    @Override
    public Button getApplyButton() {
        return applyButton;
    }

    @Override
    public Button getFetchEveCentralPricesButton() {
        return fetchEveCentralPricesButton;
    }

    @Override
    public Button getFetchEveMetricsPricesButton() {
        return fetchEveMetricsPricesButton;
    }

    @Override
    public Button getCreateDirectLinkButton() {
        return createDirectLinkButton;
    }

    @Override
    public void createDirectLink() {
        String blueprintTypeName = enterBlueprintSuggestBox.getText();
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
        directLinkPanel.insert(new Anchor(directUrlNameStringBuilder.toString(), url), 0);
    }

    @Override
    public void showBlueprintDetails(EditableCalculationItem editableCalculationItem) {
        FlexTable calculationItemTable = editableCalculationItem.getCalculationItemTable();
        Integer index = editableCalculationItem.getIndex();
        if (calculationItemTable == null) {
            rootCalculationItemTableFlexFormatter.setVisible(index, 9, true);
            rootCalculationItemTableFlexFormatter.setVisible(index, 10, true);
            rootCalculationItemTableFlexFormatter.setVisible(index, 11, true);
            rootCalculationItemTableFlexFormatter.setVisible(index, 12, true);
            rootCalculationItemTableFlexFormatter.setVisible(index, 13, true);
            rootCalculationItemTableFlexFormatter.setVisible(index, 14, true);
        } else {
            FlexTable.FlexCellFormatter calculationItemTableFlexCellFormatter = calculationItemTable.getFlexCellFormatter();
            calculationItemTableFlexCellFormatter.setVisible(index, 13, true);
            calculationItemTableFlexCellFormatter.setVisible(index, 14, true);
            calculationItemTableFlexCellFormatter.setVisible(index, 15, true);
            calculationItemTableFlexCellFormatter.setVisible(index, 16, true);
            calculationItemTableFlexCellFormatter.setVisible(index, 17, true);
            calculationItemTableFlexCellFormatter.setVisible(index, 18, true);
        }
    }

    @Override
    public void hideBlueprintDetails(EditableCalculationItem editableCalculationItem) {
        FlexTable calculationItemTable = editableCalculationItem.getCalculationItemTable();
        Integer index = editableCalculationItem.getIndex();
        if (calculationItemTable == null) {
            rootCalculationItemTableFlexFormatter.setVisible(index, 9, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 10, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 11, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 12, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 13, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 14, false);
        } else {
            FlexTable.FlexCellFormatter calculationItemTableFlexCellFormatter = calculationItemTable.getFlexCellFormatter();
            calculationItemTableFlexCellFormatter.setVisible(index, 13, false);
            calculationItemTableFlexCellFormatter.setVisible(index, 14, false);
            calculationItemTableFlexCellFormatter.setVisible(index, 15, false);
            calculationItemTableFlexCellFormatter.setVisible(index, 16, false);
            calculationItemTableFlexCellFormatter.setVisible(index, 17, false);
            calculationItemTableFlexCellFormatter.setVisible(index, 18, false);
        }
    }

    @Override
    public void hideDetailsTable(EditableCalculationItem editableCalculationItem) {
        FlexTable calculationItemTable = editableCalculationItem.getCalculationItemTable();
        Integer index = editableCalculationItem.getIndex();
        if (calculationItemTable == null) {
            rootCalculationItemTableRowFormatter.setVisible(index + 1, false);
        } else {
            calculationItemTable.getRowFormatter().setVisible(index + 1, false);
        }
    }

    @Override
    public EditableCalculation getEditableCalculation() {
        return editableCalculation;
    }

    @Override
    public Map<String, EditableCalculationItem> getPathNodesStringToEditableCalculationItemMap() {
        return pathNodesStringToEditableCalculationItemMap;
    }

    @Override
    public Map<String, ComputableCalculationItem> getPathNodesStringToComputableCalculationItemMap() {
        return pathNodesStringToComputableCalculationItemMap;
    }

    @Override
    public Map<String, CalculationDto> getPathNodesStringToUsedCalculationMap() {
        return pathNodesStringToUsedCalculationMap;
    }

    @Override
    public Map<Long, EditableCalculationPriceSetItem> getTypeIdToEditableCalculationPriceSetItemMap() {
        return typeIdToEditableCalculationPriceSetItemMap;
    }

    @Override
    public Map<Long, CalculationPriceSetItemDto> getExistingTypeIdToCalculationPriceSetItemMap() {
        return existingTypeIdToCalculationPriceSetItemMap;
    }

    @Override
    public void setNewCalculation(CalculationDto calculation) {
        Map<Long, CalculationPriceSetItemDto> existingCalculationPriceSetItemDtoMap = createExistingTypeIdToCalculationPriceSetItemMap();
        blueprintInfoTable.removeAllRows();
        rootCalculationItemTable.removeAllRows();
        priceSetItemTable.removeAllRows();
        pathNodesStringToEditableCalculationItemMap.clear();
        pathNodesStringToComputableCalculationItemMap.clear();
        pathNodesStringToUsedCalculationMap.clear();
        typeIdToEditableCalculationPriceSetItemMap.clear();
        typeIdToComputableCalculationPriceSetItemMap.clear();
        calculationTree.removeAllNodes();
        calculationTree.build(calculation);

        computableCalculation.setCalculation(calculation);

        PricingProcessorResult pricingProcessorResult = pricingProcessor.process(1L, calculationTree, existingCalculationPriceSetItemDtoMap);
        Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap = pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        drawCalculationTree();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();
    }

    @Override
    public List<String> addCalculationTreeNode(Long[] pathNodes, CalculationDto calculation) {
        Map<Long[], CalculationDto> pathNodesToCalculationMap = new HashMap<Long[], CalculationDto>();
        pathNodesToCalculationMap.put(pathNodes, calculation);
        return addCalculationTreeNodes(pathNodesToCalculationMap);
    }

    @Override
    public List<String> addCalculationTreeNodes(Map<Long[], CalculationDto> pathNodesToCalculationMap) {
        List<String> pathNodesStringsWithBlueprint = new ArrayList<String>();
        for (Map.Entry<Long[], CalculationDto> mapEntry : pathNodesToCalculationMap.entrySet()) {
            Long[] pathNodes = mapEntry.getKey();
            CalculationDto calculation = mapEntry.getValue();
            String pathNodesString = PathExpression.createPathNodesStringFromPathNodes(pathNodes);

            pathNodesStringToUsedCalculationMap.put(pathNodesString, calculation);
            EditableCalculationItem editableCalculationItem = pathNodesStringToEditableCalculationItemMap.get(pathNodesString);
            String meString = String.valueOf(calculation.getMaterialLevel());
            String peString = String.valueOf(calculation.getProductivityLevel());
            editableCalculationItem.getMeLabel().setText(meString);
            editableCalculationItem.getPeLabel().setText(peString);
            editableCalculationItem.getMeTextBox().setText(meString);
            editableCalculationItem.getPeTextBox().setText(peString);
            FlexTable table = editableCalculationItem.getCalculationItemDetailsTable();
            for (CalculationItemDto calculationItem : calculation.getItems()) {
                calculationTree.createNode(calculationItem);
                if (hasBlueprint(calculationItem.getItemCategoryID(), calculationItem.getItemTypeID())) {
                    pathNodesStringsWithBlueprint.add(calculationItem.getPathExpression().getPathNodesString());
                }
            }
            CalculationTreeNode node = calculationTree.getNodeByPathNodes(pathNodes);
            for (CalculationTreeNode calculationTreeNode : node.getNodeMap().values()) {
                //for (CalculationTreeNode treeNode : calculationTreeNode.getNodeMap().values()) {
                calculationTreeNode.changeMePe(calculation.getMaterialLevel(), calculation.getProductivityLevel());
                //}
                drawCalculationItem(table, calculationTreeNode);
            }
            showBlueprintDetails(editableCalculationItem);
        }

        PricingProcessorResult pricingProcessorResult = pricingProcessor.process(1L, calculationTree, createExistingTypeIdToCalculationPriceSetItemMap());
        Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap = pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap();
        priceSetItemTable.removeAllRows();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(typeIdToCalculationPriceSetItemMap.values()));
        recalculate();

        return pathNodesStringsWithBlueprint;
    }

    @Override
    public void excludeCalculationTreeNodeFromCalculation(Long[] pathNodes) {
        CalculationTreeNode calculationTreeNode = calculationTree.getNodeByPathNodes(pathNodes);
        calculationTreeNode.setExcludeChildNodesFromCalculation(true);
        PricingProcessorResult pricingProcessorResult = recalculate(createExistingTypeIdToCalculationPriceSetItemMap());
        priceSetItemTable.removeAllRows();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public void excludeCalculationTreeNodesFromCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            CalculationTreeNode calculationTreeNode = calculationTree.getNodeByPathNodes(pathNodes);
            calculationTreeNode.setExcludeChildNodesFromCalculation(true);
        }
        PricingProcessorResult pricingProcessorResult = recalculate(createExistingTypeIdToCalculationPriceSetItemMap());
        priceSetItemTable.removeAllRows();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public void includeCalculationTreeNodeInCalculation(Long[] pathNodes) {
        CalculationTreeNode calculationTreeNode = calculationTree.getNodeByPathNodes(pathNodes);
        calculationTreeNode.setExcludeChildNodesFromCalculation(false);
        PricingProcessorResult pricingProcessorResult = recalculate();
        priceSetItemTable.removeAllRows();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public void includeCalculationTreeNodesInCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            CalculationTreeNode calculationTreeNode = calculationTree.getNodeByPathNodes(pathNodes);
            calculationTreeNode.setExcludeChildNodesFromCalculation(false);
        }
        PricingProcessorResult pricingProcessorResult = recalculate(createExistingTypeIdToCalculationPriceSetItemMap());
        priceSetItemTable.removeAllRows();
        drawCalculationPriceSetItems(new ArrayList<CalculationPriceSetItemDto>(pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().values()));
    }

    @Override
    public void changeMePeQuantity(Integer meLevel, Integer peLevel, Long quantity) {
        editableCalculation.getMeLabel().setText(String.valueOf(meLevel));
        editableCalculation.getPeLabel().setText(String.valueOf(peLevel));
        editableCalculation.getQuantityLabel().setText(String.valueOf(quantity));
        editableCalculation.getMeTextBox().setText(String.valueOf(meLevel));
        editableCalculation.getPeTextBox().setText(String.valueOf(peLevel));
        editableCalculation.getQuantityTextBox().setText(String.valueOf(quantity));
        calculationTree.changeRootNodesMePeQuantity(meLevel, peLevel, quantity);
        computableCalculation.getCalculation().setMaterialLevel(meLevel);
        computableCalculation.getCalculation().setProductivityLevel(peLevel);
        recalculate();
    }

    @Override
    public void changeMePe(Long[] pathNodes, Integer meLevel, Integer peLevel) {
        CalculationTreeNode calculationTreeNode = calculationTree.getNodeByPathNodes(pathNodes);
        for (CalculationTreeNode node : calculationTreeNode.getNodeMap().values()) {
            node.changeMePe(meLevel, peLevel);
        }
        recalculate();
    }

    @Override
    public void updatePrices() {
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (Map.Entry<Long, EditableCalculationPriceSetItem> mapEntry : typeIdToEditableCalculationPriceSetItemMap.entrySet()) {
            Long typeID = mapEntry.getKey();
            BigDecimal price = mapEntry.getValue().getPriceTextBox().getPrice();
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = typeIdToComputableCalculationPriceSetItemMap.get(typeID);
            computableCalculationPriceSetItem.getCalculationPriceSetItem().setPrice(price);
            typeIdToPriceMap.put(typeID, price);
        }
        calculationTree.setPrices(typeIdToPriceMap);
        recalculate();
    }

    @Override
    public void updatePrices(Map<Long, BigDecimal> typeIdToPriceMap) {
        for (Map.Entry<Long, BigDecimal> mapEntry : typeIdToPriceMap.entrySet()) {
            Long typeID = mapEntry.getKey();
            BigDecimal price = mapEntry.getValue();
            typeIdToEditableCalculationPriceSetItemMap.get(typeID).getPriceTextBox().setPrice(price);
            typeIdToComputableCalculationPriceSetItemMap.get(typeID).getCalculationPriceSetItem().setPrice(price);
        }
        calculationTree.setPrices(typeIdToPriceMap);
        recalculate();
    }

    private PricingProcessorResult recalculate() {
        return recalculate(null);
    }

    private PricingProcessorResult recalculate(Map<Long, CalculationPriceSetItemDto> existingTypeIdToCalculationPriceSetItemMap) {
        Long quantity = Long.valueOf(editableCalculation.getQuantityTextBox().getText());
        PricingProcessorResult pricingProcessorResult = pricingProcessor.process(quantity, calculationTree, existingTypeIdToCalculationPriceSetItemMap);
        for (Map.Entry<String, ComputableCalculationItem> mapEntry : pathNodesStringToComputableCalculationItemMap.entrySet()) {
            ComputableCalculationItem computableCalculationItem = mapEntry.getValue();
            computableCalculationItem.recalculate();
        }
        for (Map.Entry<Long, CalculationPriceSetItemDto> mapEntry : pricingProcessorResult.getTypeIdToCalculationPriceSetItemMap().entrySet()) {
            CalculationPriceSetItemDto calculationPriceSetItemDto = mapEntry.getValue();
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = typeIdToComputableCalculationPriceSetItemMap.get(mapEntry.getKey());
            computableCalculationPriceSetItem.setCalculationPriceSetItem(calculationPriceSetItemDto);
            computableCalculationPriceSetItem.recalculate();
        }
        computableCalculation.getCalculation().setPrice(pricingProcessorResult.getTotalPrice());
        computableCalculation.recalculate(calculator);
        return pricingProcessorResult;
    }

    @Override
    public List<HandlerRegistration> getHandlerRegistrations() {
        return handlerRegistrations;
    }

    private void drawCalculationTree() {
        CalculationDto calculation = computableCalculation.getCalculation();
        drawBlueprintInformation(calculation);

        for (CalculationTreeNode calculationTreeNode : calculationTree.getNodeMap().values()) {
            drawRootCalculationItem(calculationTreeNode);
        }
    }

    private void drawCalculationPriceSetItems(List<CalculationPriceSetItemDto> calculationPriceSetItems) {
        for (CalculationPriceSetItemDto calculationPriceSetItemDto : calculationPriceSetItems) {
            drawCalculationPriceSetItem(calculationPriceSetItemDto);
        }
    }

    private void drawBlueprintInformation(CalculationDto calculation) {
        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(calculation.getBlueprintTypeID());
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.addStyleName(resources.css().image64());
        EveItemInfoLink blueprintImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, calculation.getBlueprintTypeID());
        blueprintInfoTable.setWidget(0, 0, blueprintImageItemInfoLink);
        String productImageUrl = imageUrlProvider.getImage64Url(calculation.getProductTypeCategoryID(), calculation.getProductTypeID(), calculation.getProductGraphicIcon());
        Image productImage = new Image(productImageUrl);
        productImage.addStyleName(resources.css().image64());
        EveItemInfoLink productImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, productImage, calculation.getProductTypeID());
        blueprintInfoTable.setWidget(0, 1, productImageItemInfoLink);

        FlexTable blueprintTable = new FlexTable();
        blueprintTable.setWidget(0, 0, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculation.getBlueprintTypeName(), calculation.getBlueprintTypeID()));
        blueprintTable.setWidget(0, 1, new Label(messages.me() + ":"));
        Label meLabel = new Label(String.valueOf(calculation.getMaterialLevel()));
        blueprintTable.setWidget(0, 2, meLabel);
        blueprintTable.setWidget(0, 3, new Label(messages.pe() + ":"));
        Label peLabel = new Label(String.valueOf(calculation.getProductivityLevel()));
        blueprintTable.setWidget(0, 4, peLabel);
        WasteLabel wasteLabel = new WasteLabel(messages);
        blueprintTable.setWidget(0, 5, wasteLabel);
        Button editButton = new Button(messages.edit());
        blueprintTable.setWidget(0, 6, editButton);
        OpaqueLoadableBlueprintImage useAllBlueprintsImage = new OpaqueLoadableBlueprintImage(resources, messages, messages.useAllBlueprints(), messages.stopUsingAllBlueprints());
        useAllBlueprintsImage.addStyleName(resources.css().image16());
        useAllBlueprintsImage.addStyleName(resources.css().cursorHand());
        useAllBlueprintsImage.setOpacity();
        blueprintTable.setWidget(0, 7, useAllBlueprintsImage);
        blueprintInfoTable.setWidget(0, 2, blueprintTable);

        FlexTable productTable = new FlexTable();
        QuantityLabel quantityLabel = new QuantityLabel(1L);
        productTable.setWidget(0, 0, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculation.getProductTypeName(), calculation.getProductTypeID()));
        productTable.setWidget(0, 1, new Label("x"));
        productTable.setWidget(0, 2, quantityLabel);
        productTable.setWidget(0, 3, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculation.getPrice());
        totalPriceLabel.addStyleName(resources.css().totalPriceLabel());
        productTable.setWidget(0, 4, totalPriceLabel);
        blueprintInfoTable.setWidget(1, 0, productTable);

        FlexTable.FlexCellFormatter imageTableCellFormatter = blueprintInfoTable.getFlexCellFormatter();
        imageTableCellFormatter.setRowSpan(0, 0, 3);
        imageTableCellFormatter.setRowSpan(0, 1, 3);
        imageTableCellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
        imageTableCellFormatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);

        TextBox meTextBox = new TextBox();
        meTextBox.setValue(meLabel.getText());
        meTextBox.addStyleName(resources.css().mePeInput());
        TextBox peTextBox = new TextBox();
        peTextBox.setValue(peLabel.getText());
        peTextBox.addStyleName(resources.css().mePeInput());
        final DecoratedPopupPanel editBlueprintInfoPopup = new DecoratedPopupPanel(true);
        FlexTable editBlueprintInfoPopupFlexTable = new FlexTable();
        editBlueprintInfoPopupFlexTable.setWidget(0, 0, new Label(messages.materialLevel() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(0, 1, meTextBox);
        editBlueprintInfoPopupFlexTable.setWidget(1, 0, new Label(messages.productivityLevel() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(1, 1, peTextBox);
        TextBox quantityTextBox = new TextBox();
        quantityTextBox.setValue("1");
        quantityTextBox.addStyleName(resources.css().quantityInput());
        editBlueprintInfoPopupFlexTable.setWidget(2, 0, new Label(messages.quantity() + ":"));
        editBlueprintInfoPopupFlexTable.setWidget(2, 1, quantityTextBox);
        Button applyButton = new Button(messages.apply());
        editBlueprintInfoPopupFlexTable.setWidget(3, 0, applyButton);
        editBlueprintInfoPopupFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
        editBlueprintInfoPopup.setWidget(editBlueprintInfoPopupFlexTable);

        handlerRegistrations.add(editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft() + 10;
                int top = source.getAbsoluteTop() + 10;
                editBlueprintInfoPopup.setPopupPosition(left, top);
                editBlueprintInfoPopup.show();
            }
        }));
        handlerRegistrations.add(applyButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editBlueprintInfoPopup.hide();
            }
        }));

        editableCalculation.setUseAllBlueprintsImage(useAllBlueprintsImage);
        editableCalculation.setMeLabel(meLabel);
        editableCalculation.setPeLabel(peLabel);
        editableCalculation.setQuantityLabel(quantityLabel);
        editableCalculation.setMeTextBox(meTextBox);
        editableCalculation.setPeTextBox(peTextBox);
        editableCalculation.setQuantityTextBox(quantityTextBox);
        editableCalculation.setApplyButton(applyButton);
        computableCalculation.setWasteLabel(wasteLabel);
        computableCalculation.setTotalPriceLabel(totalPriceLabel);
    }

    private void drawRootCalculationItem(CalculationTreeNode calculationTreeNode) {
        CalculationTreeNodeSummary calculationTreeNodeSummary = calculationTreeNode.getSummary();
        String pathNodesString = calculationTreeNodeSummary.getPathNodesString();
        EditableCalculationItem editableCalculationItem = new EditableCalculationItem();
        pathNodesStringToEditableCalculationItemMap.put(pathNodesString, editableCalculationItem);
        ComputableCalculationItem computableCalculationItem = new ComputableCalculationItem();
        computableCalculationItem.setCalculationItems(calculationTreeNode.getCalculationItems());
        computableCalculationItem.setCalculationTreeNodeSummary(calculationTreeNodeSummary);
        pathNodesStringToComputableCalculationItemMap.put(pathNodesString, computableCalculationItem);

        final int index = rootCalculationItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage32Url(calculationTreeNodeSummary.getItemCategoryID(), calculationTreeNodeSummary.getItemTypeID(), calculationTreeNodeSummary.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationTreeNodeSummary.getItemTypeName());
        image.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, calculationTreeNodeSummary.getItemTypeID());
        rootCalculationItemTable.setWidget(index, 0, imageItemInfoLink);
        rootCalculationItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationTreeNodeSummary.getItemTypeName(), calculationTreeNodeSummary.getItemTypeID()));
        rootCalculationItemTable.setWidget(index, 2, new Label("x"));
        QuantityLabel quantityForParentLabel = new QuantityLabel(calculationTreeNodeSummary.getParentQuantity() * calculationTreeNodeSummary.getQuantity());
        HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
        quantityAndDamagePerJobPanel.add(quantityForParentLabel);
        BigDecimal damagePerJob = calculationTreeNodeSummary.getDamagePerJob();
        if (BigDecimal.ONE.compareTo(damagePerJob) == 1) {
            DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
            damagePerJobLabel.addStyleName(resources.css().damagePerJob());
            quantityAndDamagePerJobPanel.add(damagePerJobLabel);
            quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
        }
        rootCalculationItemTable.setWidget(index, 3, quantityAndDamagePerJobPanel);
        rootCalculationItemTable.setWidget(index, 4, new Label("x"));
        PriceLabel priceLabel = new PriceLabel(calculationTreeNodeSummary.getPrice());
        rootCalculationItemTable.setWidget(index, 5, priceLabel);
        rootCalculationItemTable.setWidget(index, 6, new Label("="));
        PriceLabel totalPriceForParentLabel = new PriceLabel(calculationTreeNodeSummary.getTotalPriceForParent());
        rootCalculationItemTable.setWidget(index, 7, totalPriceForParentLabel);

        if (hasBlueprint(calculationTreeNodeSummary.getItemCategoryID(), calculationTreeNodeSummary.getItemTypeID())) {
            OpaqueLoadableBlueprintImage blueprintImage = new OpaqueLoadableBlueprintImage(resources, messages, messages.useBlueprint(), messages.stopUsingBlueprint());
            blueprintImage.addStyleName(resources.css().image16());
            blueprintImage.addStyleName(resources.css().cursorHand());
            blueprintImage.setOpacity();
            rootCalculationItemTable.setWidget(index, 8, blueprintImage);
            rootCalculationItemTable.setWidget(index, 9, new Label(messages.me() + ":"));
            Label meLabel = new Label();
            rootCalculationItemTable.setWidget(index, 10, meLabel);
            Label peLabel = new Label();
            rootCalculationItemTable.setWidget(index, 11, new Label(messages.pe() + ":"));
            rootCalculationItemTable.setWidget(index, 12, peLabel);
            Button editBlueprintButton = new Button(messages.edit());
            rootCalculationItemTable.setWidget(index, 13, editBlueprintButton);
            Button detailsBlueprintButton = new Button(messages.details());
            rootCalculationItemTable.setWidget(index, 14, detailsBlueprintButton);

            FlexTable calculationItemDetailsTable = new FlexTable();
            rootCalculationItemTable.setWidget(index + 1, 0, null);
            rootCalculationItemTable.setWidget(index + 1, 1, calculationItemDetailsTable);
            rootCalculationItemTableFlexFormatter.setVisible(index, 9, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 10, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 11, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 12, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 13, false);
            rootCalculationItemTableFlexFormatter.setVisible(index, 14, false);
            rootCalculationItemTableFlexFormatter.setColSpan(index + 1, 1, 14);
            rootCalculationItemTableRowFormatter.setVisible(index + 1, false);

            handlerRegistrations.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (rootCalculationItemTableRowFormatter.isVisible(index + 1)) {
                        rootCalculationItemTableRowFormatter.setVisible(index + 1, false);
                    } else {
                        rootCalculationItemTableRowFormatter.setVisible(index + 1, true);
                    }
                }
            }));

            TextBox meTextBox = new TextBox();
            meTextBox.setValue(meLabel.getText());
            meTextBox.addStyleName(resources.css().mePeInput());
            TextBox peTextBox = new TextBox();
            peTextBox.setValue(peLabel.getText());
            peTextBox.addStyleName(resources.css().mePeInput());
            final DecoratedPopupPanel editCalculationItemPopup = new DecoratedPopupPanel(true);
            FlexTable editCalculationItemPopupFlexTable = new FlexTable();
            editCalculationItemPopupFlexTable.setWidget(0, 0, new Label(messages.materialLevel() + ":"));
            editCalculationItemPopupFlexTable.setWidget(0, 1, meTextBox);
            editCalculationItemPopupFlexTable.setWidget(1, 0, new Label(messages.productivityLevel() + ":"));
            editCalculationItemPopupFlexTable.setWidget(1, 1, peTextBox);
            Button applyButton = new Button(messages.apply());
            editCalculationItemPopupFlexTable.setWidget(3, 0, applyButton);
            editCalculationItemPopupFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
            editCalculationItemPopup.setWidget(editCalculationItemPopupFlexTable);

            handlerRegistrations.add(editBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    editCalculationItemPopup.setPopupPosition(left, top);
                    editCalculationItemPopup.show();
                }
            }));
            handlerRegistrations.add(applyButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    editCalculationItemPopup.hide();
                }
            }));

            editableCalculationItem.setIndex(index);
            editableCalculationItem.setBlueprintImage(blueprintImage);
            editableCalculationItem.setMeLabel(meLabel);
            editableCalculationItem.setPeLabel(peLabel);
            editableCalculationItem.setMeTextBox(meTextBox);
            editableCalculationItem.setPeTextBox(peTextBox);
            editableCalculationItem.setApplyButton(applyButton);
            editableCalculationItem.setCalculationItemDetailsTable(calculationItemDetailsTable);
        }

        computableCalculationItem.setQuantityForParentLabel(quantityForParentLabel);
        computableCalculationItem.setPriceLabel(priceLabel);
        computableCalculationItem.setTotalPriceForParentLabel(totalPriceForParentLabel);
    }

    private void drawCalculationItem(FlexTable calculationItemTable, CalculationTreeNode calculationTreeNode) {
        CalculationTreeNodeSummary calculationTreeNodeSummary = calculationTreeNode.getSummary();
        String pathNodesString = calculationTreeNodeSummary.getPathNodesString();
        EditableCalculationItem editableCalculationItem = new EditableCalculationItem();
        pathNodesStringToEditableCalculationItemMap.put(pathNodesString, editableCalculationItem);
        ComputableCalculationItem computableCalculationItem = new ComputableCalculationItem();
        computableCalculationItem.setCalculationItems(calculationTreeNode.getCalculationItems());
        computableCalculationItem.setCalculationTreeNodeSummary(calculationTreeNodeSummary);
        pathNodesStringToComputableCalculationItemMap.put(pathNodesString, computableCalculationItem);

        final int index = calculationItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage16Url(calculationTreeNodeSummary.getItemCategoryID(), calculationTreeNodeSummary.getItemTypeID(), calculationTreeNodeSummary.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationTreeNodeSummary.getItemTypeName());
        image.addStyleName(resources.css().image16());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, calculationTreeNodeSummary.getItemTypeID());
        calculationItemTable.setWidget(index, 0, imageItemInfoLink);
        calculationItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationTreeNodeSummary.getItemTypeName(), calculationTreeNodeSummary.getItemTypeID()));
        calculationItemTable.setWidget(index, 2, new Label("x"));
        QuantityLabel quantityLabel = new QuantityLabel(calculationTreeNodeSummary.getQuantity());
        calculationItemTable.setWidget(index, 3, quantityLabel);
        calculationItemTable.setWidget(index, 4, new Label("x"));
        PriceLabel priceLabel = new PriceLabel(calculationTreeNodeSummary.getPrice());
        calculationItemTable.setWidget(index, 5, priceLabel);
        calculationItemTable.setWidget(index, 6, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculationTreeNodeSummary.getTotalPrice());
        calculationItemTable.setWidget(index, 7, totalPriceLabel);
        calculationItemTable.setWidget(index, 8, new Label("x"));
        QuantityLabel parentQuantityLabel = new QuantityLabel(calculationTreeNodeSummary.getParentQuantity());
        calculationItemTable.setWidget(index, 9, parentQuantityLabel);
        calculationItemTable.setWidget(index, 10, new Label("="));
        PriceLabel totalPriceForParentLabel = new PriceLabel(calculationTreeNodeSummary.getTotalPriceForParent());
        calculationItemTable.setWidget(index, 11, totalPriceForParentLabel);


        if (hasBlueprint(calculationTreeNodeSummary.getItemCategoryID(), calculationTreeNodeSummary.getItemTypeID())) {
            OpaqueLoadableBlueprintImage blueprintImage = new OpaqueLoadableBlueprintImage(resources, messages, messages.useBlueprint(), messages.stopUsingBlueprint());
            blueprintImage.addStyleName(resources.css().image16());
            blueprintImage.addStyleName(resources.css().cursorHand());
            blueprintImage.setOpacity();
            calculationItemTable.setWidget(index, 12, blueprintImage);
            calculationItemTable.setWidget(index, 13, new Label(messages.me() + ":"));
            Label meLabel = new Label();
            calculationItemTable.setWidget(index, 14, meLabel);
            Label peLabel = new Label();
            calculationItemTable.setWidget(index, 15, new Label(messages.pe() + ":"));
            calculationItemTable.setWidget(index, 16, peLabel);
            Button editBlueprintButton = new Button(messages.edit());
            calculationItemTable.setWidget(index, 17, editBlueprintButton);
            Button detailsBlueprintButton = new Button(messages.details());
            calculationItemTable.setWidget(index, 18, detailsBlueprintButton);

            FlexTable calculationItemDetailsTable = new FlexTable();
            calculationItemTable.setWidget(index + 1, 0, null);
            calculationItemTable.setWidget(index + 1, 1, calculationItemDetailsTable);
            FlexTable.FlexCellFormatter tableFlexFormatter = calculationItemTable.getFlexCellFormatter();
            final HTMLTable.RowFormatter tableRowFormatter = calculationItemTable.getRowFormatter();
            tableFlexFormatter.setVisible(index, 13, false);
            tableFlexFormatter.setVisible(index, 14, false);
            tableFlexFormatter.setVisible(index, 15, false);
            tableFlexFormatter.setVisible(index, 16, false);
            tableFlexFormatter.setVisible(index, 17, false);
            tableFlexFormatter.setVisible(index, 18, false);
            tableFlexFormatter.setColSpan(index + 1, 1, 18);
            tableRowFormatter.setVisible(index + 1, false);

            handlerRegistrations.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (tableRowFormatter.isVisible(index + 1)) {
                        tableRowFormatter.setVisible(index + 1, false);
                    } else {
                        tableRowFormatter.setVisible(index + 1, true);
                    }
                }
            }));

            TextBox meTextBox = new TextBox();
            meTextBox.setValue(meLabel.getText());
            meTextBox.addStyleName(resources.css().mePeInput());
            TextBox peTextBox = new TextBox();
            peTextBox.setValue(peLabel.getText());
            peTextBox.addStyleName(resources.css().mePeInput());
            final DecoratedPopupPanel editCalculationItemPopup = new DecoratedPopupPanel(true);
            FlexTable editCalculationItemPopupFlexTable = new FlexTable();
            editCalculationItemPopupFlexTable.setWidget(0, 0, new Label(messages.materialLevel() + ":"));
            editCalculationItemPopupFlexTable.setWidget(0, 1, meTextBox);
            editCalculationItemPopupFlexTable.setWidget(1, 0, new Label(messages.productivityLevel() + ":"));
            editCalculationItemPopupFlexTable.setWidget(1, 1, peTextBox);
            Button applyButton = new Button(messages.apply());
            editCalculationItemPopupFlexTable.setWidget(3, 0, applyButton);
            editCalculationItemPopupFlexTable.getFlexCellFormatter().setColSpan(3, 0, 2);
            editCalculationItemPopup.setWidget(editCalculationItemPopupFlexTable);

            handlerRegistrations.add(editBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    editCalculationItemPopup.setPopupPosition(left, top);
                    editCalculationItemPopup.show();
                }
            }));
            handlerRegistrations.add(applyButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    editCalculationItemPopup.hide();
                }
            }));

            editableCalculationItem.setIndex(index);
            editableCalculationItem.setBlueprintImage(blueprintImage);
            editableCalculationItem.setMeLabel(meLabel);
            editableCalculationItem.setPeLabel(peLabel);
            editableCalculationItem.setMeTextBox(meTextBox);
            editableCalculationItem.setPeTextBox(peTextBox);
            editableCalculationItem.setApplyButton(applyButton);
            editableCalculationItem.setCalculationItemDetailsTable(calculationItemDetailsTable);
            editableCalculationItem.setCalculationItemTable(calculationItemTable);
        }

        computableCalculationItem.setQuantityLabel(quantityLabel);
        computableCalculationItem.setPriceLabel(priceLabel);
        computableCalculationItem.setTotalPriceLabel(totalPriceLabel);
        computableCalculationItem.setParentQuantityLabel(parentQuantityLabel);
        computableCalculationItem.setTotalPriceForParentLabel(totalPriceForParentLabel);
    }

    private void drawCalculationPriceSetItem(CalculationPriceSetItemDto calculationPriceSetItem) {
        EditableCalculationPriceSetItem editableCalculationPriceSetItem = new EditableCalculationPriceSetItem();
        Long typeID = calculationPriceSetItem.getItemTypeID();
        typeIdToEditableCalculationPriceSetItemMap.put(typeID, editableCalculationPriceSetItem);
        ComputableCalculationPriceSetItem computableCalculationPriceSetItem = new ComputableCalculationPriceSetItem();
        computableCalculationPriceSetItem.setCalculationPriceSetItem(calculationPriceSetItem);
        typeIdToComputableCalculationPriceSetItemMap.put(typeID, computableCalculationPriceSetItem);

        int index = priceSetItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage32Url(calculationPriceSetItem.getItemCategoryID(), typeID, calculationPriceSetItem.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationPriceSetItem.getItemTypeName());
        image.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, typeID);
        priceSetItemTable.setWidget(index, 0, imageItemInfoLink);
        priceSetItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationPriceSetItem.getItemTypeName(), typeID));
        PriceTextBox priceTextBox = new PriceTextBox();
        priceTextBox.setPrice(calculationPriceSetItem.getPrice());
        priceTextBox.addStyleName(resources.css().priceInput());
        priceSetItemTable.setWidget(index, 2, priceTextBox);
        Image eveCentralImage = new Image(resources.eveCentralIcon16());
        eveCentralImage.setTitle(messages.eveCentralQuicklook());
        priceSetItemTable.setWidget(index, 3, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, typeID));
        Image eveMetricsImage = new Image(resources.eveMetricsIcon16());
        eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
        priceSetItemTable.setWidget(index, 4, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, calculationPriceSetItem.getItemCategoryID(), typeID));
        priceSetItemTable.setWidget(index, 5, new Label("x"));
        QuantityLabel quantityLabel = new QuantityLabel(calculationPriceSetItem.getQuantity());
        HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
        quantityAndDamagePerJobPanel.add(quantityLabel);
        BigDecimal damagePerJob = calculationPriceSetItem.getDamagePerJob();
        if (BigDecimal.ONE.compareTo(damagePerJob) == 1) {
            DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
            damagePerJobLabel.addStyleName(resources.css().damagePerJob());
            quantityAndDamagePerJobPanel.add(damagePerJobLabel);
            quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
        }
        priceSetItemTable.setWidget(index, 6, quantityAndDamagePerJobPanel);
        priceSetItemTable.setWidget(index, 7, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculationPriceSetItem.getTotalPrice());
        priceSetItemTable.setWidget(index, 8, totalPriceLabel);

        editableCalculationPriceSetItem.setPriceTextBox(priceTextBox);
        computableCalculationPriceSetItem.setQuantityLabel(quantityLabel);
        computableCalculationPriceSetItem.setTotalPriceLabel(totalPriceLabel);
    }

    private Map<Long, CalculationPriceSetItemDto> createExistingTypeIdToCalculationPriceSetItemMap() {
        for (Map.Entry<Long, EditableCalculationPriceSetItem> mapEntry : typeIdToEditableCalculationPriceSetItemMap.entrySet()) {
            ComputableCalculationPriceSetItem computableCalculationPriceSetItem = typeIdToComputableCalculationPriceSetItemMap.get(mapEntry.getKey());
            computableCalculationPriceSetItem.getCalculationPriceSetItem();
            existingTypeIdToCalculationPriceSetItemMap.put(mapEntry.getKey(), computableCalculationPriceSetItem.getCalculationPriceSetItem());
        }
        return existingTypeIdToCalculationPriceSetItemMap;
    }

    // TODO this is workaround
    private Boolean hasBlueprint(Long categoryID, Long typeID) {
        return (categoryID == 6L // Ship
                || categoryID == 7L // Module
                || categoryID == 8L // Charge
                || categoryID == 17L // Commodity
                || categoryID == 18L // Drone
                || categoryID == 23L // Structure
                || categoryID == 32L) // Subsystem
                && (typeID != 3687L); // Electronic Parts
    }
}
