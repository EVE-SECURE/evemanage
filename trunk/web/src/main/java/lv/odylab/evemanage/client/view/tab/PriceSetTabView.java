package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.oracle.TypeSuggestOracle;
import lv.odylab.evemanage.client.presenter.tab.PriceSetTabPresenter;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.AttachedCharacterListBox;
import lv.odylab.evemanage.client.widget.EveCentralQuicklookLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.EveMetricsItemPriceLink;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.PriceSetListBox;
import lv.odylab.evemanage.client.widget.PriceTextBox;
import lv.odylab.evemanage.client.widget.SharingLevelListBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PriceSetTabView implements PriceSetTabPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private HorizontalPanel headerPanel;
    private Label headerLabel;
    private Image spinnerImage;

    private FlexTable errorMessageTable;
    private Label errorMessageLabel;
    private Image errorImage;

    private HorizontalPanel priceSetNamePanel;
    private FlexTable priceSetNameTable;
    private Label selectPriceSetLabel;
    private PriceSetListBox priceSetNamesListBox;
    private TextBox newPriceSetNameTextBox;
    private Button createPriceSetButton;
    private Button copyPriceSetButton;
    private Button renamePriceSetButton;
    private Button deletePriceSetButton;
    private Button addBasicMineralsButton;
    private Button addAdvancedMoonMaterialsButton;
    private DisclosurePanel otherOperationsDisclosurePanel;

    private FlexTable otherOperationsTable;
    private FlexTable priceSetItemsTable;

    private Label priceSetItemsLabel;
    private PriceSetDto currentPriceSet;
    private Map<PriceSetItemDto, PriceTextBox> currentPriceSetItemValues;
    private Map<PriceSetItemDto, Button> currentPriceSetItemDeleteButtons;
    private List<PriceSetItemDto> currentPriceSetDeletedItems;
    private FlexTable newItemTable;
    private Label newItemLabel;
    private SuggestBox newItemSuggestBox;
    private FlexTable attachedCharacterAndSharingLevelTable;
    private Label attachedCharacterLabel;
    private AttachedCharacterListBox attachedCharacterNameListBox;
    private Label sharingLevelLabel;
    private SharingLevelListBox sharingLevelListBox;
    private Button addNewItemButton;
    private FlexTable saveAndFetchPricesTable;
    private Button saveButton;
    private Button fetchEveCentralPricesButton;
    private Button fetchEveMetricsPricesButton;
    private Label notePricesAreTakenFrom;

    private Label corporationPriceSetsLabel;
    private FlexTable corporationPriceSetNameTable;
    private Label selectCorporationPriceSetLabel;
    private PriceSetListBox corporationPriceSetNamesListBox;
    private FlexTable corporationPriceSetItemsTable;
    private Button reloadCorporationPriceSetNamesButton;

    private Label alliancePriceSetsLabel;
    private FlexTable alliancePriceSetNameTable;
    private Label selectAlliancePriceSetLabel;
    private PriceSetListBox alliancePriceSetNamesListBox;
    private FlexTable alliancePriceSetItemsTable;
    private Button reloadAlliancePriceSetNamesButton;

    @Inject
    public PriceSetTabView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider, TypeSuggestOracle typeSuggestOracle) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.priceSets());
        headerLabel.addStyleName(resources.css().tabHeaderText());
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().tabHeaderSpinner());

        errorMessageTable = new FlexTable();
        errorMessageLabel = new Label();
        errorMessageLabel.addStyleName(resources.css().errorLabel());
        errorImage = new Image(resources.errorIcon());
        errorImage.setTitle(messages.error());

        priceSetNamePanel = new HorizontalPanel();
        priceSetNameTable = new FlexTable();
        selectPriceSetLabel = new Label(messages.selectPriceSet() + ":");
        priceSetNamesListBox = new PriceSetListBox(messages);
        priceSetNamesListBox.setEnabled(false);

        otherOperationsDisclosurePanel = new DisclosurePanel(messages.otherOperations());
        otherOperationsTable = new FlexTable();
        priceSetNamesListBox.addStyleName(resources.css().priceSetNameList());
        newPriceSetNameTextBox = new TextBox();
        newPriceSetNameTextBox.addStyleName(resources.css().priceSetNameInput());
        createPriceSetButton = new Button(messages.create());
        copyPriceSetButton = new Button(messages.copyCurrent());
        copyPriceSetButton.setEnabled(false);
        renamePriceSetButton = new Button(messages.renameCurrent());
        renamePriceSetButton.setEnabled(false);
        deletePriceSetButton = new Button(messages.deleteCurrent());
        deletePriceSetButton.setEnabled(false);
        addBasicMineralsButton = new Button(messages.addBasicMinerals());
        addBasicMineralsButton.setEnabled(false);
        addAdvancedMoonMaterialsButton = new Button(messages.addAdvancedMoonComponents());
        addAdvancedMoonMaterialsButton.setEnabled(false);

        priceSetItemsLabel = new Label(messages.priceSetContents());
        priceSetItemsLabel.addStyleName(resources.css().tabHeadingText());
        priceSetItemsTable = new FlexTable();
        currentPriceSetItemValues = new HashMap<PriceSetItemDto, PriceTextBox>();
        currentPriceSetItemDeleteButtons = new HashMap<PriceSetItemDto, Button>();
        currentPriceSetDeletedItems = new ArrayList<PriceSetItemDto>();
        newItemTable = new FlexTable();
        newItemLabel = new Label(messages.enterItemName() + ":");
        newItemSuggestBox = new SuggestBox(typeSuggestOracle);
        newItemSuggestBox.getTextBox().setEnabled(false);
        newItemSuggestBox.addStyleName(resources.css().priceSetItemNameInput());
        attachedCharacterAndSharingLevelTable = new FlexTable();
        attachedCharacterLabel = new Label(messages.attachedCharacter() + ":");
        attachedCharacterNameListBox = new AttachedCharacterListBox(messages);
        attachedCharacterNameListBox.setEnabled(false);
        sharingLevelLabel = new Label(messages.sharingLevel() + ":");
        sharingLevelListBox = new SharingLevelListBox(messages);
        sharingLevelListBox.setEnabled(false);
        addNewItemButton = new Button(messages.add());
        addNewItemButton.setEnabled(false);
        saveAndFetchPricesTable = new FlexTable();
        saveButton = new Button(messages.save());
        saveButton.setEnabled(false);
        fetchEveCentralPricesButton = new Button(messages.fetchPricesFromEveCentral());
        fetchEveCentralPricesButton.setEnabled(false);
        fetchEveMetricsPricesButton = new Button(messages.orEveMetrics());
        fetchEveMetricsPricesButton.setEnabled(false);
        notePricesAreTakenFrom = new Label(messages.notePricesAreTakenFrom() + ".");
        notePricesAreTakenFrom.addStyleName(resources.css().noteLabel());

        corporationPriceSetsLabel = new Label(messages.corporationPriceSets());
        corporationPriceSetsLabel.addStyleName(resources.css().tabHeadingText());
        corporationPriceSetNameTable = new FlexTable();
        selectCorporationPriceSetLabel = new Label(messages.selectPriceSet() + ":");
        corporationPriceSetNamesListBox = new PriceSetListBox(messages);
        corporationPriceSetNamesListBox.addStyleName(resources.css().priceSetNameList());
        corporationPriceSetNamesListBox.setEnabled(false);
        corporationPriceSetItemsTable = new FlexTable();
        reloadCorporationPriceSetNamesButton = new Button(messages.reload());
        reloadCorporationPriceSetNamesButton.setEnabled(false);

        alliancePriceSetsLabel = new Label(messages.alliancePriceSets());
        alliancePriceSetsLabel.addStyleName(resources.css().tabHeadingText());
        alliancePriceSetNameTable = new FlexTable();
        selectAlliancePriceSetLabel = new Label(messages.selectPriceSet() + ":");
        alliancePriceSetNamesListBox = new PriceSetListBox(messages);
        alliancePriceSetNamesListBox.addStyleName(resources.css().priceSetNameList());
        alliancePriceSetNamesListBox.setEnabled(false);
        alliancePriceSetItemsTable = new FlexTable();
        reloadAlliancePriceSetNamesButton = new Button(messages.reload());
        reloadAlliancePriceSetNamesButton.setEnabled(false);
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

        FlexTable.FlexCellFormatter otherOperationsTableFormatter = otherOperationsTable.getFlexCellFormatter();
        otherOperationsTable.setWidget(0, 0, newPriceSetNameTextBox);
        otherOperationsTable.setWidget(0, 1, createPriceSetButton);
        otherOperationsTable.setWidget(1, 1, copyPriceSetButton);
        otherOperationsTable.setWidget(2, 1, renamePriceSetButton);
        otherOperationsTable.setWidget(3, 0, deletePriceSetButton);
        otherOperationsTableFormatter.setColSpan(3, 0, 2);
        otherOperationsTable.setWidget(4, 0, addBasicMineralsButton);
        otherOperationsTableFormatter.setColSpan(4, 0, 2);
        otherOperationsTable.setWidget(5, 0, addAdvancedMoonMaterialsButton);
        otherOperationsTableFormatter.setColSpan(5, 0, 2);
        otherOperationsDisclosurePanel.setContent(otherOperationsTable);

        priceSetNameTable.setWidget(0, 0, selectPriceSetLabel);
        priceSetNameTable.setWidget(0, 1, priceSetNamesListBox);
        priceSetNamePanel.add(priceSetNameTable);
        priceSetNamePanel.add(otherOperationsDisclosurePanel);
        priceSetNamePanel.setCellVerticalAlignment(otherOperationsDisclosurePanel, HasVerticalAlignment.ALIGN_MIDDLE);
        container.add(priceSetNamePanel);

        newItemTable.setWidget(0, 0, newItemLabel);
        newItemTable.setWidget(0, 1, newItemSuggestBox);
        newItemTable.setWidget(0, 2, addNewItemButton);
        container.add(newItemTable);

        container.add(priceSetItemsLabel);

        attachedCharacterAndSharingLevelTable.setWidget(0, 0, attachedCharacterLabel);
        attachedCharacterAndSharingLevelTable.setWidget(0, 1, attachedCharacterNameListBox);
        attachedCharacterAndSharingLevelTable.setWidget(0, 2, sharingLevelLabel);
        attachedCharacterAndSharingLevelTable.setWidget(0, 3, sharingLevelListBox);
        container.add(attachedCharacterAndSharingLevelTable);

        container.add(priceSetItemsTable);

        saveAndFetchPricesTable.setWidget(0, 0, saveButton);
        saveAndFetchPricesTable.setWidget(0, 1, fetchEveCentralPricesButton);
        saveAndFetchPricesTable.setWidget(0, 2, fetchEveMetricsPricesButton);
        container.add(saveAndFetchPricesTable);
        container.add(notePricesAreTakenFrom);

        container.add(corporationPriceSetsLabel);
        corporationPriceSetNameTable.setWidget(0, 0, selectCorporationPriceSetLabel);
        corporationPriceSetNameTable.setWidget(0, 1, corporationPriceSetNamesListBox);
        corporationPriceSetNameTable.setWidget(0, 2, reloadCorporationPriceSetNamesButton);
        container.add(corporationPriceSetNameTable);
        container.add(corporationPriceSetItemsTable);

        container.add(alliancePriceSetsLabel);
        alliancePriceSetNameTable.setWidget(0, 0, selectAlliancePriceSetLabel);
        alliancePriceSetNameTable.setWidget(0, 1, alliancePriceSetNamesListBox);
        alliancePriceSetNameTable.setWidget(0, 2, reloadAlliancePriceSetNamesButton);
        container.add(alliancePriceSetNameTable);
        container.add(alliancePriceSetItemsTable);

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
    public PriceSetListBox getPriceSetNamesListBox() {
        return priceSetNamesListBox;
    }

    @Override
    public void setCurrentPriceSetNameIndex(Integer index) {
        priceSetNamesListBox.setSelectedIndex(index);
    }

    @Override
    public PriceSetDto getCurrentPriceSet() {
        currentPriceSet.setSharingLevel(sharingLevelListBox.getSharingLevel());
        currentPriceSet.setAttachedCharacterName(attachedCharacterNameListBox.getAttachedCharacterName());
        return currentPriceSet;
    }

    @Override
    public void setCurrentPriceSet(PriceSetDto priceSet) {
        this.currentPriceSet = priceSet;
        this.currentPriceSetItemValues.clear();
        this.currentPriceSetItemDeleteButtons.clear();
        this.currentPriceSetDeletedItems.clear();
        if (priceSet != null) {
            sharingLevelListBox.setSharingLevel(priceSet.getSharingLevel());
            attachedCharacterNameListBox.setAttachedCharacter(priceSet.getAttachedCharacterName());
        }
        drawPriceSet(priceSet);
    }

    @Override
    public void clearPriceSetItemsTable() {
        priceSetItemsTable.removeAllRows();
    }

    @Override
    public AttachedCharacterListBox getAttachedCharacterNameListBox() {
        return attachedCharacterNameListBox;
    }

    @Override
    public ListBox getSharingLevelListBox() {
        return sharingLevelListBox;
    }

    @Override
    public void setSharingLevels(List<String> sharingLevels) {
        sharingLevelListBox.clear();
        for (String sharingLevel : sharingLevels) {
            sharingLevelListBox.addItem(sharingLevel);
        }
    }

    @Override
    public void addPriceSetItem(PriceSetItemDto priceSetItem) {
        drawPriceSetItem(priceSetItem);
        this.currentPriceSet.getItems().add(priceSetItem);
    }

    @Override
    public Boolean priceSetItemExists(PriceSetItemDto priceSetItem) {
        Set<Long> existingItemTypes = new HashSet<Long>();
        for (PriceSetItemDto existingPriceSetItem : currentPriceSet.getItems()) {
            existingItemTypes.add(existingPriceSetItem.getItemTypeID());
        }
        return existingItemTypes.contains(priceSetItem.getItemTypeID());
    }

    @Override
    public Map<PriceSetItemDto, PriceTextBox> getCurrentPriceSetItemValues() {
        return currentPriceSetItemValues;
    }

    @Override
    public Map<PriceSetItemDto, Button> getCurrentPriceSetItemDeleteButtons() {
        return currentPriceSetItemDeleteButtons;
    }

    @Override
    public List<PriceSetItemDto> getCurrentPriceSetDeletedItems() {
        return currentPriceSetDeletedItems;
    }

    @Override
    public PriceSetListBox getPriceSetListBox() {
        return priceSetNamesListBox;
    }

    @Override
    public TextBox getNewPriceSetName() {
        return newPriceSetNameTextBox;
    }

    @Override
    public Button getSavePriceSetButton() {
        return saveButton;
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
    public Button getCreatePriceSetButton() {
        return createPriceSetButton;
    }

    @Override
    public Button getCopyPriceSetButton() {
        return copyPriceSetButton;
    }

    @Override
    public Button getRenamePriceSetButton() {
        return renamePriceSetButton;
    }

    @Override
    public Button getDeletePriceSetButton() {
        return deletePriceSetButton;
    }

    @Override
    public Button getAddBasicMineralsButton() {
        return addBasicMineralsButton;
    }

    @Override
    public Button getAddAdvancedMoonMaterialButton() {
        return addAdvancedMoonMaterialsButton;
    }

    @Override
    public SuggestBox getAddNewItemName() {
        return newItemSuggestBox;
    }

    @Override
    public Button getAddNewItemButton() {
        return addNewItemButton;
    }

    @Override
    public void clearCorporationPriceSetItemsTable() {
        corporationPriceSetItemsTable.removeAllRows();
    }

    @Override
    public Button getReloadCorporationPriceSetNames() {
        return reloadCorporationPriceSetNamesButton;
    }

    @Override
    public PriceSetListBox getCorporationPriceSetNamesListBox() {
        return corporationPriceSetNamesListBox;
    }

    @Override
    public void setCurrentCorporationPriceSet(PriceSetDto priceSet) {
        drawCorporationOrAlliancePriceSet(corporationPriceSetItemsTable, priceSet);
    }

    @Override
    public void clearAlliancePriceSetItemsTable() {
        alliancePriceSetItemsTable.removeAllRows();
    }

    @Override
    public PriceSetListBox getAlliancePriceSetNamesListBox() {
        return alliancePriceSetNamesListBox;
    }

    @Override
    public void setCurrentAlliancePriceSet(PriceSetDto priceSet) {
        drawCorporationOrAlliancePriceSet(alliancePriceSetItemsTable, priceSet);
    }

    @Override
    public Button getReloadAlliancePriceSetNames() {
        return reloadAlliancePriceSetNamesButton;
    }

    private void drawPriceSet(PriceSetDto priceSet) {
        priceSetItemsTable.removeAllRows();
        if (priceSet == null) {
            return;
        }
        for (PriceSetItemDto priceSetItem : priceSet.getItems()) {
            drawPriceSetItem(priceSetItem);
        }
    }

    private void drawPriceSetItem(PriceSetItemDto priceSetItem) {
        int index = priceSetItemsTable.getRowCount();
        Long itemCategoryID = priceSetItem.getItemCategoryID();
        Long itemTypeID = priceSetItem.getItemTypeID();
        String imageUrl = imageUrlProvider.getImage32Url(itemCategoryID, itemTypeID, priceSetItem.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(priceSetItem.getItemTypeName());
        image.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, itemTypeID);
        priceSetItemsTable.setWidget(index, 0, imageItemInfoLink);
        priceSetItemsTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, priceSetItem.getItemTypeName(), itemTypeID));
        PriceTextBox priceTextBox = new PriceTextBox();
        priceTextBox.setPrice(priceSetItem.getPrice());
        currentPriceSetItemValues.put(priceSetItem, priceTextBox);
        priceTextBox.addStyleName(resources.css().priceInput());
        priceSetItemsTable.setWidget(index, 2, priceTextBox);
        Button deleteButton = new Button(messages.delete());
        currentPriceSetItemDeleteButtons.put(priceSetItem, deleteButton);
        Image eveCentralImage = new Image(resources.eveCentralIcon());
        priceSetItemsTable.setWidget(index, 3, deleteButton);
        eveCentralImage.setTitle(messages.eveCentralQuicklook());
        priceSetItemsTable.setWidget(index, 4, new EveCentralQuicklookLink(constants, urlMessages, eveCentralImage, itemTypeID));
        Image eveMetricsImage = new Image(resources.eveMetricsIcon());
        eveMetricsImage.setTitle(messages.eveMetricsItemPrice());
        priceSetItemsTable.setWidget(index, 5, new EveMetricsItemPriceLink(constants, urlMessages, eveMetricsImage, itemCategoryID, itemTypeID));

    }

    private void drawCorporationOrAlliancePriceSet(FlexTable table, PriceSetDto priceSet) {
        table.removeAllRows();
        if (priceSet == null) {
            table.setWidget(0, 0, new Label(messages.priceSetIsNotAvailable() + "."));
            return;
        } else if (priceSet.getItems().size() == 0) {
            table.setWidget(0, 0, new Label(messages.empty() + "."));
            return;
        }
        for (PriceSetItemDto priceSetItem : priceSet.getItems()) {
            drawCorporationOrAlliancePriceSetItem(table, priceSetItem);
        }
    }

    private void drawCorporationOrAlliancePriceSetItem(FlexTable table, PriceSetItemDto priceSetItem) {
        int index = table.getRowCount();
        String imageUrl = imageUrlProvider.getImage16Url(priceSetItem.getItemCategoryID(), priceSetItem.getItemTypeID(), priceSetItem.getItemTypeIcon());
        Image image = new Image(imageUrl);
        image.setTitle(priceSetItem.getItemTypeName());
        image.addStyleName(resources.css().image16());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, image, priceSetItem.getItemTypeID());
        table.setWidget(index, 0, imageItemInfoLink);
        table.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, priceSetItem.getItemTypeName(), priceSetItem.getItemTypeID()));
        table.setWidget(index, 2, new PriceLabel(priceSetItem.getPrice()));
    }
}
