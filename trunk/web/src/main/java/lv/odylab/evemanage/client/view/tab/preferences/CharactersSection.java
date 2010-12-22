package lv.odylab.evemanage.client.view.tab.preferences;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.widget.EveAllianceInfoLink;
import lv.odylab.evemanage.client.widget.EveCharacterInfoLink;
import lv.odylab.evemanage.client.widget.EveCorporationInfoLink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharactersSection implements PreferencesTabPresenter.CharactersSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

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

    @Inject
    public CharactersSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

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

        characterDeleteButtonMap = new HashMap<CharacterDto, Button>();
        characterImageMap = new HashMap<String, Image>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(charactersHeadingLabel);
        charactersFlexTable = new FlexTable();
        container.add(charactersFlexTable);

        newCharacterFlexTable.setWidget(0, 0, addCharacterLabel);
        newCharacterFlexTable.setWidget(0, 1, newCharacterListBox);
        newCharacterFlexTable.setWidget(0, 2, addNewCharacterButton);
        container.add(newCharacterFlexTable);
        container.add(hintCharactersAreNeededLabel);
        container.add(hintSameSharingLevelLabel);
    }

    @Override
    public void setCharacters(List<CharacterDto> characters) {
        characterImageMap.clear();
        charactersFlexTable.removeAllRows();
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
}
