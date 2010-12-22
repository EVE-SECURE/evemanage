package lv.odylab.evemanage.client.view.tab.preferences;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.PreferencesTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.preferences.EditablePreferenceSkillLevel;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.SkillBookImage;
import lv.odylab.evemanage.client.widget.SkillLevelImage;
import lv.odylab.evemanage.client.widget.SkillLevelListBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillsForCalculationSection implements PreferencesTabPresenter.SkillsForCalculationSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;

    private Image spinnerImage;

    private Label skillsSectionLabel;
    private FlexTable skillLevelsFlexTable;

    private FlexTable saveAndFetchFlexTable;
    private Button saveSkillLevelsButton;
    private Button fetchSkillLevelsForCurrentCharacterButton;

    private Map<Long, EditablePreferenceSkillLevel> typeIdToEditablePreferenceSkillLevelMap;

    @Inject
    public SkillsForCalculationSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;

        spinnerImage = new Image(resources.spinnerIcon());
        spinnerImage.setTitle(messages.loading());
        spinnerImage.setVisible(false);

        skillsSectionLabel = new Label(messages.skillsForCalculation());
        skillsSectionLabel.addStyleName(resources.css().tabHeadingText());
        skillLevelsFlexTable = new FlexTable();

        saveSkillLevelsButton = new Button(messages.save());
        saveSkillLevelsButton.setEnabled(false);
        fetchSkillLevelsForCurrentCharacterButton = new Button(messages.fetchForMainCharacter());
        fetchSkillLevelsForCurrentCharacterButton.setEnabled(false);
        saveAndFetchFlexTable = new FlexTable();

        typeIdToEditablePreferenceSkillLevelMap = new HashMap<Long, EditablePreferenceSkillLevel>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(skillsSectionLabel);
        container.add(skillLevelsFlexTable);

        saveAndFetchFlexTable.setWidget(0, 0, saveSkillLevelsButton);
        saveAndFetchFlexTable.setWidget(0, 1, fetchSkillLevelsForCurrentCharacterButton);
        saveAndFetchFlexTable.setWidget(0, 2, spinnerImage);
        container.add(saveAndFetchFlexTable);
    }

    @Override
    public Widget getSkillsForCalculationSectionSpinnerImage() {
        return spinnerImage;
    }

    @Override
    public void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        skillLevelsFlexTable.removeAllRows();
        for (SkillLevelDto skillLevel : skillLevelsForCalculation) {
            drawSkillLevel(skillLevel);
        }
    }

    @Override
    public void updateSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        for (SkillLevelDto skillLevel : skillLevelsForCalculation) {
            EditablePreferenceSkillLevel editablePreferenceSkillLevel = typeIdToEditablePreferenceSkillLevelMap.get(skillLevel.getTypeID());
            SkillLevelListBox skillLevelListBox = editablePreferenceSkillLevel.getSkillLevelListBox();
            SkillBookImage skillBookImage = editablePreferenceSkillLevel.getSkillBookImage();
            SkillLevelImage skillLevelImage = editablePreferenceSkillLevel.getSkillLevelImage();
            Integer level = skillLevel.getLevel();
            skillLevelListBox.setLevel(level);
            skillBookImage.setLevel(level);
            skillLevelImage.setLevel(level);
        }
    }

    @Override
    public List<SkillLevelDto> getSkillLevelsForCalculationThatNotEqualTo5() {
        List<SkillLevelDto> skillLevelThatNotEqualTo5 = new ArrayList<SkillLevelDto>();
        for (Map.Entry<Long, EditablePreferenceSkillLevel> mapEntry : typeIdToEditablePreferenceSkillLevelMap.entrySet()) {
            EditablePreferenceSkillLevel editablePreferenceSkillLevel = mapEntry.getValue();
            SkillLevelDto skillLevel = editablePreferenceSkillLevel.getSkillLevel();
            SkillLevelListBox skillLevelListBox = editablePreferenceSkillLevel.getSkillLevelListBox();
            Integer selectedValue = skillLevelListBox.getSelectedLevel();
            if (selectedValue != 5) {
                skillLevel.setLevel(Integer.valueOf(selectedValue));
                skillLevelThatNotEqualTo5.add(skillLevel);
            }
        }
        return skillLevelThatNotEqualTo5;
    }

    @Override
    public Button getSaveSkillLevelsButton() {
        return saveSkillLevelsButton;
    }

    @Override
    public Button getFetchSkillLevelsForCurrentCharacterButton() {
        return fetchSkillLevelsForCurrentCharacterButton;
    }

    @Override
    public Map<Long, EditablePreferenceSkillLevel> getTypeIdToEditablePreferenceSkillLevelMap() {
        return typeIdToEditablePreferenceSkillLevelMap;
    }

    private void drawSkillLevel(SkillLevelDto skillLevel) {
        int index = skillLevelsFlexTable.getRowCount();
        Long typeID = skillLevel.getTypeID();
        Integer level = skillLevel.getLevel();

        SkillBookImage skillBookImage = new SkillBookImage(resources, level);
        skillBookImage.setTitle(skillLevel.getName());
        skillBookImage.addStyleName(resources.css().image32());
        skillLevelsFlexTable.setWidget(index, 0, new EveItemInfoLink(ccpJsMessages, skillBookImage, typeID));
        skillLevelsFlexTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, skillLevel.getName(), typeID));
        SkillLevelListBox skillLevelListBox = new SkillLevelListBox(level);
        skillLevelsFlexTable.setWidget(index, 2, skillLevelListBox);
        SkillLevelImage skillLevelImage = new SkillLevelImage(resources, level);
        skillLevelsFlexTable.setWidget(index, 3, skillLevelImage);

        EditablePreferenceSkillLevel editablePreferenceSkillLevel = new EditablePreferenceSkillLevel();
        editablePreferenceSkillLevel.setSkillLevel(skillLevel);
        editablePreferenceSkillLevel.setSkillLevelListBox(skillLevelListBox);
        editablePreferenceSkillLevel.setSkillBookImage(skillBookImage);
        editablePreferenceSkillLevel.setSkillLevelImage(skillLevelImage);
        typeIdToEditablePreferenceSkillLevelMap.put(typeID, editablePreferenceSkillLevel);
    }
}
