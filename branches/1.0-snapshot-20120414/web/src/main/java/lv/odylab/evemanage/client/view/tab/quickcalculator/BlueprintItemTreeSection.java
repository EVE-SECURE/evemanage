package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.*;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTree;
import lv.odylab.evemanage.client.presenter.tab.calculator.BlueprintItemTreeNode;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.ComputableBlueprintItem;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableBlueprintItem;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.DecryptorDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.*;
import lv.odylab.evemanage.shared.eve.BlueprintUse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueprintItemTreeSection implements QuickCalculatorTabPresenter.BlueprintItemTreeSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private Label blueprintItemSectionLabel;
    private FlexTable blueprintItemTable;
    private FlexTable.FlexCellFormatter blueprintItemTableFlexFormatter;
    private HTMLTable.RowFormatter blueprintItemTableRowFormatter;

    private BlueprintItemTree blueprintItemTree;
    private Map<String, EditableBlueprintItem> pathNodesStringToEditableBlueprintItemMap;
    private Map<String, ComputableBlueprintItem> pathNodesStringToComputableBlueprintItemMap;
    private List<HandlerRegistration> blueprintItemTreeSectionHandlerRegistrations;

    @Inject
    public BlueprintItemTreeSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        blueprintItemSectionLabel = new Label(messages.blueprintCost());
        blueprintItemSectionLabel.addStyleName(resources.css().tabHeadingText());
        blueprintItemTable = new FlexTable();
        blueprintItemTableFlexFormatter = blueprintItemTable.getFlexCellFormatter();
        blueprintItemTableRowFormatter = blueprintItemTable.getRowFormatter();

        blueprintItemTree = new BlueprintItemTree();
        pathNodesStringToEditableBlueprintItemMap = new HashMap<String, EditableBlueprintItem>();
        pathNodesStringToComputableBlueprintItemMap = new HashMap<String, ComputableBlueprintItem>();
        blueprintItemTreeSectionHandlerRegistrations = new ArrayList<HandlerRegistration>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(blueprintItemSectionLabel);
        container.add(blueprintItemTable);
    }

    @Override
    public BlueprintItemTree getBlueprintItemTree() {
        return blueprintItemTree;
    }

    @Override
    public void drawBlueprintItemTree(BlueprintItemTree blueprintItemTree) {
        for (BlueprintItemTreeNode blueprintItemTreeNode : blueprintItemTree.getNodeMap().values()) {
            if (!blueprintItemTreeNode.isExcludeNodeCalculation()) {
                BlueprintItemDto blueprintItem = blueprintItemTreeNode.getBlueprintItem();
                drawRootBlueprintItem(blueprintItem);
                EditableBlueprintItem editableBlueprintItem = pathNodesStringToEditableBlueprintItemMap.get(blueprintItem.getPathExpression().getPathNodesString());
                for (BlueprintItemTreeNode childBlueprintItemTreeNode : blueprintItemTreeNode.getNodeMap().values()) {
                    drawChildBlueprintItem(editableBlueprintItem.getInventionBlueprintItemTable(), childBlueprintItemTreeNode);
                }
            }
        }
    }

    @Override
    public void cleanBlueprintItemTree() {
        blueprintItemTable.removeAllRows();
        pathNodesStringToEditableBlueprintItemMap.clear();
        pathNodesStringToComputableBlueprintItemMap.clear();
    }

    @Override
    public void showBlueprintItemDetails(EditableBlueprintItem editableBlueprintItem) {
        blueprintItemTableRowFormatter.setVisible(editableBlueprintItem.getIndex(), true);
    }

    @Override
    public void hideBlueprintItemDetails(EditableBlueprintItem editableBlueprintItem) {
        blueprintItemTableRowFormatter.setVisible(editableBlueprintItem.getIndex(), false);
    }

    @Override
    public void showBlueprintInventionItems(EditableBlueprintItem editableBlueprintItem) {
        blueprintItemTableRowFormatter.setVisible(editableBlueprintItem.getIndex() + 1, true);
    }

    @Override
    public void hideBlueprintInventionItems(EditableBlueprintItem editableBlueprintItem) {
        blueprintItemTableRowFormatter.setVisible(editableBlueprintItem.getIndex() + 1, false);
    }

    @Override
    public void addBlueprintItemTreeNodes(Map<Long[], UsedBlueprintDto> pathNodesToUsedBlueprintMap) {
        for (Map.Entry<Long[], UsedBlueprintDto> mapEntry : pathNodesToUsedBlueprintMap.entrySet()) {
            UsedBlueprintDto usedBlueprint = mapEntry.getValue();
            BlueprintItemDto blueprintItem = usedBlueprint.getBlueprintItem();
            if (blueprintItem != null) {
                blueprintItemTree.createNode(blueprintItem);
                drawRootBlueprintItem(blueprintItem);
            }
        }
    }

    @Override
    public void drawChildBlueprintItems(BlueprintItemTreeNode blueprintItemTreeNode) {
        BlueprintItemDto blueprintItem = blueprintItemTreeNode.getBlueprintItem();
        EditableBlueprintItem editableBlueprintItem = pathNodesStringToEditableBlueprintItemMap.get(blueprintItem.getPathExpression().getPathNodesString());
        FlexTable inventionBlueprintItemTable = editableBlueprintItem.getInventionBlueprintItemTable();
        blueprintItemTableRowFormatter.setVisible(editableBlueprintItem.getIndex() + 1, true);
        for (BlueprintItemTreeNode childBlueprintItemTreeNode : blueprintItemTreeNode.getNodeMap().values()) {
            drawChildBlueprintItem(inventionBlueprintItemTable, childBlueprintItemTreeNode);
        }
    }

    @Override
    public void drawDecryptors(FlexTable decryptorTable, List<DecryptorDto> decryptors) {
        decryptorTable.setWidget(0, 0, new Anchor("Do not use decryptor"));
        decryptorTable.getFlexCellFormatter().setColSpan(0, 0, 12);
        for (DecryptorDto decryptor : decryptors) {
            int index = decryptorTable.getRowCount();
            BigDecimal probabilityMultiplier = decryptor.getProbabilityMultiplier();
            Integer maxRunModifier = decryptor.getMaxRunModifier();
            Integer meModifier = decryptor.getMeModifier();
            Integer peModifier = decryptor.getPeModifier();
            Long decryptorTypeID = decryptor.getTypeID();

            Image decryptorImage = new Image(resources.decryptorIcon());
            decryptorImage.setTitle(decryptor.getTypeName());
            decryptorImage.addStyleName(resources.css().image32());
            EveItemInfoLink decryptorImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, decryptorImage, decryptorTypeID);
            decryptorTable.setWidget(index, 0, new Anchor("use"));
            decryptorTable.setWidget(index, 1, decryptorImageItemInfoLink);
            decryptorTable.setWidget(index, 2, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, decryptor.getTypeName(), decryptorTypeID));
            Image eveCentralImage = new Image(resources.eveCentralIcon());
            eveCentralImage.setTitle(messages.eveCentralQuicklook());
            decryptorTable.setWidget(index, 3, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, decryptorTypeID));
            Image eveMetricsImage = new Image(resources.eveMetricsIcon());
            eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
            decryptorTable.setWidget(index, 4, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, decryptor.getCategoryID(), decryptorTypeID));
            decryptorTable.setWidget(index, 5, new Label("Chance:"));
            decryptorTable.setWidget(index, 6, new Label(probabilityMultiplier.toPlainString() + "x"));
            if (BigDecimal.ONE.compareTo(probabilityMultiplier) < 0) {
                decryptorTable.getCellFormatter().addStyleName(index, 6, resources.css().decryptorImprove());
            } else if (BigDecimal.ONE.compareTo(probabilityMultiplier) > 0) {
                decryptorTable.getCellFormatter().addStyleName(index, 6, resources.css().decryptorWorsen());
            }
            decryptorTable.setWidget(index, 7, new Label("Runs:"));
            decryptorTable.setWidget(index, 8, new Label(maxRunModifier == 0 ? "0" : "+" + String.valueOf(maxRunModifier)));
            if (maxRunModifier > 0) {
                decryptorTable.getCellFormatter().addStyleName(index, 8, resources.css().decryptorImprove());
            }
            decryptorTable.setWidget(index, 9, new Label("ME:"));
            decryptorTable.setWidget(index, 10, new Label((meModifier > 0 ? "+" : "") + String.valueOf(meModifier)));
            if (meModifier > 0) {
                decryptorTable.getCellFormatter().addStyleName(index, 10, resources.css().decryptorImprove());
            } else {
                decryptorTable.getCellFormatter().addStyleName(index, 10, resources.css().decryptorWorsen());
            }
            decryptorTable.setWidget(index, 11, new Label("PE:"));
            decryptorTable.setWidget(index, 12, new Label((peModifier > 0 ? "+" : "") + String.valueOf(peModifier)));
            if (peModifier > 0) {
                decryptorTable.getCellFormatter().addStyleName(index, 12, resources.css().decryptorImprove());
            } else {
                decryptorTable.getCellFormatter().addStyleName(index, 12, resources.css().decryptorWorsen());
            }
        }
    }

    @Override
    public void drawBaseItems(FlexTable baseItemTable, List<ItemTypeDto> baseItems) {
        baseItemTable.setWidget(0, 0, new Anchor("Do not use base item"));
        baseItemTable.getFlexCellFormatter().setColSpan(0, 0, 4);
        for (ItemTypeDto baseItem : baseItems) {
            int index = baseItemTable.getRowCount();
            String imageUrl = imageUrlProvider.getImage32Url(baseItem.getItemCategoryID(), baseItem.getItemTypeID(), baseItem.getGraphicIcon());
            Image baseItemImage = new Image(imageUrl);
            baseItemImage.setTitle(baseItem.getName());
            baseItemImage.addStyleName(resources.css().image32());
            baseItemTable.setWidget(index, 0, new Anchor("use"));
            baseItemTable.setWidget(index, 1, baseItemImage);
            baseItemTable.setWidget(index, 2, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, baseItem.getName(), baseItem.getItemTypeID()));
            Image eveCentralImage = new Image(resources.eveCentralIcon());
            eveCentralImage.setTitle(messages.eveCentralQuicklook());
            baseItemTable.setWidget(index, 3, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, baseItem.getItemTypeID()));
            Image eveMetricsImage = new Image(resources.eveMetricsIcon());
            eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
            baseItemTable.setWidget(index, 4, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, baseItem.getItemCategoryID(), baseItem.getItemTypeID()));
            baseItemTable.setWidget(index, 5, new Label("Meta:"));
            baseItemTable.setWidget(index, 6, new Label(String.valueOf(baseItem.getMetaLevel())));
        }
    }

    @Override
    public void excludeBlueprintItemTreeNodesFromCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            BlueprintItemTreeNode blueprintItemTreeNode = blueprintItemTree.getNodeByPathNodes(pathNodes);
            blueprintItemTreeNode.setExcludeNodeFromCalculation(true);
        }
    }

    @Override
    public void includeBlueprintItemTreeNodesInCalculation(List<Long[]> pathNodesList) {
        for (Long[] pathNodes : pathNodesList) {
            BlueprintItemTreeNode blueprintItemTreeNode = blueprintItemTree.getNodeByPathNodes(pathNodes);
            blueprintItemTreeNode.setExcludeNodeFromCalculation(false);
        }
    }

    @Override
    public Map<String, EditableBlueprintItem> getPathNodesStringToEditableBlueprintItemMap() {
        return pathNodesStringToEditableBlueprintItemMap;
    }

    @Override
    public Map<String, ComputableBlueprintItem> getPathNodesStringToComputableBlueprintItemMap() {
        return pathNodesStringToComputableBlueprintItemMap;
    }

    private void drawRootBlueprintItem(final BlueprintItemDto blueprintItem) {
        final int index = blueprintItemTable.getRowCount();
        String pathNodesString = blueprintItem.getPathExpression().getPathNodesString();
        EditableBlueprintItem editableBlueprintItem = new EditableBlueprintItem();
        ComputableBlueprintItem computableBlueprintItem = new ComputableBlueprintItem();

        Long itemTypeID = blueprintItem.getItemTypeID();
        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(itemTypeID);
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink blueprintImageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, itemTypeID);
        blueprintItemTable.setWidget(index, 0, blueprintImageItemInfoLink);
        blueprintItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, blueprintItem.getItemTypeName(), itemTypeID));
        final BlueprintUseButton blueprintUseButton = new BlueprintUseButton(resources, blueprintItem.getBlueprintUse());
        blueprintItemTable.setWidget(index, 2, blueprintUseButton);

        final DecoratedPopupPanel editBlueprintUsagePopup = new DecoratedPopupPanel(true);
        FlexTable editBlueprintInfoPopupFlexTable = new FlexTable();
        if (blueprintItem.getParentBlueprintTypeID() != null) {
            Anchor inventAnchor = new Anchor("Invent");
            editBlueprintInfoPopupFlexTable.setWidget(editBlueprintInfoPopupFlexTable.getRowCount(), 0, inventAnchor);
            final FlexTable inventionTable = new FlexTable();
            Button useDecryptorButton = new Button("Decryptor");
            final DecoratedPopupPanel useDecryptorPopup = new DecoratedPopupPanel(true);
            FlexTable decryptorTable = new FlexTable();
            useDecryptorPopup.add(decryptorTable);
            Button useBaseItemButton = new Button("Base item");
            final DecoratedPopupPanel useBaseItemPopup = new DecoratedPopupPanel(true);
            FlexTable baseItemTable = new FlexTable();
            useBaseItemPopup.add(baseItemTable);
            Image spinnerImage = new Image(resources.spinnerIcon());
            spinnerImage.setTitle(messages.loading());
            inventionTable.setWidget(0, 0, spinnerImage);

            blueprintItemTreeSectionHandlerRegistrations.add(inventAnchor.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    blueprintUseButton.setBlueprintUse(BlueprintUse.INVENTION);
                    editBlueprintUsagePopup.hide();
                    blueprintItemTable.setWidget(index, 3, inventionTable);
                }
            }));
            blueprintItemTreeSectionHandlerRegistrations.add(useDecryptorButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    useDecryptorPopup.setPopupPosition(left, top);
                    useDecryptorPopup.show();
                }
            }));
            blueprintItemTreeSectionHandlerRegistrations.add(useBaseItemButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Widget source = (Widget) event.getSource();
                    int left = source.getAbsoluteLeft() + 10;
                    int top = source.getAbsoluteTop() + 10;
                    useBaseItemPopup.setPopupPosition(left, top);
                    useBaseItemPopup.show();
                }
            }));

            FlexTable inventionBlueprintItemTable = new FlexTable();
            blueprintItemTableFlexFormatter.setColSpan(index + 1, 1, 3);
            blueprintItemTable.setWidget(index + 1, 1, inventionBlueprintItemTable);
            blueprintItemTableRowFormatter.setVisible(index + 1, false);

            editableBlueprintItem.setInventAnchor(inventAnchor);
            editableBlueprintItem.setUseDecryptorButton(useDecryptorButton);
            editableBlueprintItem.setInventionTable(inventionTable);
            editableBlueprintItem.setInventionBlueprintItemTable(inventionBlueprintItemTable);
            editableBlueprintItem.setUseBaseItemButton(useBaseItemButton);
            editableBlueprintItem.setDecryptorTable(decryptorTable);
            editableBlueprintItem.setUseBaseItemButton(useBaseItemButton);
            editableBlueprintItem.setBaseItemTable(baseItemTable);
            /*computableBlueprintItem.setInventionQuantityLabel(new QuantityLabel());
            computableBlueprintItem.setInventionMeLevelLabel(new QuantityLabel());
            computableBlueprintItem.setInventionPeLevelLabel(new QuantityLabel());
            computableBlueprintItem.setInventionChanceLabel(new MultiplierLabel());*/
        }
        Anchor useOriginalAnchor = new Anchor("Use original");
        Anchor useCopyAnchor = new Anchor("Use copy");
        editBlueprintInfoPopupFlexTable.setWidget(editBlueprintInfoPopupFlexTable.getRowCount(), 0, useOriginalAnchor);
        editBlueprintInfoPopupFlexTable.setWidget(editBlueprintInfoPopupFlexTable.getRowCount(), 0, useCopyAnchor);
        editBlueprintUsagePopup.add(editBlueprintInfoPopupFlexTable);

        final FlexTable originalTable = new FlexTable();
        QuantityLabel runsForOriginalLabel = new QuantityLabel(blueprintItem.getQuantity());
        originalTable.setWidget(0, 0, runsForOriginalLabel);
        originalTable.setWidget(0, 1, new Label("runs"));
        computableBlueprintItem.setOriginalRunsLabel(runsForOriginalLabel);

        final FlexTable copyTable = new FlexTable();
        QuantityLabel runsForCopyLabel = new QuantityLabel();
        QuantityLabel quantityLabel = new QuantityLabel();
        MultiplierLabel correctiveMultiplier = new MultiplierLabel();
        PriceLabel totalPrice = new PriceLabel(blueprintItem.getPrice());
        PriceTextBox copyPriceTextBox = new PriceTextBox();
        copyPriceTextBox.setPrice(blueprintItem.getPrice());
        copyPriceTextBox.addStyleName(resources.css().priceInput());
        copyTable.setWidget(0, 0, runsForCopyLabel);
        copyTable.setWidget(0, 1, new Label("runs,"));
        copyTable.setWidget(0, 2, quantityLabel);
        copyTable.setWidget(0, 3, new Label("copy"));
        copyTable.setWidget(0, 4, new Label("x"));
        copyTable.setWidget(0, 5, copyPriceTextBox);
        copyTable.setWidget(0, 6, new Label("x"));
        copyTable.setWidget(0, 7, correctiveMultiplier);
        copyTable.setWidget(0, 8, new Label("="));
        copyTable.setWidget(0, 9, totalPrice);
        editableBlueprintItem.setCopyPriceTextBox(copyPriceTextBox);
        computableBlueprintItem.setCopyRunsLabel(runsForCopyLabel);
        computableBlueprintItem.setCopyQuantityLabel(quantityLabel);
        computableBlueprintItem.setCopyCorrectiveMultiplierLabel(correctiveMultiplier);
        computableBlueprintItem.setCopyTotalPriceLabel(totalPrice);

        BlueprintUse blueprintUse = blueprintItem.getBlueprintUse();
        if (BlueprintUse.ORIGINAL.equals(blueprintUse)) {
            blueprintItemTable.setWidget(index, 3, originalTable);
        } else if (BlueprintUse.COPY.equals(blueprintUse)) {
            blueprintItemTable.setWidget(index, 3, copyTable);
        }

        blueprintItemTreeSectionHandlerRegistrations.add(blueprintUseButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft() + 10;
                int top = source.getAbsoluteTop() + 10;
                editBlueprintUsagePopup.setPopupPosition(left, top);
                editBlueprintUsagePopup.show();
            }
        }));
        blueprintItemTreeSectionHandlerRegistrations.add(useOriginalAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                blueprintUseButton.setBlueprintUse(BlueprintUse.ORIGINAL);
                editBlueprintUsagePopup.hide();
                blueprintItemTable.setWidget(index, 3, originalTable);
            }
        }));
        blueprintItemTreeSectionHandlerRegistrations.add(useCopyAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                blueprintUseButton.setBlueprintUse(BlueprintUse.COPY);
                editBlueprintUsagePopup.hide();
                blueprintItemTable.setWidget(index, 3, copyTable);
            }
        }));

        editableBlueprintItem.setIndex(index);
        editableBlueprintItem.setBlueprintUseButton(blueprintUseButton);
        editableBlueprintItem.setUseOriginalAnchor(useOriginalAnchor);
        editableBlueprintItem.setUseCopyAnchor(useCopyAnchor);
        computableBlueprintItem.setBlueprintItem(blueprintItem);
        pathNodesStringToEditableBlueprintItemMap.put(pathNodesString, editableBlueprintItem);
        pathNodesStringToComputableBlueprintItemMap.put(pathNodesString, computableBlueprintItem);
    }

    private void drawChildBlueprintItem(FlexTable inventionBlueprintItemTable, BlueprintItemTreeNode childNode) {
        final int index = inventionBlueprintItemTable.getRowCount();
        BlueprintItemDto blueprintItem = childNode.getBlueprintItem();
        String pathNodesString = blueprintItem.getPathExpression().getPathNodesString();
        EditableBlueprintItem editableBlueprintItem = new EditableBlueprintItem();
        ComputableBlueprintItem computableBlueprintItem = new ComputableBlueprintItem();

        Long itemCategoryID = blueprintItem.getItemCategoryID();
        Long itemTypeID = blueprintItem.getItemTypeID();
        QuantityLabel inventionQuantityLabel = new QuantityLabel();
        PriceTextBox inventionPriceTextBox = new PriceTextBox();
        inventionPriceTextBox.setPrice(blueprintItem.getPrice());
        inventionPriceTextBox.addStyleName(resources.css().priceInput());
        PriceLabel inventionItemTotalPriceLabel = new PriceLabel(BigDecimal.ZERO);
        if (itemCategoryID == 9L) { // Blueprint
            String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(itemTypeID);
            Image blueprintImage = new Image(blueprintImageUrl);
            blueprintImage.addStyleName(resources.css().image16());
            inventionBlueprintItemTable.setWidget(index, 0, new EveItemInfoLink(ccpJsMessages, blueprintImage, itemTypeID));
            inventionBlueprintItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, blueprintItem.getItemTypeName() + " (copy)", itemTypeID));
            inventionBlueprintItemTable.setWidget(index, 2, new Label("x"));
            inventionBlueprintItemTable.setWidget(index, 3, inventionQuantityLabel);
            inventionBlueprintItemTable.setWidget(index, 4, new Label("x"));
            inventionBlueprintItemTable.setWidget(index, 5, inventionPriceTextBox);
            inventionBlueprintItemTable.setWidget(index, 6, new Label("="));
            inventionBlueprintItemTable.setWidget(index, 7, inventionItemTotalPriceLabel);
        } else {
            String imageUrl = imageUrlProvider.getImage16Url(itemCategoryID, itemTypeID, blueprintItem.getItemTypeIcon());
            Image image = new Image(imageUrl);
            image.addStyleName(resources.css().image16());
            inventionBlueprintItemTable.setWidget(index, 0, new EveItemInfoLink(ccpJsMessages, image, itemTypeID));
            inventionBlueprintItemTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, blueprintItem.getItemTypeName(), itemTypeID));
            inventionBlueprintItemTable.setWidget(index, 2, new Label("x"));
            inventionBlueprintItemTable.setWidget(index, 3, inventionQuantityLabel);
            inventionBlueprintItemTable.setWidget(index, 4, new Label("x"));
            inventionBlueprintItemTable.setWidget(index, 5, inventionPriceTextBox);
            inventionBlueprintItemTable.setWidget(index, 6, new Label("="));
            inventionBlueprintItemTable.setWidget(index, 7, inventionItemTotalPriceLabel);
        }

        editableBlueprintItem.setIndex(index);
        editableBlueprintItem.setInventionPriceTextBox(inventionPriceTextBox);
        computableBlueprintItem.setInventionQuantityLabel(inventionQuantityLabel);
        computableBlueprintItem.setInventionTotalPriceLabel(inventionItemTotalPriceLabel);
        computableBlueprintItem.setBlueprintItem(blueprintItem);
        pathNodesStringToEditableBlueprintItemMap.put(pathNodesString, editableBlueprintItem);
        pathNodesStringToComputableBlueprintItemMap.put(pathNodesString, computableBlueprintItem);
    }
}