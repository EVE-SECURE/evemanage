package lv.odylab.evemanage.client.view.tab.preferences;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferencesApiKey;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyCharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;
import lv.odylab.evemanage.client.widget.EveCorporationInfoLink;
import lv.odylab.evemanage.client.widget.ValidOrInvalidImage;
import lv.odylab.evemanage.shared.eve.ApiKeyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiKeysSection implements PreferencesTabPresenter.ApiKeysSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

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
    private DateTimeFormat dateTimeFormat;
    private Map<ApiKeyDto, EditablePreferencesApiKey> apiKeyToEditablePreferencesApiKeyMap;

    @Inject
    public ApiKeysSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

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

        dateTimeFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_FULL);

        apiKeyToEditablePreferencesApiKeyMap = new HashMap<ApiKeyDto, EditablePreferencesApiKey>();
    }

    @Override
    public void attach(HasWidgets container) {
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
    public void setApiKeys(List<ApiKeyDto> apiKeys) {
        apiKeysFlexTable.removeAllRows();
        for (ApiKeyDto apiKey : apiKeys) {
            drawApiKey(apiKey);
        }
    }

    @Override
    public Map<ApiKeyDto, EditablePreferencesApiKey> getApiKeyToEditablePreferencesApiKeyMap() {
        return apiKeyToEditablePreferencesApiKeyMap;
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

    private void drawApiKey(ApiKeyDto apiKeyDto) {
        int index = apiKeysFlexTable.getRowCount();
        Image apiKeyImage;
        if (ApiKeyType.FULL.equals(apiKeyDto.getKeyType())) {
            apiKeyImage = new Image(resources.fullKeyIcon());
            apiKeyImage.setTitle(messages.fullApiKey());
        } else {
            apiKeyImage = new Image(resources.limitedKeyIcon());
            apiKeyImage.setTitle(messages.limitedApiKey());
        }
        apiKeysFlexTable.setWidget(index, 0, apiKeyImage);
        Image isValidIcon = new ValidOrInvalidImage(resources, apiKeyDto.isValid());
        isValidIcon.setTitle(messages.lastApiCheckDate() + ": " + dateTimeFormat.format(apiKeyDto.getLastCheckDate()));
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
        apiKeysFlexTable.setWidget(index, 6, deleteButton);

        EditablePreferencesApiKey editablePreferencesApiKey = new EditablePreferencesApiKey();
        editablePreferencesApiKey.setDeleteButton(deleteButton);
        apiKeyToEditablePreferencesApiKeyMap.put(apiKeyDto, editablePreferencesApiKey);
    }
}
