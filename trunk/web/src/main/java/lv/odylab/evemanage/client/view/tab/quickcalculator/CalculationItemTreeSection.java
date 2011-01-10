package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.calculator.CalculationItemTreeNodeSummary;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableCalculationItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationItem;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.DamagePerJobLabel;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.OpaqueLoadableSchematicImage;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.shared.PathExpression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationItemTreeSection implements QuickCalculatorTabPresenter.CalculationItemTreeSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private FlexTable rootCalculationItemTable;
    private FlexTable.FlexCellFormatter rootCalculationItemTableFlexFormatter;
    private HTMLTable.RowFormatter rootCalculationItemTableRowFormatter;

    private CalculationItemTree calculationItemTree;
    private Map<String, EditableCalculationItem> pathNodesStringToEditableCalculationItemMap;
    private Map<String, ComputableCalculationItem> pathNodesStringToComputableCalculationItemMap;
    private List<String> pathNodeStringsWithUsedBlueprint;
    private List<String> pathNodeStringsWithUsedSchematic;
    private List<HandlerRegistration> calculationItemTreeSectionRegistrationHandlers;

    @Inject
    public CalculationItemTreeSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        rootCalculationItemTable = new FlexTable();
        rootCalculationItemTableFlexFormatter = rootCalculationItemTable.getFlexCellFormatter();
        rootCalculationItemTableRowFormatter = rootCalculationItemTable.getRowFormatter();

        calculationItemTree = new CalculationItemTree();
        pathNodesStringToEditableCalculationItemMap = new HashMap<String, EditableCalculationItem>();
        pathNodesStringToComputableCalculationItemMap = new HashMap<String, ComputableCalculationItem>();
        pathNodeStringsWithUsedBlueprint = new ArrayList<String>();
        pathNodeStringsWithUsedSchematic = new ArrayList<String>();
        calculationItemTreeSectionRegistrationHandlers = new ArrayList<HandlerRegistration>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(rootCalculationItemTable);
    }

    @Override
    public CalculationItemTree getCalculationItemTree() {
        return calculationItemTree;
    }

    @Override
    public void drawCalculationItemTree(CalculationItemTree calculationItemTree) {
        for (CalculationItemTreeNode calculationItemTreeNode : calculationItemTree.getNodeMap().values()) {
            drawRootCalculationItem(calculationItemTreeNode);
        }
    }

    private void drawRootCalculationItem(CalculationItemTreeNode calculationItemTreeNode) {
        CalculationItemTreeNodeSummary calculationItemTreeNodeSummary = calculationItemTreeNode.getSummary();
        String pathNodesString = calculationItemTreeNodeSummary.getPathNodesString();
        EditableCalculationItem editableCalculationItem = new EditableCalculationItem();
        pathNodesStringToEditableCalculationItemMap.put(pathNodesString, editableCalculationItem);
        ComputableCalculationItem computableCalculationItem = new ComputableCalculationItem();
        computableCalculationItem.setCalculationItems(calculationItemTreeNode.getCalculationItems());
        computableCalculationItem.setCalculationItemTreeNodeSummary(calculationItemTreeNodeSummary);
        pathNodesStringToComputableCalculationItemMap.put(pathNodesString, computableCalculationItem);

        final int index = rootCalculationItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage32Url(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID(), calculationItemTreeNodeSummary.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationItemTreeNodeSummary.getItemTypeName());
        image.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, calculationItemTreeNodeSummary.getItemTypeID());
        rootCalculationItemTable.setWidget(index, 0, imageItemInfoLink);
        rootCalculationItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationItemTreeNodeSummary.getItemTypeName(), calculationItemTreeNodeSummary.getItemTypeID()));
        rootCalculationItemTable.setWidget(index, 2, new Label("x"));
        QuantityLabel quantityForParentLabel = new QuantityLabel(resources, calculationItemTreeNodeSummary.getParentQuantity() * calculationItemTreeNodeSummary.getQuantity(), calculationItemTreeNodeSummary.getParentQuantityMultiplier());
        HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
        quantityAndDamagePerJobPanel.add(quantityForParentLabel);
        BigDecimal damagePerJob = calculationItemTreeNodeSummary.getDamagePerJob();
        if (BigDecimal.ONE.compareTo(damagePerJob) == 1) {
            DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
            damagePerJobLabel.addStyleName(resources.css().damagePerJobLabel());
            quantityAndDamagePerJobPanel.add(damagePerJobLabel);
            quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
        }
        rootCalculationItemTable.setWidget(index, 3, quantityAndDamagePerJobPanel);
        rootCalculationItemTable.setWidget(index, 4, new Label("x"));
        PriceLabel priceLabel = new PriceLabel(calculationItemTreeNodeSummary.getPrice());
        rootCalculationItemTable.setWidget(index, 5, priceLabel);
        rootCalculationItemTable.setWidget(index, 6, new Label("="));
        PriceLabel totalPriceForParentLabel = new PriceLabel(calculationItemTreeNodeSummary.getTotalPriceForParent());
        rootCalculationItemTable.setWidget(index, 7, totalPriceForParentLabel);

        if (hasBlueprint(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID())) {
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

            calculationItemTreeSectionRegistrationHandlers.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
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

            calculationItemTreeSectionRegistrationHandlers.add(editBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    editCalculationItemPopup.setPopupPosition(left, top);
                    editCalculationItemPopup.show();
                }
            }));
            calculationItemTreeSectionRegistrationHandlers.add(applyButton.addClickHandler(new ClickHandler() {
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
        } else if (hasSchematic(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID())) {
            OpaqueLoadableSchematicImage schematicImage = new OpaqueLoadableSchematicImage(resources, messages, messages.useSchematic(), messages.stopUsingSchematic());
            schematicImage.addStyleName(resources.css().image16());
            schematicImage.addStyleName(resources.css().cursorHand());
            schematicImage.setOpacity();
            rootCalculationItemTable.setWidget(index, 8, schematicImage);
            Button detailsBlueprintButton = new Button(messages.details());
            rootCalculationItemTable.setWidget(index, 9, detailsBlueprintButton);
            rootCalculationItemTableFlexFormatter.setColSpan(index, 9, 5);

            FlexTable calculationItemDetailsTable = new FlexTable();
            rootCalculationItemTable.setWidget(index + 1, 0, null);
            rootCalculationItemTable.setWidget(index + 1, 1, calculationItemDetailsTable);
            rootCalculationItemTableFlexFormatter.setVisible(index, 9, false);
            rootCalculationItemTableFlexFormatter.setColSpan(index + 1, 1, 14);
            rootCalculationItemTableRowFormatter.setVisible(index + 1, false);

            calculationItemTreeSectionRegistrationHandlers.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (rootCalculationItemTableRowFormatter.isVisible(index + 1)) {
                        rootCalculationItemTableRowFormatter.setVisible(index + 1, false);
                    } else {
                        rootCalculationItemTableRowFormatter.setVisible(index + 1, true);
                    }
                }
            }));

            editableCalculationItem.setIndex(index);
            editableCalculationItem.setSchematicImage(schematicImage);
            editableCalculationItem.setCalculationItemDetailsTable(calculationItemDetailsTable);
        }

        computableCalculationItem.setQuantityForParentLabel(quantityForParentLabel);
        computableCalculationItem.setPriceLabel(priceLabel);
        computableCalculationItem.setTotalPriceForParentLabel(totalPriceForParentLabel);
    }

    private void drawCalculationItem(FlexTable calculationItemTable, CalculationItemTreeNode calculationItemTreeNode) {
        CalculationItemTreeNodeSummary calculationItemTreeNodeSummary = calculationItemTreeNode.getSummary();
        String pathNodesString = calculationItemTreeNodeSummary.getPathNodesString();
        EditableCalculationItem editableCalculationItem = new EditableCalculationItem();
        pathNodesStringToEditableCalculationItemMap.put(pathNodesString, editableCalculationItem);
        ComputableCalculationItem computableCalculationItem = new ComputableCalculationItem();
        computableCalculationItem.setCalculationItems(calculationItemTreeNode.getCalculationItems());
        computableCalculationItem.setCalculationItemTreeNodeSummary(calculationItemTreeNodeSummary);
        pathNodesStringToComputableCalculationItemMap.put(pathNodesString, computableCalculationItem);

        final int index = calculationItemTable.getRowCount();
        String imageUrl = imageUrlProvider.getImage16Url(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID(), calculationItemTreeNodeSummary.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(calculationItemTreeNodeSummary.getItemTypeName());
        image.addStyleName(resources.css().image16());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, calculationItemTreeNodeSummary.getItemTypeID());
        calculationItemTable.setWidget(index, 0, imageItemInfoLink);
        calculationItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, calculationItemTreeNodeSummary.getItemTypeName(), calculationItemTreeNodeSummary.getItemTypeID()));
        calculationItemTable.setWidget(index, 2, new Label("x"));
        QuantityLabel quantityLabel = new QuantityLabel(resources, calculationItemTreeNodeSummary.getQuantity(), calculationItemTreeNodeSummary.getQuantityMultiplier());
        HorizontalPanel quantityAndDamagePerJobPanel = new HorizontalPanel();
        quantityAndDamagePerJobPanel.add(quantityLabel);
        BigDecimal damagePerJob = calculationItemTreeNodeSummary.getDamagePerJob();
        if (BigDecimal.ONE.compareTo(damagePerJob) == 1) {
            DamagePerJobLabel damagePerJobLabel = new DamagePerJobLabel(damagePerJob);
            damagePerJobLabel.addStyleName(resources.css().damagePerJobLabel());
            quantityAndDamagePerJobPanel.add(damagePerJobLabel);
            quantityAndDamagePerJobPanel.setCellVerticalAlignment(damagePerJobLabel, HasVerticalAlignment.ALIGN_BOTTOM);
        }
        calculationItemTable.setWidget(index, 3, quantityAndDamagePerJobPanel);
        calculationItemTable.setWidget(index, 4, new Label("x"));
        PriceLabel priceLabel = new PriceLabel(calculationItemTreeNodeSummary.getPrice());
        calculationItemTable.setWidget(index, 5, priceLabel);
        calculationItemTable.setWidget(index, 6, new Label("="));
        PriceLabel totalPriceLabel = new PriceLabel(calculationItemTreeNodeSummary.getTotalPrice());
        calculationItemTable.setWidget(index, 7, totalPriceLabel);
        calculationItemTable.setWidget(index, 8, new Label("x"));
        QuantityLabel parentQuantityLabel = new QuantityLabel(resources, calculationItemTreeNodeSummary.getParentQuantity(), calculationItemTreeNodeSummary.getParentQuantityMultiplier());
        calculationItemTable.setWidget(index, 9, parentQuantityLabel);
        calculationItemTable.setWidget(index, 10, new Label("="));
        PriceLabel totalPriceForParentLabel = new PriceLabel(calculationItemTreeNodeSummary.getTotalPriceForParent());
        calculationItemTable.setWidget(index, 11, totalPriceForParentLabel);

        if (hasBlueprint(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID())) {
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

            calculationItemTreeSectionRegistrationHandlers.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
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

            calculationItemTreeSectionRegistrationHandlers.add(editBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    editCalculationItemPopup.setPopupPosition(left, top);
                    editCalculationItemPopup.show();
                }
            }));
            calculationItemTreeSectionRegistrationHandlers.add(applyButton.addClickHandler(new ClickHandler() {
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
        } else if (hasSchematic(calculationItemTreeNodeSummary.getItemCategoryID(), calculationItemTreeNodeSummary.getItemTypeID())) {
            OpaqueLoadableSchematicImage schematicImage = new OpaqueLoadableSchematicImage(resources, messages, messages.useSchematic(), messages.stopUsingSchematic());
            schematicImage.addStyleName(resources.css().image16());
            schematicImage.addStyleName(resources.css().cursorHand());
            schematicImage.setOpacity();
            calculationItemTable.setWidget(index, 12, schematicImage);
            Button detailsBlueprintButton = new Button(messages.details());
            calculationItemTable.setWidget(index, 13, detailsBlueprintButton);
            FlexTable.FlexCellFormatter tableFlexFormatter = calculationItemTable.getFlexCellFormatter();
            tableFlexFormatter.setColSpan(index, 13, 5);

            FlexTable calculationItemDetailsTable = new FlexTable();
            calculationItemTable.setWidget(index + 1, 0, null);
            calculationItemTable.setWidget(index + 1, 1, calculationItemDetailsTable);

            final HTMLTable.RowFormatter tableRowFormatter = calculationItemTable.getRowFormatter();
            tableFlexFormatter.setVisible(index, 13, false);
            tableFlexFormatter.setColSpan(index + 1, 1, 18);
            tableRowFormatter.setVisible(index + 1, false);

            calculationItemTreeSectionRegistrationHandlers.add(detailsBlueprintButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (tableRowFormatter.isVisible(index + 1)) {
                        tableRowFormatter.setVisible(index + 1, false);
                    } else {
                        tableRowFormatter.setVisible(index + 1, true);
                    }
                }
            }));

            editableCalculationItem.setIndex(index);
            editableCalculationItem.setSchematicImage(schematicImage);
            editableCalculationItem.setCalculationItemDetailsTable(calculationItemDetailsTable);
            editableCalculationItem.setCalculationItemTable(calculationItemTable);
        }

        computableCalculationItem.setQuantityLabel(quantityLabel);
        computableCalculationItem.setPriceLabel(priceLabel);
        computableCalculationItem.setTotalPriceLabel(totalPriceLabel);
        computableCalculationItem.setParentQuantityLabel(parentQuantityLabel);
        computableCalculationItem.setTotalPriceForParentLabel(totalPriceForParentLabel);
    }

    @Override
    public void cleanCalculationItemTree() {
        rootCalculationItemTable.removeAllRows();
        pathNodesStringToEditableCalculationItemMap.clear();
        pathNodesStringToComputableCalculationItemMap.clear();
        pathNodeStringsWithUsedBlueprint.clear();
        pathNodeStringsWithUsedSchematic.clear();
    }

    @Override
    public void showCalculationItemDetails(EditableCalculationItem editableCalculationItem) {
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
    public void hideCalculationItemDetails(EditableCalculationItem editableCalculationItem) {
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
    public List<String> getPathNodeStringsWithUsedBlueprint() {
        return pathNodeStringsWithUsedBlueprint;
    }

    @Override
    public List<String> getPathNodeStringsWithUsedSchematic() {
        return pathNodeStringsWithUsedSchematic;
    }

    @Override
    public List<String> addCalculationItemTreeNodes(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap) {
        List<String> pathNodesStringsWithBlueprintOrSchematic = new ArrayList<String>();
        for (Map.Entry<Long[], UsedBlueprintDto> mapEntry : pathNodesToUsedBlueprintMap.entrySet()) {
            Long[] pathNodes = mapEntry.getKey();
            UsedBlueprintDto usedBlueprint = mapEntry.getValue();
            String pathNodesString = PathExpression.createPathNodesStringFromPathNodes(pathNodes);

            pathNodeStringsWithUsedBlueprint.add(pathNodesString);
            EditableCalculationItem editableCalculationItem = pathNodesStringToEditableCalculationItemMap.get(pathNodesString);
            String meString = String.valueOf(usedBlueprint.getMaterialLevel());
            String peString = String.valueOf(usedBlueprint.getProductivityLevel());
            editableCalculationItem.getMeLabel().setText(meString);
            editableCalculationItem.getPeLabel().setText(peString);
            editableCalculationItem.getMeTextBox().setText(meString);
            editableCalculationItem.getPeTextBox().setText(peString);
            FlexTable table = editableCalculationItem.getCalculationItemDetailsTable();
            for (CalculationItemDto calculationItem : usedBlueprint.getCalculationItems()) {
                calculationItemTree.createNode(calculationItem);
                if (hasBlueprint(calculationItem.getItemCategoryID(), calculationItem.getItemTypeID())
                        || hasSchematic(calculationItem.getItemCategoryID(), calculationItem.getItemTypeID())) {
                    pathNodesStringsWithBlueprintOrSchematic.add(calculationItem.getPathExpression().getPathNodesString());
                }
            }
            CalculationItemTreeNode node = calculationItemTree.getNodeByPathNodes(pathNodes);
            for (CalculationItemTreeNode calculationItemTreeNode : node.getNodeMap().values()) {
                calculationItemTreeNode.changeMePe(usedBlueprint.getMaterialLevel(), usedBlueprint.getProductivityLevel());
                drawCalculationItem(table, calculationItemTreeNode);
            }
            showCalculationItemDetails(editableCalculationItem);
        }
        return pathNodesStringsWithBlueprintOrSchematic;
    }

    public List<String> addCalculationItemTreeNodesForSchematic(Map<Long[], UsedSchematicDto> pathNodesToUsedSchematicMap) {
        List<String> pathNodesStringsWithSchematic = new ArrayList<String>();
        for (Map.Entry<Long[], UsedSchematicDto> mapEntry : pathNodesToUsedSchematicMap.entrySet()) {
            Long[] pathNodes = mapEntry.getKey();
            UsedSchematicDto usedSchematic = mapEntry.getValue();
            String pathNodesString = PathExpression.createPathNodesStringFromPathNodes(pathNodes);

            pathNodeStringsWithUsedSchematic.add(pathNodesString);
            EditableCalculationItem editableCalculationItem = pathNodesStringToEditableCalculationItemMap.get(pathNodesString);
            FlexTable table = editableCalculationItem.getCalculationItemDetailsTable();
            for (CalculationItemDto calculationItem : usedSchematic.getCalculationItems()) {
                calculationItemTree.createNode(calculationItem);
                if (hasSchematic(calculationItem.getItemCategoryID(), calculationItem.getItemTypeID())) {
                    pathNodesStringsWithSchematic.add(calculationItem.getPathExpression().getPathNodesString());
                }
            }
            CalculationItemTreeNode node = calculationItemTree.getNodeByPathNodes(pathNodes);
            for (CalculationItemTreeNode calculationItemTreeNode : node.getNodeMap().values()) {
                drawCalculationItem(table, calculationItemTreeNode);
            }
            showCalculationItemDetails(editableCalculationItem);
        }
        return pathNodesStringsWithSchematic;
    }

    @Override
    public void excludeCalculationItemTreeNodesFromCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            CalculationItemTreeNode calculationItemTreeNode = calculationItemTree.getNodeByPathNodes(pathNodes);
            calculationItemTreeNode.setExcludeChildNodesFromCalculation(true);
        }
    }

    @Override
    public void includeCalculationItemTreeNodesInCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            CalculationItemTreeNode calculationItemTreeNode = calculationItemTree.getNodeByPathNodes(pathNodes);
            calculationItemTreeNode.setExcludeChildNodesFromCalculation(false);
        }
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
    public List<HandlerRegistration> getCalculationItemTreeSectionRegistrationHandlers() {
        return calculationItemTreeSectionRegistrationHandlers;
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

    // TODO this is workaround
    private Boolean hasSchematic(Long categoryID, Long typeID) {
        return (categoryID == 43L
                && typeID != 2073 // Micro Organisms
                && typeID != 2267 // Base Metals
                && typeID != 2268 // Aqueous Liquids
                && typeID != 2270 // Noble Metals
                && typeID != 2272 // Heavy Metals
                && typeID != 2286 // Planktic Colonies
                && typeID != 2287 // Complex Organisms
                && typeID != 2288 // Carbon Compounds
                && typeID != 2305 // Autotrophs
                && typeID != 2306 // Non-CS Crystals
                && typeID != 2307 // Felsic Magma
                && typeID != 2308 // Suspended Plasma
                && typeID != 2309 // Ionic Solutions
                && typeID != 2310 // Noble Gas
                && typeID != 2311); // Reactive Gas

        /*
         SELECT
           CONCAT('&& typeID != '. typeID, ' // ', typeName)
         FROM invTypes
         WHERE
           groupID in (1032, 1033, 1035) -- Planet Solid, Planet Liquid-Gas, Planet Organic
         ORDER BY typeID
         */
    }
}
