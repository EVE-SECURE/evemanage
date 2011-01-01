package lv.odylab.evemanage.client.view.tab.quickcalculator;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.presenter.tab.QuickCalculatorTabPresenter;
import lv.odylab.evemanage.client.presenter.tab.quickcalculator.EditableCalculationSkillLevel;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;
import lv.odylab.evemanage.client.widget.EveItemInfoLink;
import lv.odylab.evemanage.client.widget.EveItemMarketDetailsLink;
import lv.odylab.evemanage.client.widget.SkillBookImage;
import lv.odylab.evemanage.client.widget.SkillLevelImage;
import lv.odylab.evemanage.client.widget.SkillLevelListBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillsForCalculationSection implements QuickCalculatorTabPresenter.SkillsForCalculationSectionDisplay {
    private EveManageConstants constants;
    private EveManageResources resources;
    private EveManageMessages messages;
    private EveManageUrlMessages urlMessages;
    private CcpJsMessages ccpJsMessages;
    private EveImageUrlProvider imageUrlProvider;

    private Label skillsSectionLabel;
    private FlexTable skillLevelsTable;

    private Map<Long, EditableCalculationSkillLevel> typeIdToEditableCalculationSkillLevelMap;

    @Inject
    public SkillsForCalculationSection(EveManageConstants constants, EveManageResources resources, EveManageMessages messages, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, EveImageUrlProvider imageUrlProvider) {
        this.constants = constants;
        this.resources = resources;
        this.messages = messages;
        this.urlMessages = urlMessages;
        this.ccpJsMessages = ccpJsMessages;
        this.imageUrlProvider = imageUrlProvider;

        skillsSectionLabel = new Label(messages.skills());
        skillsSectionLabel.addStyleName(resources.css().tabHeadingText());
        skillLevelsTable = new FlexTable();

        typeIdToEditableCalculationSkillLevelMap = new HashMap<Long, EditableCalculationSkillLevel>();
    }

    @Override
    public void attach(HasWidgets container) {
        container.add(skillsSectionLabel);
        container.add(skillLevelsTable);
    }

    @Override
    public void drawSkillsForCalculation(List<SkillLevelDto> skillLevels) {
        for (SkillLevelDto skillLevel : skillLevels) {
            drawSkillLevel(skillLevel);
        }
    }

    @Override
    public void cleanSkillsForCalculation() {
        skillLevelsTable.removeAllRows();
    }

    @Override
    public Map<Long, Integer> getTypeIdToSkillLevelMap() {
        Map<Long, Integer> typeIdToSkillLevelMap = new HashMap<Long, Integer>();
        for (Map.Entry<Long, EditableCalculationSkillLevel> mapEntry : typeIdToEditableCalculationSkillLevelMap.entrySet()) {
            Long typeID = mapEntry.getKey();
            EditableCalculationSkillLevel editableCalculationSkillLevel = mapEntry.getValue();
            typeIdToSkillLevelMap.put(typeID, editableCalculationSkillLevel.getSkillLevelListBox().getSelectedLevel());
        }
        return typeIdToSkillLevelMap;
    }

    @Override
    public Map<Long, EditableCalculationSkillLevel> getTypeIdToEditableCalculationSkillLevelMap() {
        return typeIdToEditableCalculationSkillLevelMap;
    }

    private void drawSkillLevel(SkillLevelDto skillLevel) {
        int index = skillLevelsTable.getRowCount();
        Long typeID = skillLevel.getTypeID();
        Integer level = skillLevel.getLevel();

        SkillBookImage skillBookImage = new SkillBookImage(resources, level);
        skillBookImage.setTitle(skillLevel.getName());
        skillBookImage.addStyleName(resources.css().image32());
        skillLevelsTable.setWidget(index, 0, new EveItemInfoLink(ccpJsMessages, skillBookImage, typeID));
        skillLevelsTable.setWidget(index, 1, new EveItemMarketDetailsLink(constants, urlMessages, ccpJsMessages, skillLevel.getName(), typeID));
        SkillLevelListBox skillLevelListBox = new SkillLevelListBox(level);
        skillLevelsTable.setWidget(index, 2, skillLevelListBox);
        SkillLevelImage skillLevelImage = new SkillLevelImage(resources, level);
        skillLevelsTable.setWidget(index, 3, skillLevelImage);

        EditableCalculationSkillLevel editableCalculationSkillLevel = new EditableCalculationSkillLevel();
        editableCalculationSkillLevel.setSkillLevel(skillLevel);
        editableCalculationSkillLevel.setSkillLevelListBox(skillLevelListBox);
        editableCalculationSkillLevel.setSkillBookImage(skillBookImage);
        editableCalculationSkillLevel.setSkillLevelImage(skillLevelImage);
        typeIdToEditableCalculationSkillLevelMap.put(typeID, editableCalculationSkillLevel);
    }
}
