package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
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
import lv.odylab.evemanage.client.presenter.tab.BlueprintsTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.blueprint.EditableBlueprintDetails;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyCharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.AttachedCharacterListBox;
import lv.odylab.evemanage.client.widget.CharacterOrCorporationLevelListBox;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;
import lv.odylab.evemanage.client.widget.EveCorporationInfoLink;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.SharingLevelListBox;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueprintsTabView implements BlueprintsTabPresenter.Display {
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

    private FlexTable newBlueprintTable;
    private Label enterBlueprintNameLabel;
    private SuggestBox newBlueprintSuggestBox;
    private FlexTable newBlueprintMePeTable;
    private Label enterMeLabel;
    private TextBox meTextBox;
    private Label enterPeLabel;
    private TextBox peTextBox;
    private Button addButton;
    private Label hintCanAddBothBpoAndBpcLabel;

    private DisclosurePanel importDisclosurePanel;
    private VerticalPanel importPanel;
    private FlexTable importXmlDescriptionTable;
    private TextArea importXmlTextArea;
    private HTML descriptionYouCanImportXmlLabel;
    private Anchor charIndustryJobAnchor;
    private Anchor corpIndustryJobAnchor;
    private Label noteOnlyBposLabel;
    private Label noteSinceApiDoesNotProvide;
    private Label hintYouCanImportLabel;
    private Label hintBlueprintItemIdsLabel;
    private FlexTable importTable;
    private Label postXmlLabel;
    private Label orIfLazyLabel;
    private FlexTable importOneTimeTable;
    private TextBox oneTimeFullApiKeyTextBox;
    private TextBox oneTimeUserIdTextBox;
    private TextBox oneTimeCharacterIdTextBox;
    private CharacterOrCorporationLevelListBox oneTimeLevelListBox;
    private Label noteOneTime;
    private Label orPostCsvLabel;
    private FlexTable importCsvDescriptionTable;
    private HTML descriptionYouCanImportCsvLabel;
    private TextArea importCsvTextArea;
    private Label orFullApiKeyLabel;
    private ListBox fullApiKeyCharacterListBox;
    private CharacterOrCorporationLevelListBox fullApiKeyLevelListBox;
    private FlexTable importWithFullApiKeyTable;
    private Label andAttachLabel;
    private Label attachToCharacterLabel;
    private AttachedCharacterListBox attachToCharacterListBox;
    private Label importSharingLabel;
    private SharingLevelListBox sharingLevelListBox;
    private Button importButton;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<SharingLevel> sharingLevels;

    private Label libraryTitleLabel;
    private FlexTable filterTable;
    private FlexTable.FlexCellFormatter filterTableCellFormatter;
    private Label filterLabel;
    private TextBox filterTextBox;
    private FlexTable blueprintTable;
    private HTMLTable.RowFormatter blueprintTableRowFormatter;
    private Button reloadButton;
    private Button hideButton;

    private Label corporationLibraryTitleLabel;
    private FlexTable corporationFilterTable;
    private FlexTable.FlexCellFormatter corporationFilterTableCellFormatter;
    private Label corporationFilterLabel;
    private TextBox corporationFilterTextBox;
    private FlexTable corporationBlueprintTable;
    private HTMLTable.RowFormatter corporationBlueprintTableRowFormatter;
    private Button corporationReloadButton;
    private Button corporationHideButton;

    private Label allianceLibraryTitleLabel;
    private FlexTable allianceFilterTable;
    private FlexTable.FlexCellFormatter allianceFilterTableCellFormatter;
    private Label allianceFilterLabel;
    private TextBox allianceFilterTextBox;
    private FlexTable allianceBlueprintTable;
    private HTMLTable.RowFormatter allianceBlueprintTableRowFormatter;
    private Button allianceReloadButton;
    private Button allianceHideButton;

    private Map<Long, BlueprintDto> blueprintIdToBlueprintMap;
    private Map<Long, Integer> blueprintIdToRowMap;
    private Map<String, List<Integer>> blueprintNameToRowsMap;
    private Map<BlueprintDto, EditableBlueprintDetails> blueprintToEditableDetailsMap;
    private Map<Long, BlueprintDto> corporationBlueprintIdToBlueprintMap;
    private Map<String, List<Integer>> corporationBlueprintNameToRowsMap;
    private Map<BlueprintDto, EditableBlueprintDetails> corporationBlueprintToEditableDetailsMap;
    private Map<Long, BlueprintDto> allianceBlueprintIdToBlueprintMap;
    private Map<String, List<Integer>> allianceBlueprintNameToRowsMap;
    private Map<BlueprintDto, EditableBlueprintDetails> allianceBlueprintToEditableDetailsMap;

    private List<HandlerRegistration> handlerRegistrations;
    private List<HandlerRegistration> handlerRegistrationsForCorporation;
    private List<HandlerRegistration> handlerRegistrationsForAlliance;

    @Inject
    public BlueprintsTabView(EveManageConstants constants, EveManageResources resources, final EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider, BlueprintTypeSuggestOracle blueprintTypeSuggestOracle) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.blueprints());
        headerLabel.addStyleName(resources.css().tabHeaderText());
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().tabHeaderSpinner());

        errorMessageTable = new FlexTable();
        errorMessageLabel = new Label();
        errorMessageLabel.addStyleName(resources.css().errorLabel());
        errorImage = new Image(resources.errorIcon());
        errorImage.setTitle(messages.error());

        newBlueprintTable = new FlexTable();
        newBlueprintSuggestBox = new SuggestBox(blueprintTypeSuggestOracle);
        newBlueprintSuggestBox.addStyleName(resources.css().blueprintNameInput());
        newBlueprintSuggestBox.getTextBox().setEnabled(false);
        newBlueprintMePeTable = new FlexTable();
        enterBlueprintNameLabel = new Label(messages.enterBlueprintName() + ":");
        enterMeLabel = new Label(messages.enterMe() + ":");
        meTextBox = new TextBox();
        meTextBox.setValue("0");
        meTextBox.addStyleName(resources.css().mePeInput());
        meTextBox.setEnabled(false);
        enterPeLabel = new Label(messages.enterPe() + ":");
        peTextBox = new TextBox();
        peTextBox.setValue("0");
        peTextBox.addStyleName(resources.css().mePeInput());
        peTextBox.setEnabled(false);
        addButton = new Button(messages.add());
        addButton.setEnabled(false);
        hintCanAddBothBpoAndBpcLabel = new Label(messages.hintCanAddBothBpoAndBpc());
        hintCanAddBothBpoAndBpcLabel.addStyleName(resources.css().hintLabel());

        importDisclosurePanel = new DisclosurePanel(messages.apiImport());
        importPanel = new VerticalPanel();
        importXmlDescriptionTable = new FlexTable();
        importXmlTextArea = new TextArea();
        importXmlTextArea.addStyleName(resources.css().importTextArea());
        importXmlTextArea.setEnabled(false);
        descriptionYouCanImportXmlLabel = new HTML(messages.descriptionYouCanImportXml() + ".");
        charIndustryJobAnchor = new Anchor(constants.charIndustryJobsTitle(), constants.charIndustryJobsUrl(), "_blank");
        corpIndustryJobAnchor = new Anchor(constants.corpIndustryJobsTitle(), constants.corpIndustryJobsUrl(), "_blank");
        noteOnlyBposLabel = new Label(messages.noteOnlyBPOs() + ".");
        noteOnlyBposLabel.addStyleName(resources.css().noteLabel());
        noteSinceApiDoesNotProvide = new Label(messages.noteSinceApiDoesNotProvide() + ".");
        noteSinceApiDoesNotProvide.addStyleName(resources.css().noteLabel());
        hintYouCanImportLabel = new Label(messages.hintYouCanImport() + ".");
        hintYouCanImportLabel.addStyleName(resources.css().hintLabel());
        hintBlueprintItemIdsLabel = new Label(messages.hintBlueprintItemIDs() + ".");
        hintBlueprintItemIdsLabel.addStyleName(resources.css().hintLabel());
        importTable = new FlexTable();
        postXmlLabel = new Label(messages.postXmlFromApi());
        postXmlLabel.addStyleName(resources.css().tabHeadingText());
        orIfLazyLabel = new Label(messages.orIfYouAreLazy());
        orIfLazyLabel.addStyleName(resources.css().tabHeadingText());
        importOneTimeTable = new FlexTable();
        oneTimeFullApiKeyTextBox = new TextBox();
        oneTimeFullApiKeyTextBox.addStyleName(resources.css().apiKeyStringInput());
        oneTimeFullApiKeyTextBox.setEnabled(false);
        oneTimeUserIdTextBox = new TextBox();
        oneTimeUserIdTextBox.addStyleName(resources.css().apiKeyUserIdInput());
        oneTimeUserIdTextBox.setEnabled(false);
        oneTimeCharacterIdTextBox = new TextBox();
        oneTimeCharacterIdTextBox.addStyleName(resources.css().apiKeyCharacterIdInput());
        oneTimeCharacterIdTextBox.setEnabled(false);
        oneTimeLevelListBox = new CharacterOrCorporationLevelListBox(messages);
        oneTimeLevelListBox.setEnabled(false);
        noteOneTime = new Label(messages.noteFullApiKeyWillNotBeStored() + ".");
        noteOneTime.addStyleName(resources.css().noteLabel());
        orPostCsvLabel = new Label(messages.orPostCsv());
        orPostCsvLabel.addStyleName(resources.css().tabHeadingText());
        importCsvDescriptionTable = new FlexTable();
        descriptionYouCanImportCsvLabel = new HTML(messages.descriptionYouCanImportCsv() + ".");
        importCsvTextArea = new TextArea();
        importCsvTextArea.addStyleName(resources.css().importTextArea());
        importCsvTextArea.setEnabled(false);
        orFullApiKeyLabel = new Label(messages.orIfYouHaveFullApiKeyEnteredInPreferences());
        orFullApiKeyLabel.addStyleName(resources.css().tabHeadingText());
        fullApiKeyCharacterListBox = new ListBox();
        fullApiKeyCharacterListBox.setEnabled(false);
        fullApiKeyLevelListBox = new CharacterOrCorporationLevelListBox(messages);
        fullApiKeyLevelListBox.setEnabled(false);
        importWithFullApiKeyTable = new FlexTable();
        andAttachLabel = new Label(messages.andAttachTo());
        andAttachLabel.addStyleName(resources.css().tabHeadingText());
        attachToCharacterLabel = new Label(messages.character() + ":");
        attachToCharacterListBox = new AttachedCharacterListBox(messages);
        attachToCharacterListBox.setEnabled(false);
        importSharingLabel = new Label(messages.sharingLevel() + ":");
        sharingLevelListBox = new SharingLevelListBox(messages);
        sharingLevelListBox.addStyleName(resources.css().sharingListBox());
        sharingLevelListBox.setEnabled(false);
        importButton = new Button(messages.import_());
        importButton.setEnabled(false);

        libraryTitleLabel = new Label(messages.library());
        libraryTitleLabel.addStyleName(resources.css().tabHeadingText());
        filterTable = new FlexTable();
        filterTableCellFormatter = filterTable.getFlexCellFormatter();
        filterLabel = new Label(messages.filter() + ":");
        filterTextBox = new TextBox();
        filterTextBox.setEnabled(false);
        filterTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                filterBlueprints(filterTextBox.getText());
            }
        });
        blueprintTable = new FlexTable();
        blueprintTableRowFormatter = blueprintTable.getRowFormatter();

        reloadButton = new Button(messages.reload());
        reloadButton.setEnabled(false);
        hideButton = new Button(messages.hide());
        hideButton.setEnabled(false);
        hideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (blueprintTable.isVisible()) {
                    filterTableCellFormatter.setVisible(0, 0, false);
                    filterTableCellFormatter.setVisible(0, 2, false);
                    filterTableCellFormatter.setVisible(0, 3, false);
                    blueprintTable.setVisible(false);
                    hideButton.setText(messages.show());
                } else {
                    filterTableCellFormatter.setVisible(0, 0, true);
                    filterTableCellFormatter.setVisible(0, 2, true);
                    filterTableCellFormatter.setVisible(0, 3, true);
                    blueprintTable.setVisible(true);
                    hideButton.setText(messages.hide());
                }
            }
        });

        corporationLibraryTitleLabel = new Label(messages.corporationLibrary());
        corporationLibraryTitleLabel.addStyleName(resources.css().tabHeadingText());
        corporationFilterTable = new FlexTable();
        corporationFilterTableCellFormatter = corporationFilterTable.getFlexCellFormatter();
        corporationFilterLabel = new Label(messages.filter() + ":");
        corporationFilterLabel.setVisible(false);
        corporationFilterTextBox = new TextBox();
        corporationFilterTextBox.setEnabled(false);
        corporationFilterTextBox.setVisible(false);
        corporationFilterTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                filterCorporationBlueprints(corporationFilterTextBox.getText());
            }
        });
        corporationBlueprintTable = new FlexTable();
        corporationBlueprintTableRowFormatter = corporationBlueprintTable.getRowFormatter();
        corporationReloadButton = new Button(messages.load());
        corporationReloadButton.setEnabled(false);
        corporationHideButton = new Button(messages.hide());
        corporationHideButton.setEnabled(false);
        corporationHideButton.setVisible(false);
        corporationHideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (corporationBlueprintTable.isVisible()) {
                    corporationFilterTableCellFormatter.setVisible(0, 0, false);
                    corporationFilterTableCellFormatter.setVisible(0, 2, false);
                    corporationFilterTableCellFormatter.setVisible(0, 3, false);
                    corporationBlueprintTable.setVisible(false);
                    corporationHideButton.setText(messages.show());
                } else {
                    corporationFilterTableCellFormatter.setVisible(0, 0, true);
                    corporationFilterTableCellFormatter.setVisible(0, 2, true);
                    corporationFilterTableCellFormatter.setVisible(0, 3, true);
                    corporationBlueprintTable.setVisible(true);
                    corporationHideButton.setText(messages.hide());
                }
            }
        });

        allianceLibraryTitleLabel = new Label(messages.allianceLibrary());
        allianceLibraryTitleLabel.addStyleName(resources.css().tabHeadingText());
        allianceFilterTable = new FlexTable();
        allianceFilterTableCellFormatter = allianceFilterTable.getFlexCellFormatter();
        allianceFilterLabel = new Label(messages.filter() + ":");
        allianceFilterLabel.setVisible(false);
        allianceFilterTextBox = new TextBox();
        allianceFilterTextBox.setEnabled(false);
        allianceFilterTextBox.setVisible(false);
        allianceFilterTextBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                filterAllianceBlueprints(allianceFilterTextBox.getText());
            }
        });
        allianceBlueprintTable = new FlexTable();
        allianceBlueprintTableRowFormatter = allianceBlueprintTable.getRowFormatter();
        allianceReloadButton = new Button(messages.load());
        allianceReloadButton.setEnabled(false);
        allianceHideButton = new Button(messages.hide());
        allianceHideButton.setEnabled(false);
        allianceHideButton.setVisible(false);
        allianceHideButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (allianceBlueprintTable.isVisible()) {
                    allianceFilterTableCellFormatter.setVisible(0, 0, false);
                    allianceFilterTableCellFormatter.setVisible(0, 2, false);
                    allianceFilterTableCellFormatter.setVisible(0, 3, false);
                    allianceBlueprintTable.setVisible(false);
                    allianceHideButton.setText(messages.show());
                } else {
                    allianceFilterTableCellFormatter.setVisible(0, 0, true);
                    allianceFilterTableCellFormatter.setVisible(0, 2, true);
                    allianceFilterTableCellFormatter.setVisible(0, 3, true);
                    allianceBlueprintTable.setVisible(true);
                    allianceHideButton.setText(messages.hide());
                }
            }
        });

        blueprintIdToBlueprintMap = new HashMap<Long, BlueprintDto>();
        blueprintIdToRowMap = new HashMap<Long, Integer>();
        blueprintNameToRowsMap = new HashMap<String, List<Integer>>();
        blueprintToEditableDetailsMap = new HashMap<BlueprintDto, EditableBlueprintDetails>();
        corporationBlueprintIdToBlueprintMap = new HashMap<Long, BlueprintDto>();
        corporationBlueprintNameToRowsMap = new HashMap<String, List<Integer>>();
        corporationBlueprintToEditableDetailsMap = new HashMap<BlueprintDto, EditableBlueprintDetails>();
        allianceBlueprintIdToBlueprintMap = new HashMap<Long, BlueprintDto>();
        allianceBlueprintNameToRowsMap = new HashMap<String, List<Integer>>();
        allianceBlueprintToEditableDetailsMap = new HashMap<BlueprintDto, EditableBlueprintDetails>();

        handlerRegistrations = new ArrayList<HandlerRegistration>();
        handlerRegistrationsForCorporation = new ArrayList<HandlerRegistration>();
        handlerRegistrationsForAlliance = new ArrayList<HandlerRegistration>();
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

        newBlueprintTable.setWidget(0, 0, enterBlueprintNameLabel);
        newBlueprintTable.setWidget(0, 1, newBlueprintSuggestBox);
        newBlueprintMePeTable.setWidget(0, 0, enterMeLabel);
        newBlueprintMePeTable.setWidget(0, 1, meTextBox);
        newBlueprintMePeTable.setWidget(0, 2, enterPeLabel);
        newBlueprintMePeTable.setWidget(0, 3, peTextBox);
        newBlueprintMePeTable.setWidget(0, 4, addButton);
        container.add(newBlueprintTable);
        container.add(newBlueprintMePeTable);
        container.add(hintCanAddBothBpoAndBpcLabel);

        importXmlDescriptionTable.setWidget(0, 0, descriptionYouCanImportXmlLabel);
        importXmlDescriptionTable.setWidget(1, 0, charIndustryJobAnchor);
        importXmlDescriptionTable.setWidget(2, 0, corpIndustryJobAnchor);

        importPanel.add(postXmlLabel);
        importPanel.add(importXmlDescriptionTable);
        importPanel.add(importXmlTextArea);
        importPanel.add(noteOnlyBposLabel);
        importPanel.add(noteSinceApiDoesNotProvide);
        importPanel.add(hintYouCanImportLabel);
        importPanel.add(hintBlueprintItemIdsLabel);
        importPanel.add(orIfLazyLabel);
        importOneTimeTable.setWidget(0, 0, new Label(messages.fullApiKey() + ":"));
        importOneTimeTable.setWidget(0, 1, oneTimeFullApiKeyTextBox);
        importOneTimeTable.setWidget(1, 0, new Label(messages.userID() + ":"));
        importOneTimeTable.setWidget(1, 1, oneTimeUserIdTextBox);
        importOneTimeTable.setWidget(2, 0, new Label(messages.characterID() + ":"));
        importOneTimeTable.setWidget(2, 1, oneTimeCharacterIdTextBox);
        importOneTimeTable.setWidget(3, 0, new Label(messages.level() + ":"));
        importOneTimeTable.setWidget(3, 1, oneTimeLevelListBox);
        importPanel.add(importOneTimeTable);
        importPanel.add(noteOneTime);
        importPanel.add(orPostCsvLabel);
        importCsvDescriptionTable.setWidget(0, 0, descriptionYouCanImportCsvLabel);
        importPanel.add(importCsvDescriptionTable);
        importPanel.add(importCsvTextArea);
        importPanel.add(orFullApiKeyLabel);
        importWithFullApiKeyTable.setWidget(0, 0, new Label(messages.character() + ":"));

        importWithFullApiKeyTable.setWidget(0, 1, fullApiKeyCharacterListBox);
        importWithFullApiKeyTable.setWidget(1, 0, new Label(messages.level() + ":"));
        importWithFullApiKeyTable.setWidget(1, 1, fullApiKeyLevelListBox);
        importPanel.add(importWithFullApiKeyTable);
        importPanel.add(andAttachLabel);
        importTable.setWidget(0, 0, attachToCharacterLabel);
        importTable.setWidget(0, 1, attachToCharacterListBox);
        importTable.setWidget(0, 2, importSharingLabel);
        importTable.setWidget(0, 3, sharingLevelListBox);
        importTable.setWidget(0, 4, importButton);
        importPanel.add(importTable);
        importDisclosurePanel.setContent(importPanel);
        container.add(importDisclosurePanel);

        container.add(libraryTitleLabel);

        filterTable.setWidget(0, 0, reloadButton);
        filterTable.setWidget(0, 1, hideButton);
        filterTable.setWidget(0, 2, filterLabel);
        filterTable.setWidget(0, 3, filterTextBox);
        container.add(filterTable);

        container.add(blueprintTable);

        container.add(corporationLibraryTitleLabel);
        corporationFilterTable.setWidget(0, 0, corporationReloadButton);
        corporationFilterTable.setWidget(0, 1, corporationHideButton);
        corporationFilterTable.setWidget(0, 2, corporationFilterLabel);
        corporationFilterTable.setWidget(0, 3, corporationFilterTextBox);
        container.add(corporationFilterTable);
        container.add(corporationBlueprintTable);

        container.add(allianceLibraryTitleLabel);
        allianceFilterTable.setWidget(0, 0, allianceReloadButton);
        allianceFilterTable.setWidget(0, 1, allianceHideButton);
        allianceFilterTable.setWidget(0, 2, allianceFilterLabel);
        allianceFilterTable.setWidget(0, 3, allianceFilterTextBox);
        container.add(allianceFilterTable);
        container.add(allianceBlueprintTable);
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
    public SuggestBox getNewBlueprintTextBox() {
        return newBlueprintSuggestBox;
    }

    @Override
    public TextBox getNewBlueprintMeTextBox() {
        return meTextBox;
    }

    @Override
    public TextBox getNewBlueprintPeTextBox() {
        return peTextBox;
    }

    @Override
    public Button getAddNewBlueprintButton() {
        return addButton;
    }

    @Override
    public TextArea getImportXmlTextArea() {
        return importXmlTextArea;
    }

    @Override
    public TextBox getOneTimeFullApiKeyTextBox() {
        return oneTimeFullApiKeyTextBox;
    }

    @Override
    public TextBox getOneTimeUserIdTextBox() {
        return oneTimeUserIdTextBox;
    }

    @Override
    public TextBox getOneTimeCharacterIdTextBox() {
        return oneTimeCharacterIdTextBox;
    }

    @Override
    public ListBox getOneTimeLevelListBox() {
        return oneTimeLevelListBox;
    }

    @Override
    public TextArea getImportCsvTextArea() {
        return importCsvTextArea;
    }

    @Override
    public ListBox getFullApiKeyCharacterListBox() {
        return fullApiKeyCharacterListBox;
    }

    @Override
    public ListBox getFullApiKeyLevelListBox() {
        return fullApiKeyLevelListBox;
    }

    @Override
    public void setAttachedCharacterNames(List<CharacterNameDto> attachedCharacterNames) {
        this.attachedCharacterNames = attachedCharacterNames;
    }

    @Override
    public AttachedCharacterListBox getAttachedCharacterNames() {
        return attachToCharacterListBox;
    }

    @Override
    public void setSharingLevels(List<SharingLevel> sharingLevels) {
        this.sharingLevels = sharingLevels;
        sharingLevelListBox.clear();
        for (SharingLevel sharingLevel : sharingLevels) {
            sharingLevelListBox.addItem(sharingLevel);
        }
    }

    @Override
    public void setFullApiKeys(List<ApiKeyDto> fullApiKeys) {
        fullApiKeyCharacterListBox.clear();
        fullApiKeyCharacterListBox.addItem(messages.none(), "-1");
        for (ApiKeyDto fullApiKey : fullApiKeys) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ApiKeyCharacterInfoDto apiKeyCharacterInfo : fullApiKey.getCharacterInfos()) {
                fullApiKeyCharacterListBox.addItem(apiKeyCharacterInfo.getName(), apiKeyCharacterInfo.getCharacterID().toString());
            }
        }
    }

    @Override
    public SharingLevelListBox getSharingLevels() {
        return sharingLevelListBox;
    }

    @Override
    public Button getImportButton() {
        return importButton;
    }

    @Override
    public Map<Long, BlueprintDto> getBlueprintMap() {
        return blueprintIdToBlueprintMap;
    }

    @Override
    public Map<Long, BlueprintDto> getCorporationBlueprintMap() {
        return corporationBlueprintIdToBlueprintMap;
    }

    @Override
    public Map<Long, BlueprintDto> getAllianceBlueprintMap() {
        return allianceBlueprintIdToBlueprintMap;
    }

    @Override
    public Map<BlueprintDto, EditableBlueprintDetails> getBlueprintEditableDetailsMap() {
        return blueprintToEditableDetailsMap;
    }

    @Override
    public Map<BlueprintDto, EditableBlueprintDetails> getCorporationBlueprintEditableDetailsMap() {
        return corporationBlueprintToEditableDetailsMap;
    }

    @Override
    public Map<BlueprintDto, EditableBlueprintDetails> getAllianceBlueprintEditableDetailsMap() {
        return allianceBlueprintToEditableDetailsMap;
    }

    @Override
    public void setCurrentBlueprints(List<BlueprintDto> blueprints) {
        blueprintTable.removeAllRows();
        blueprintNameToRowsMap.clear();
        blueprintIdToBlueprintMap.clear();
        blueprintIdToRowMap.clear();
        blueprintNameToRowsMap.clear();
        blueprintToEditableDetailsMap.clear();
        for (BlueprintDto blueprint : blueprints) {
            drawBlueprint(blueprint);
        }
    }

    @Override
    public void setCurrentCorporationBlueprints(List<BlueprintDto> blueprints) {
        if (corporationReloadButton.getText().equals(messages.load())) {
            corporationReloadButton.setText(messages.reload());
            corporationHideButton.setVisible(true);
            corporationFilterLabel.setVisible(true);
            corporationFilterTextBox.setVisible(true);
            corporationFilterTableCellFormatter.setVisible(0, 1, true);
            corporationFilterTableCellFormatter.setVisible(0, 2, true);
            corporationFilterTableCellFormatter.setVisible(0, 3, true);
        }

        corporationBlueprintTable.removeAllRows();
        corporationBlueprintNameToRowsMap.clear();
        corporationBlueprintIdToBlueprintMap.clear();
        corporationBlueprintNameToRowsMap.clear();
        corporationBlueprintToEditableDetailsMap.clear();
        for (BlueprintDto blueprint : blueprints) {
            drawCorporationBlueprint(blueprint);
        }
    }

    @Override
    public void setCurrentAllianceBlueprints(List<BlueprintDto> blueprints) {
        if (allianceReloadButton.getText().equals(messages.load())) {
            allianceReloadButton.setText(messages.reload());
            allianceHideButton.setVisible(true);
            allianceFilterLabel.setVisible(true);
            allianceFilterTextBox.setVisible(true);
            allianceFilterTableCellFormatter.setVisible(0, 1, true);
            allianceFilterTableCellFormatter.setVisible(0, 2, true);
            allianceFilterTableCellFormatter.setVisible(0, 3, true);
        }

        allianceBlueprintTable.removeAllRows();
        allianceBlueprintNameToRowsMap.clear();
        allianceBlueprintIdToBlueprintMap.clear();
        allianceBlueprintNameToRowsMap.clear();
        allianceBlueprintToEditableDetailsMap.clear();
        for (BlueprintDto blueprint : blueprints) {
            drawAllianceBlueprint(blueprint);
        }
    }

    @Override
    public void addBlueprint(BlueprintDto blueprint) {
        drawBlueprint(blueprint);
    }

    @Override
    public void deleteBlueprint(Long blueprintID) {
        BlueprintDto blueprint = blueprintIdToBlueprintMap.get(blueprintID);
        Integer row = blueprintIdToRowMap.get(blueprintID);
        blueprintNameToRowsMap.get(blueprint.getItemTypeName()).remove(row);
        blueprintTableRowFormatter.setVisible(row, false);
        blueprintTableRowFormatter.setVisible(row + 1, false);
    }

    @Override
    public Button getReloadButton() {
        return reloadButton;
    }

    @Override
    public Button getHideButton() {
        return hideButton;
    }

    @Override
    public TextBox getFilterTextBox() {
        return filterTextBox;
    }

    @Override
    public Button getCorporationReloadButton() {
        return corporationReloadButton;
    }

    @Override
    public Button getCorporationHideButton() {
        return corporationHideButton;
    }

    @Override
    public TextBox getCorporationFilterTextBox() {
        return corporationFilterTextBox;
    }

    @Override
    public Button getAllianceReloadButton() {
        return allianceReloadButton;
    }

    @Override
    public Button getAllianceHideButton() {
        return allianceHideButton;
    }

    @Override
    public TextBox getAllianceFilterTextBox() {
        return allianceFilterTextBox;
    }

    @Override
    public List<HandlerRegistration> getHandlerRegistrations() {
        return handlerRegistrations;
    }

    @Override
    public List<HandlerRegistration> getHandlerRegistrationsForCorporation() {
        return handlerRegistrationsForCorporation;
    }

    @Override
    public List<HandlerRegistration> getHandlerRegistrationsForAlliance() {
        return handlerRegistrationsForAlliance;
    }

    @Override
    public void resetCorporationAndAllianceBlueprints() {
        corporationReloadButton.setText(messages.load());
        corporationFilterTableCellFormatter.setVisible(0, 1, false);
        corporationFilterTableCellFormatter.setVisible(0, 2, false);
        corporationFilterTableCellFormatter.setVisible(0, 3, false);
        corporationBlueprintTable.removeAllRows();

        allianceReloadButton.setText(messages.load());
        allianceFilterTableCellFormatter.setVisible(0, 1, false);
        allianceFilterTableCellFormatter.setVisible(0, 2, false);
        allianceFilterTableCellFormatter.setVisible(0, 3, false);
        allianceBlueprintTable.removeAllRows();
    }

    private void filterBlueprints(String text) {
        List<Integer> rowsToShow = new ArrayList<Integer>();
        for (Map.Entry<String, List<Integer>> mapEntry : blueprintNameToRowsMap.entrySet()) {
            String blueprintName = mapEntry.getKey();
            List<Integer> rows = mapEntry.getValue();
            for (Integer row : rows) {
                if (text.length() == 0 || blueprintName.toUpperCase().startsWith(text.toUpperCase().trim())) {
                    rowsToShow.add(row);
                }
            }
        }

        for (int i = 0; i < blueprintTable.getRowCount(); i++) {
            if (rowsToShow.contains(i)) {
                blueprintTableRowFormatter.setVisible(i, true);
            } else {
                blueprintTableRowFormatter.setVisible(i, false);
            }
        }
    }

    private void filterCorporationBlueprints(String text) {
        List<Integer> rowsToShow = new ArrayList<Integer>();
        for (Map.Entry<String, List<Integer>> mapEntry : corporationBlueprintNameToRowsMap.entrySet()) {
            String blueprintName = mapEntry.getKey();
            List<Integer> rows = mapEntry.getValue();
            for (Integer row : rows) {
                if (text.length() == 0 || blueprintName.toUpperCase().startsWith(text.toUpperCase().trim())) {
                    rowsToShow.add(row);
                }
            }
        }

        for (int i = 0; i < corporationBlueprintTable.getRowCount(); i++) {
            if (rowsToShow.contains(i)) {
                corporationBlueprintTableRowFormatter.setVisible(i, true);
            } else {
                corporationBlueprintTableRowFormatter.setVisible(i, false);
            }
        }
    }

    private void filterAllianceBlueprints(String text) {
        List<Integer> rowsToShow = new ArrayList<Integer>();
        for (Map.Entry<String, List<Integer>> mapEntry : allianceBlueprintNameToRowsMap.entrySet()) {
            String blueprintName = mapEntry.getKey();
            List<Integer> rows = mapEntry.getValue();
            for (Integer row : rows) {
                if (text.length() == 0 || blueprintName.toUpperCase().startsWith(text.toUpperCase().trim())) {
                    rowsToShow.add(row);
                }
            }
        }

        for (int i = 0; i < allianceBlueprintTable.getRowCount(); i++) {
            if (rowsToShow.contains(i)) {
                allianceBlueprintTableRowFormatter.setVisible(i, true);
            } else {
                allianceBlueprintTableRowFormatter.setVisible(i, false);
            }
        }
    }

    private void drawBlueprint(final BlueprintDto blueprint) {
        EditableBlueprintDetails editableBlueprintDetails = new EditableBlueprintDetails();
        blueprintToEditableDetailsMap.put(blueprint, editableBlueprintDetails);

        final int index = blueprintTable.getRowCount();
        Long blueprintTypeID = blueprint.getItemTypeID();
        String blueprintTypeName = blueprint.getItemTypeName();
        List<Integer> rows = blueprintNameToRowsMap.get(blueprintTypeName);
        if (rows == null) {
            rows = new ArrayList<Integer>();
            blueprintNameToRowsMap.put(blueprintTypeName, rows);
        }
        rows.add(index);
        blueprintIdToBlueprintMap.put(blueprint.getId(), blueprint);
        blueprintIdToRowMap.put(blueprint.getId(), index);

        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(blueprintTypeID);
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.setTitle(blueprintTypeName);
        blueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, blueprint.getItemTypeID(), blueprint.getItemID());
        blueprintTable.setWidget(index, 0, imageItemInfoLink);
        blueprintTable.setWidget(index, 1, new EveItemInfoLink(constants, urlMessages, ccpJsMessages, blueprintTypeName, blueprintTypeID));
        blueprintTable.setWidget(index, 2, new Label(messages.me() + ":"));
        Label meLabel = new Label(String.valueOf(blueprint.getMaterialLevel()));
        blueprintTable.setWidget(index, 3, meLabel);
        blueprintTable.setWidget(index, 4, new Label(messages.pe() + ":"));
        Label peLabel = new Label(String.valueOf(blueprint.getProductivityLevel()));
        blueprintTable.setWidget(index, 5, peLabel);
        Button detailsButton = new Button(messages.details());
        blueprintTable.setWidget(index, 6, detailsButton);
        Button editButton = new Button(messages.edit());
        blueprintTable.setWidget(index, 7, editButton);
        Button deleteButton = new Button(messages.delete());
        blueprintTable.setWidget(index, 8, deleteButton);

        FlexTable.FlexCellFormatter blueprintTableCellFormatter = blueprintTable.getFlexCellFormatter();
        Image spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().blueprintSpinnerImage());
        FlexTable detailsTable = new FlexTable();
        detailsTable.setWidget(0, 0, spinnerImage);
        blueprintTable.setWidget(index + 1, 0, detailsTable);
        blueprintTableCellFormatter.setColSpan(index + 1, 0, 9);
        blueprintTableRowFormatter.setVisible(index + 1, false);

        handlerRegistrations.add(detailsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (blueprintTableRowFormatter.isVisible(index + 1)) {
                    blueprintTableRowFormatter.setVisible(index + 1, false);
                } else {
                    blueprintTableRowFormatter.setVisible(index + 1, true);
                }
            }
        }));

        TextBox meLevelTextBox = new TextBox();
        meLevelTextBox.setValue(meLabel.getText());
        meLevelTextBox.addStyleName(resources.css().mePeInput());
        TextBox peLevelTextBox = new TextBox();
        peLevelTextBox.setValue(peLabel.getText());
        peLevelTextBox.addStyleName(resources.css().mePeInput());
        final DecoratedPopupPanel editPopup = new DecoratedPopupPanel(true);
        FlexTable editPopupFlexTable = new FlexTable();
        editPopupFlexTable.setWidget(0, 0, new Label(messages.materialLevel() + ":"));
        editPopupFlexTable.setWidget(0, 1, meLevelTextBox);
        editPopupFlexTable.setWidget(1, 0, new Label(messages.productivityLevel() + ":"));
        editPopupFlexTable.setWidget(1, 1, peLevelTextBox);
        editPopupFlexTable.setWidget(2, 0, new Label(messages.attachedCharacter() + ":"));
        AttachedCharacterListBox editAttachToCharacterListBox = new AttachedCharacterListBox(messages);
        editAttachToCharacterListBox.addItem(messages.none(), "-1");
        for (CharacterNameDto characterNameDto : attachedCharacterNames) {
            editAttachToCharacterListBox.addItem(characterNameDto);
        }
        editAttachToCharacterListBox.setAttachedCharacter(blueprint.getAttachedCharacterInfo());
        editPopupFlexTable.setWidget(2, 1, editAttachToCharacterListBox);
        editPopupFlexTable.setWidget(3, 0, new Label(messages.sharingLevel() + ":"));
        SharingLevelListBox editSharingLevelListBox = new SharingLevelListBox(messages);
        for (SharingLevel sharingLevel : sharingLevels) {
            editSharingLevelListBox.addItem(sharingLevel);
        }
        editSharingLevelListBox.setSharingLevel(blueprint.getSharingLevel());
        editPopupFlexTable.setWidget(3, 1, editSharingLevelListBox);
        editPopupFlexTable.setWidget(4, 0, new Label(messages.itemID() + ":"));
        TextBox itemIdTextBox = new TextBox();
        itemIdTextBox.addStyleName(resources.css().itemIdInput());
        if (blueprint.getItemID() != null) {
            itemIdTextBox.setText(String.valueOf(blueprint.getItemID()));
        }
        editPopupFlexTable.setWidget(4, 1, itemIdTextBox);
        Button saveButton = new Button(messages.save());
        editPopupFlexTable.setWidget(5, 0, saveButton);
        editPopupFlexTable.getFlexCellFormatter().setColSpan(5, 0, 2);
        editPopup.setWidget(editPopupFlexTable);

        final DecoratedPopupPanel deletePopup = new DecoratedPopupPanel(true);
        FlexTable deletePopupTable = new FlexTable();
        deletePopupTable.setWidget(0, 0, new Label(messages.areYouSure()));
        Button confirmDeleteButton = new Button(messages.yes());
        deletePopupTable.setWidget(1, 0, confirmDeleteButton);
        deletePopup.setWidget(deletePopupTable);

        editableBlueprintDetails.setSpinnerImage(spinnerImage);
        editableBlueprintDetails.setMeLevelLabel(meLabel);
        editableBlueprintDetails.setPeLevelLabel(peLabel);
        editableBlueprintDetails.setMeLevelTextBox(meLevelTextBox);
        editableBlueprintDetails.setPeLevelTextBox(peLevelTextBox);
        editableBlueprintDetails.setAttachedCharacterListBox(editAttachToCharacterListBox);
        editableBlueprintDetails.setSharingLevelListBox(editSharingLevelListBox);
        editableBlueprintDetails.setItemIdTextBox(itemIdTextBox);
        editableBlueprintDetails.setSaveButton(saveButton);
        editableBlueprintDetails.setDetailsButton(detailsButton);
        editableBlueprintDetails.setDeleteButton(confirmDeleteButton);
        editableBlueprintDetails.setDetailsTable(detailsTable);

        handlerRegistrations.add(editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft() + 10;
                int top = source.getAbsoluteTop() + 10;
                editPopup.setPopupPosition(left, top);
                editPopup.show();
            }
        }));

        handlerRegistrations.add(deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Widget source = (Widget) event.getSource();
                int left = source.getAbsoluteLeft() + 10;
                int top = source.getAbsoluteTop() + 10;
                deletePopup.setPopupPosition(left, top);
                deletePopup.show();
            }
        }));

        handlerRegistrations.add(saveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editPopup.hide();
            }
        }));

        handlerRegistrations.add(confirmDeleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deletePopup.hide();
            }
        }));
    }

    private void drawCorporationBlueprint(final BlueprintDto blueprint) {
        EditableBlueprintDetails editableBlueprintDetails = new EditableBlueprintDetails();
        corporationBlueprintToEditableDetailsMap.put(blueprint, editableBlueprintDetails);

        final int index = corporationBlueprintTable.getRowCount();
        Long blueprintTypeID = blueprint.getItemTypeID();
        String blueprintTypeName = blueprint.getItemTypeName();
        List<Integer> rows = corporationBlueprintNameToRowsMap.get(blueprintTypeName);
        if (rows == null) {
            rows = new ArrayList<Integer>();
            corporationBlueprintNameToRowsMap.put(blueprintTypeName, rows);
        }
        rows.add(index);
        corporationBlueprintIdToBlueprintMap.put(blueprint.getId(), blueprint);

        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(blueprintTypeID);
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.setTitle(blueprintTypeName);
        blueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, blueprint.getItemTypeID(), blueprint.getItemID());
        corporationBlueprintTable.setWidget(index, 0, imageItemInfoLink);
        corporationBlueprintTable.setWidget(index, 1, new EveItemInfoLink(constants, urlMessages, ccpJsMessages, blueprintTypeName, blueprintTypeID));
        corporationBlueprintTable.setWidget(index, 2, new Label(messages.me() + ":"));
        Label meLabel = new Label(String.valueOf(blueprint.getMaterialLevel()));
        corporationBlueprintTable.setWidget(index, 3, meLabel);
        corporationBlueprintTable.setWidget(index, 4, new Label(messages.pe() + ":"));
        Label peLabel = new Label(String.valueOf(blueprint.getProductivityLevel()));
        corporationBlueprintTable.setWidget(index, 5, peLabel);
        Button detailsButton = new Button(messages.details());
        corporationBlueprintTable.setWidget(index, 6, detailsButton);
        CharacterInfoDto attachedCharacterInfo = blueprint.getAttachedCharacterInfo();
        Image characterImage = new Image(urlMessages.imgEveCharacter32Url(constants.eveGateImagesUrl(), attachedCharacterInfo.getCharacterID()));
        characterImage.addStyleName(resources.css().image32());
        characterImage.setTitle(attachedCharacterInfo.getName());
        corporationBlueprintTable.setWidget(index, 7, new EveCharacterInfoLink(ccpJsMessages, characterImage, attachedCharacterInfo.getCharacterID()));

        FlexTable.FlexCellFormatter corporationBlueprintTableCellFormatter = corporationBlueprintTable.getFlexCellFormatter();
        Image spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().blueprintSpinnerImage());
        FlexTable detailsTable = new FlexTable();
        detailsTable.setWidget(0, 0, spinnerImage);
        corporationBlueprintTable.setWidget(index + 1, 0, detailsTable);
        corporationBlueprintTableCellFormatter.setColSpan(index + 1, 0, 8);
        corporationBlueprintTableRowFormatter.setVisible(index + 1, false);

        handlerRegistrationsForCorporation.add(detailsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (corporationBlueprintTableRowFormatter.isVisible(index + 1)) {
                    corporationBlueprintTableRowFormatter.setVisible(index + 1, false);
                } else {
                    corporationBlueprintTableRowFormatter.setVisible(index + 1, true);
                }
            }
        }));

        editableBlueprintDetails.setSpinnerImage(spinnerImage);
        editableBlueprintDetails.setMeLevelLabel(meLabel);
        editableBlueprintDetails.setPeLevelLabel(peLabel);
        editableBlueprintDetails.setDetailsButton(detailsButton);
        editableBlueprintDetails.setDetailsTable(detailsTable);
    }

    private void drawAllianceBlueprint(final BlueprintDto blueprint) {
        EditableBlueprintDetails editableBlueprintDetails = new EditableBlueprintDetails();
        allianceBlueprintToEditableDetailsMap.put(blueprint, editableBlueprintDetails);

        final int index = allianceBlueprintTable.getRowCount();
        Long blueprintTypeID = blueprint.getItemTypeID();
        String blueprintTypeName = blueprint.getItemTypeName();
        List<Integer> rows = allianceBlueprintNameToRowsMap.get(blueprintTypeName);
        if (rows == null) {
            rows = new ArrayList<Integer>();
            allianceBlueprintNameToRowsMap.put(blueprintTypeName, rows);
        }
        rows.add(index);
        allianceBlueprintIdToBlueprintMap.put(blueprint.getId(), blueprint);

        String blueprintImageUrl = imageUrlProvider.getBlueprintImageUrl(blueprintTypeID);
        Image blueprintImage = new Image(blueprintImageUrl);
        blueprintImage.setTitle(blueprintTypeName);
        blueprintImage.addStyleName(resources.css().image32());
        EveItemInfoLink imageItemInfoLink = new EveItemInfoLink(ccpJsMessages, blueprintImage, blueprint.getItemTypeID(), blueprint.getItemID());
        allianceBlueprintTable.setWidget(index, 0, imageItemInfoLink);
        allianceBlueprintTable.setWidget(index, 1, new EveItemInfoLink(constants, urlMessages, ccpJsMessages, blueprintTypeName, blueprintTypeID));
        allianceBlueprintTable.setWidget(index, 2, new Label(messages.me() + ":"));
        Label meLabel = new Label(String.valueOf(blueprint.getMaterialLevel()));
        allianceBlueprintTable.setWidget(index, 3, meLabel);
        allianceBlueprintTable.setWidget(index, 4, new Label(messages.pe() + ":"));
        Label peLabel = new Label(String.valueOf(blueprint.getProductivityLevel()));
        allianceBlueprintTable.setWidget(index, 5, peLabel);
        Button detailsButton = new Button(messages.details());
        allianceBlueprintTable.setWidget(index, 6, detailsButton);
        CharacterInfoDto attachedCharacterInfo = blueprint.getAttachedCharacterInfo();
        Image corporationImage = new Image(urlMessages.imgEveCorporation32Url(constants.eveGateImagesUrl(), attachedCharacterInfo.getCorporationID()));
        corporationImage.addStyleName(resources.css().image32());
        corporationImage.setTitle(attachedCharacterInfo.getCorporationName());
        allianceBlueprintTable.setWidget(index, 7, new EveCorporationInfoLink(constants, urlMessages, ccpJsMessages, corporationImage, attachedCharacterInfo.getCorporationID()));
        Image characterImage = new Image(urlMessages.imgEveCharacter32Url(constants.eveGateImagesUrl(), attachedCharacterInfo.getCharacterID()));
        characterImage.addStyleName(resources.css().image32());
        characterImage.setTitle(attachedCharacterInfo.getName());
        allianceBlueprintTable.setWidget(index, 8, new EveCharacterInfoLink(ccpJsMessages, characterImage, attachedCharacterInfo.getCharacterID()));

        FlexTable.FlexCellFormatter allianceBlueprintTableCellFormatter = allianceBlueprintTable.getFlexCellFormatter();
        Image spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().blueprintSpinnerImage());
        FlexTable detailsTable = new FlexTable();
        detailsTable.setWidget(0, 0, spinnerImage);
        allianceBlueprintTable.setWidget(index + 1, 0, detailsTable);
        allianceBlueprintTableCellFormatter.setColSpan(index + 1, 0, 9);
        allianceBlueprintTableRowFormatter.setVisible(index + 1, false);

        handlerRegistrationsForAlliance.add(detailsButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (allianceBlueprintTableRowFormatter.isVisible(index + 1)) {
                    allianceBlueprintTableRowFormatter.setVisible(index + 1, false);
                } else {
                    allianceBlueprintTableRowFormatter.setVisible(index + 1, true);
                }
            }
        }));

        editableBlueprintDetails.setSpinnerImage(spinnerImage);
        editableBlueprintDetails.setMeLevelLabel(meLabel);
        editableBlueprintDetails.setPeLevelLabel(peLabel);
        editableBlueprintDetails.setDetailsButton(detailsButton);
        editableBlueprintDetails.setDetailsTable(detailsTable);
    }
}