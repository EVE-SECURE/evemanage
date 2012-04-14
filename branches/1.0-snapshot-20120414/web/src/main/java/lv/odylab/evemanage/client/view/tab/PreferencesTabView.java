package lv.odylab.evemanage.client.view.tab;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferenceSkillLevel;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferencesApiKey;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferencesCharacter;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.view.tab.preferences.ApiKeysSection;
import lv.odylab.evemanage.client.view.tab.preferences.CharactersSection;
import lv.odylab.evemanage.client.view.tab.preferences.PriceFetchingSection;
import lv.odylab.evemanage.client.view.tab.preferences.SkillsForCalculationSection;
import lv.odylab.evemanage.client.widget.PriceFetchOptionListBox;
import lv.odylab.evemanage.client.widget.RegionListBox;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.util.List;
import java.util.Map;

public class PreferencesTabView implements PreferencesTabPresenter.Display {
    private HorizontalPanel headerPanel;
    private Label headerLabel;
    private Image spinnerImage;

    private FlexTable errorMessageTable;
    private Label errorMessageLabel;
    private Image errorImage;

    private Label hintOnlyForSharingLabel;

    private CharactersSection charactersSection;
    private ApiKeysSection apiKeysSection;
    private SkillsForCalculationSection skillsForCalculationSection;
    private PriceFetchingSection priceFetchingSection;

    @Inject
    public PreferencesTabView(EveManageResources resources, EveManageMessages messages, CharactersSection charactersSection, ApiKeysSection apiKeysSection, SkillsForCalculationSection skillsForCalculationSection, PriceFetchingSection priceFetchingSection) {
        this.charactersSection = charactersSection;
        this.apiKeysSection = apiKeysSection;
        this.skillsForCalculationSection = skillsForCalculationSection;
        this.priceFetchingSection = priceFetchingSection;

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

        hintOnlyForSharingLabel = new Label(messages.hintOnlyForSharing() + ".");
        hintOnlyForSharingLabel.addStyleName(resources.css().hintLabel());
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

        charactersSection.attach(container);
        apiKeysSection.attach(container);
        skillsForCalculationSection.attach(container);
        priceFetchingSection.attach(container);
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
        charactersSection.setCharacters(characters);
    }

    @Override
    public void setMainCharacter(CharacterDto mainCharacter) {
        charactersSection.setMainCharacter(mainCharacter);
    }

    @Override
    public Map<CharacterDto, EditablePreferencesCharacter> getCharacterToEditablePreferencesCharacterMap() {
        return charactersSection.getCharacterToEditablePreferencesCharacterMap();
    }

    @Override
    public Long getSelectedNewCharacterID() {
        return charactersSection.getSelectedNewCharacterID();
    }

    @Override
    public void setNewCharacterNames(List<CharacterNameDto> characterNames) {
        charactersSection.setNewCharacterNames(characterNames);
    }

    @Override
    public ListBox getNewCharacterNamesListBox() {
        return charactersSection.getNewCharacterNamesListBox();
    }

    @Override
    public Button getAddNewCharacterButton() {
        return charactersSection.getAddNewCharacterButton();
    }

    @Override
    public void setApiKeys(List<ApiKeyDto> apiKeys) {
        apiKeysSection.setApiKeys(apiKeys);
    }

    @Override
    public Map<ApiKeyDto, EditablePreferencesApiKey> getApiKeyToEditablePreferencesApiKeyMap() {
        return apiKeysSection.getApiKeyToEditablePreferencesApiKeyMap();
    }

    @Override
    public TextBox getNewApiKeyUserIdTextBox() {
        return apiKeysSection.getNewApiKeyUserIdTextBox();
    }

    @Override
    public TextBox getNewApiKeyStringTextBox() {
        return apiKeysSection.getNewApiKeyStringTextBox();
    }

    @Override
    public Button getAddNewApiKeyButton() {
        return apiKeysSection.getAddNewApiKeyButton();
    }

    @Override
    public Widget getSkillsForCalculationSectionSpinnerImage() {
        return skillsForCalculationSection.getSkillsForCalculationSectionSpinnerImage();
    }

    @Override
    public void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        skillsForCalculationSection.setSkillLevelsForCalculation(skillLevelsForCalculation);
    }

    @Override
    public void updateSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        skillsForCalculationSection.updateSkillLevelsForCalculation(skillLevelsForCalculation);
    }

    @Override
    public List<SkillLevelDto> getSkillLevelsForCalculationThatNotEqualTo5() {
        return skillsForCalculationSection.getSkillLevelsForCalculationThatNotEqualTo5();
    }

    @Override
    public Button getSaveSkillLevelsButton() {
        return skillsForCalculationSection.getSaveSkillLevelsButton();
    }

    @Override
    public Button getFetchSkillLevelsForCurrentCharacterButton() {
        return skillsForCalculationSection.getFetchSkillLevelsForCurrentCharacterButton();
    }

    @Override
    public Map<Long, EditablePreferenceSkillLevel> getTypeIdToEditablePreferenceSkillLevelMap() {
        return skillsForCalculationSection.getTypeIdToEditablePreferenceSkillLevelMap();
    }

    @Override
    public Widget getPriceFetchingSectionSpinnerImage() {
        return priceFetchingSection.getPriceFetchingSectionSpinnerImage();
    }

    @Override
    public void setRegions(List<RegionDto> regions) {
        priceFetchingSection.setRegions(regions);
    }

    @Override
    public void setPreferredRegion(RegionDto preferredRegion) {
        priceFetchingSection.setPreferredRegion(preferredRegion);
    }

    @Override
    public RegionListBox getPreferredRegionListBox() {
        return priceFetchingSection.getPreferredRegionListBox();
    }

    @Override
    public void setPriceFetchOptions(List<PriceFetchOption> priceFetchOptions) {
        priceFetchingSection.setPriceFetchOptions(priceFetchOptions);
    }

    @Override
    public void setPreferredPriceFetchOption(PriceFetchOption preferredPriceFetchOption) {
        priceFetchingSection.setPreferredPriceFetchOption(preferredPriceFetchOption);
    }

    @Override
    public PriceFetchOptionListBox getPreferredPriceFetchOption() {
        return priceFetchingSection.getPreferredPriceFetchOption();
    }

    @Override
    public Button getSavePriceFetchConfigurationButton() {
        return priceFetchingSection.getSavePriceFetchConfigurationButton();
    }
}