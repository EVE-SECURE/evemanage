package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyCharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.widget.EveAllianceInfoLink;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;
import lv.odylab.evemanage.client.widget.EveCorporationInfoLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesTabView implements PreferencesTabPresenter.Display {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

    private HorizontalPanel headerPanel;
    private Label headerLabel;
    private Image spinnerImage;

    private FlexTable errorMessageTable;
    private Label errorMessageLabel;
    private Image errorImage;

    private Label hintOnlyForSharingLabel;
    private Label charactersHeadingLabel;
    private FlexTable charactersFlexTable;
    private FlexTable newCharacterFlexTable;
    private ListBox newCharacterListBox;
    private Label addCharacterLabel;
    private Button addNewCharacterButton;
    private Label hintCharactersAreNeededLabel;
    private Label hintSameSharingLevelLabel;
    private Map<CharacterDto, Button> characterDeleteButtonMap;
    private Map<String, Image> characterImageMap;

    private Label apiKeysHeadingLabel;
    private FlexTable apiKeysFlexTable;
    private FlexTable newApiKeyFlexTable;
    private TextBox newApiKeyUserIdTextBox;
    private TextBox newApiKeyStringTextBox;
    private Button addNewApiKeyButton;
    private Label enterUserIdLabel;
    private Label enterApiKeyLabel;
    private Label noteKeysAreCheckedLabel;
    private Label hintUseThisLinkLabel;
    private Map<ApiKeyDto, Button> apiKeyDeleteButtonMap;

    @Inject
    public PreferencesTabView(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

        headerPanel = new HorizontalPanel();
        headerPanel.addStyleName(resources.css().tabHeaderPanel());
        headerLabel = new Label(messages.preferences());
        headerLabel.addStyleName(resources.css().tabHeaderText());
        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.addStyleName(resources.css().tabHeaderSpinner());

        errorMessageTable = new FlexTable();
        errorMessageLabel = new Label();
        errorMessageLabel.addStyleName(resources.css().errorLabel());
        errorImage = new Image(resources.errorIcon());
        errorImage.setTitle(messages.error());

        apiKeyDeleteButtonMap = new HashMap<ApiKeyDto, Button>();
        characterDeleteButtonMap = new HashMap<CharacterDto, Button>();
        characterImageMap = new HashMap<String, Image>();

        hintOnlyForSharingLabel = new Label(messages.hintOnlyForSharing() + ".");
        hintOnlyForSharingLabel.addStyleName(resources.css().hintLabel());

        charactersHeadingLabel = new Label(messages.characters());
        charactersHeadingLabel.addStyleName(resources.css().tabHeadingText());

        newCharacterFlexTable = new FlexTable();
        addCharacterLabel = new Label(messages.addCharacter() + ":");
        newCharacterListBox = new ListBox();
        newCharacterListBox.setEnabled(false);
        addNewCharacterButton = new Button(messages.add());
        addNewCharacterButton.setEnabled(false);

        hintCharactersAreNeededLabel = new Label(messages.hintCharactersAreNeeded() + ".");
        hintCharactersAreNeededLabel.addStyleName(resources.css().hintLabel());
        hintSameSharingLevelLabel = new Label(messages.hintSameSharingLevel() + ".");
        hintSameSharingLevelLabel.addStyleName(resources.css().hintLabel());

        apiKeysHeadingLabel = new Label(messages.apiKeys());
        apiKeysHeadingLabel.addStyleName(resources.css().tabHeadingText());

        apiKeysFlexTable = new FlexTable();
        newApiKeyFlexTable = new FlexTable();
        newApiKeyUserIdTextBox = new TextBox();
        newApiKeyUserIdTextBox.addStyleName(resources.css().apiKeyUserIdInput());
        newApiKeyUserIdTextBox.setEnabled(false);
        newApiKeyStringTextBox = new TextBox();
        newApiKeyStringTextBox.addStyleName(resources.css().apiKeyStringInput());
        newApiKeyStringTextBox.setEnabled(false);
        enterUserIdLabel = new Label(messages.enterUserID() + ":");
        enterApiKeyLabel = new Label(messages.enterApiKey() + ":");
        addNewApiKeyButton = new Button(messages.add());
        addNewApiKeyButton.setEnabled(false);

        noteKeysAreCheckedLabel = new Label(messages.noteKeysAreChecked() + ".");
        noteKeysAreCheckedLabel.addStyleName(resources.css().noteLabel());
        Anchor anchor = new Anchor(messages.eveApiKeyManagement(), constants.eveApiKeyManagementPageUrl());
        anchor.setTarget("_blank");
        hintUseThisLinkLabel = new HTML(messages.hintUserThisLink(anchor.toString()) + ".");
        hintUseThisLinkLabel.addStyleName(resources.css().hintLabel());
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

        container.add(hintOnlyForSharingLabel);

        container.add(charactersHeadingLabel);

        charactersFlexTable = new FlexTable();
        container.add(charactersFlexTable);

        newCharacterFlexTable.setWidget(0, 0, addCharacterLabel);
        newCharacterFlexTable.setWidget(0, 1, newCharacterListBox);
        newCharacterFlexTable.setWidget(0, 2, addNewCharacterButton);
        container.add(newCharacterFlexTable);

        container.add(hintCharactersAreNeededLabel);
        container.add(hintSameSharingLevelLabel);

        container.add(apiKeysHeadingLabel);

        container.add(apiKeysFlexTable);

        FlexTable.FlexCellFormatter newApiKeyFlexTableFormatter = newApiKeyFlexTable.getFlexCellFormatter();
        newApiKeyFlexTable.setWidget(0, 0, enterUserIdLabel);
        newApiKeyFlexTable.setWidget(0, 1, newApiKeyUserIdTextBox);
        newApiKeyFlexTableFormatter.setColSpan(0, 1, 2);
        newApiKeyFlexTable.setWidget(1, 0, enterApiKeyLabel);
        newApiKeyFlexTable.setWidget(1, 1, newApiKeyStringTextBox);
        newApiKeyFlexTable.setWidget(1, 2, addNewApiKeyButton);
        container.add(newApiKeyFlexTable);

        container.add(noteKeysAreCheckedLabel);
        container.add(hintUseThisLinkLabel);
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
    public void setCharacters(List<CharacterDto> characters) {
        characterImageMap.clear();
        charactersFlexTable.removeAllRows();
        charactersFlexTable.clear();
        for (CharacterDto character : characters) {
            drawCharacter(character);
        }
    }

    @Override
    public void setMainCharacter(CharacterDto mainCharacter) {
        for (Map.Entry<String, Image> characterImageEntry : characterImageMap.entrySet()) {
            String characterName = characterImageEntry.getKey();
            Image characterImage = characterImageEntry.getValue();
            if (!mainCharacter.getName().equals(characterName)) {
                characterImage.addStyleName(resources.css().imageOpacity05());
                characterImage.addStyleName(resources.css().cursorHand());
                characterImage.setTitle(characterName + ", " + messages.clickToSetAsMainCharacter());
            } else {
                characterImage.removeStyleName(resources.css().imageOpacity05());
                characterImage.removeStyleName(resources.css().cursorHand());
                characterImage.setTitle(characterName + ", " + messages.currentMainCharacter());
            }
        }
    }

    @Override
    public Map<String, Image> getCharacterImageMap() {
        return characterImageMap;
    }

    @Override
    public Map<CharacterDto, Button> getCharacterDeleteButtonMap() {
        return characterDeleteButtonMap;
    }

    @Override
    public Long getSelectedNewCharacterID() {
        return Long.valueOf(newCharacterListBox.getValue(newCharacterListBox.getSelectedIndex()));
    }

    @Override
    public void setNewCharacterNames(List<CharacterNameDto> characterNames) {
        newCharacterListBox.clear();
        for (CharacterNameDto characterName : characterNames) {
            newCharacterListBox.addItem(characterName.getName(), String.valueOf(characterName.getId()));
        }
        if (newCharacterListBox.getItemCount() > 0) {
            newCharacterListBox.setSelectedIndex(0);
        }
    }

    @Override
    public ListBox getNewCharacterNamesListBox() {
        return newCharacterListBox;
    }

    @Override
    public Button getAddNewCharacterButton() {
        return addNewCharacterButton;
    }

    @Override
    public void setApiKeys(List<ApiKeyDto> apiKeys) {
        apiKeysFlexTable.removeAllRows();
        apiKeysFlexTable.clear();
        for (ApiKeyDto apiKey : apiKeys) {
            drawApiKey(apiKey);
        }
    }

    @Override
    public Map<ApiKeyDto, Button> getApiKeyDeleteButtonMap() {
        return apiKeyDeleteButtonMap;
    }

    @Override
    public TextBox getNewApiKeyUserIdTextBox() {
        return newApiKeyUserIdTextBox;
    }

    @Override
    public TextBox getNewApiKeyStringTextBox() {
        return newApiKeyStringTextBox;
    }

    @Override
    public Button getAddNewApiKeyButton() {
        return addNewApiKeyButton;
    }

    private void drawCharacter(CharacterDto character) {
        int index = charactersFlexTable.getRowCount();
        Image characterImage = new Image(urlMessages.imgEveCharacter50Url(constants.eveGateImagesUrl(), character.getCharacterID()));
        characterImage.addStyleName(resources.css().image50());
        Image corporationImage;
        if (character.getCorporationID() != null) {
            corporationImage = new Image(urlMessages.imgEveCorporation50Url(constants.eveGateImagesUrl(), character.getCorporationID()));
            corporationImage.setTitle(character.getCorporationName());
            corporationImage.addStyleName(resources.css().image50());
        } else {
            corporationImage = new Image(resources.nokIcon());
        }
        Image allianceImage;
        if (character.getAllianceID() != null) {
            allianceImage = new Image(urlMessages.imgEveAlliance50Url(constants.eveGateImagesUrl(), character.getAllianceID()));
            allianceImage.setTitle(character.getAllianceName());
            allianceImage.addStyleName(resources.css().image50());
        } else {
            allianceImage = new Image(resources.nokIcon());
        }

        charactersFlexTable.setWidget(index, 0, characterImage);
        charactersFlexTable.setWidget(index, 1, new EveCorporationInfoLink(constants, urlMessages, ccpJsMessages, corporationImage, character.getCorporationID()));
        charactersFlexTable.setWidget(index, 2, new EveAllianceInfoLink(constants, urlMessages, ccpJsMessages, allianceImage, character.getAllianceID()));
        HTMLTable.CellFormatter characterFlexTableFormatter = charactersFlexTable.getCellFormatter();
        characterFlexTableFormatter.setHorizontalAlignment(index, 1, HasHorizontalAlignment.ALIGN_CENTER);
        characterFlexTableFormatter.setHorizontalAlignment(index, 2, HasHorizontalAlignment.ALIGN_CENTER);
        VerticalPanel characterInfoPanel = new VerticalPanel();
        EveCharacterInfoLink characterInfoLink = new EveCharacterInfoLink(ccpJsMessages, character.getName(), character.getCharacterID());
        EveCorporationInfoLink corporationInfoLink = new EveCorporationInfoLink(constants, urlMessages, ccpJsMessages, character.getCorporationName(), character.getCorporationID());
        EveAllianceInfoLink allianceInfoLink = new EveAllianceInfoLink(constants, urlMessages, ccpJsMessages, character.getAllianceName(), character.getAllianceID());
        characterInfoPanel.add(characterInfoLink);
        characterInfoPanel.add(corporationInfoLink);
        characterInfoPanel.add(allianceInfoLink);
        charactersFlexTable.setWidget(index, 3, characterInfoPanel);
        FlowPanel corpTitlesPanel = new FlowPanel();
        List<String> corpTitles = character.getCorporationTitles();
        if (corpTitles != null) {
            for (String title : corpTitles) {
                InlineLabel corpTitleLabel = new InlineLabel(title);
                corpTitleLabel.addStyleName(resources.css().corpTitle());
                corpTitlesPanel.add(corpTitleLabel);
            }
        } else if (character.getCorporationID() == null && character.getAllianceID() == null) {
            corpTitlesPanel.add(new Label(messages.noValidApiKeyFound()));
        } else {
            corpTitlesPanel.add(new Label(messages.noCorpTitles()));
        }
        charactersFlexTable.setWidget(index, 4, corpTitlesPanel);
        Button deleteButton = new Button(messages.delete());
        characterDeleteButtonMap.put(character, deleteButton);
        characterImageMap.put(character.getName(), characterImage);
        charactersFlexTable.setWidget(index, 5, deleteButton);
    }

    private void drawApiKey(ApiKeyDto apiKeyDto) {
        int index = apiKeysFlexTable.getRowCount();
        Image apiKeyImage;
        if ("FULL".equals(apiKeyDto.getKeyType())) {
            apiKeyImage = new Image(resources.fullKeyIcon());
            apiKeyImage.setTitle(messages.fullApiKey());
        } else {
            apiKeyImage = new Image(resources.limitedKeyIcon());
            apiKeyImage.setTitle(messages.limitedApiKey());
        }
        apiKeysFlexTable.setWidget(index, 0, apiKeyImage);
        Image isValidIcon = new Image(getIsValidImageResources(apiKeyDto.isValid()));
        isValidIcon.setTitle(messages.lastApiCheckDate() + apiKeyDto.getLastCheckDate());
        apiKeysFlexTable.setWidget(index, 1, isValidIcon);
        Panel characterPanel = new VerticalPanel();
        apiKeysFlexTable.setWidget(index, 2, characterPanel);
        List<ApiKeyCharacterInfoDto> keyCharacterInfos = apiKeyDto.getCharacterInfos();
        for (ApiKeyCharacterInfoDto apiKeyCharacterInfo : keyCharacterInfos) {
            EveCharacterInfoLink characterInfoLink = new EveCharacterInfoLink(ccpJsMessages, apiKeyCharacterInfo.getName(), apiKeyCharacterInfo.getCharacterID());
            EveCorporationInfoLink corporationInfoLink = new EveCorporationInfoLink(constants, urlMessages, ccpJsMessages, apiKeyCharacterInfo.getCorporationName(), apiKeyCharacterInfo.getCorporationID());
            characterPanel.add(new HTML(characterInfoLink + " (" + corporationInfoLink + ")"));
        }
        int column = 3;
        for (ApiKeyCharacterInfoDto apiKeyCharacterInfo : keyCharacterInfos) {
            Image characterImage = new Image(urlMessages.imgEveCharacter50Url(constants.eveGateImagesUrl(), apiKeyCharacterInfo.getCharacterID()));
            characterImage.setTitle(apiKeyCharacterInfo.getName());
            characterImage.addStyleName(resources.css().image50());
            apiKeysFlexTable.setWidget(index, column++, new EveCharacterInfoLink(ccpJsMessages, characterImage, apiKeyCharacterInfo.getCharacterID()));
        }
        Button deleteButton = new Button(messages.delete());
        apiKeyDeleteButtonMap.put(apiKeyDto, deleteButton);
        apiKeysFlexTable.setWidget(index, 6, deleteButton);
    }

    private ImageResource getIsValidImageResources(Boolean isValid) {
        if (Boolean.TRUE.equals(isValid)) {
            return resources.okIcon();
        } else {
            return resources.nokIcon();
        }
    }
}