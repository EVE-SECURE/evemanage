package lv.odylab.evemanage.client.presenter.tab.preferences;

import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.widget.SkillBookImage;
import lv.odylab.evemanage.client.widget.SkillLevelImage;
import lv.odylab.evemanage.client.widget.SkillLevelListBox;

public class EditablePreferenceSkillLevel {
    private SkillLevelDto skillLevel;
    private SkillLevelListBox skillLevelListBox;
    private SkillBookImage skillBookImage;
    private SkillLevelImage skillLevelImage;

    public SkillLevelDto getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevelDto skillLevel) {
        this.skillLevel = skillLevel;
    }

    public SkillLevelListBox getSkillLevelListBox() {
        return skillLevelListBox;
    }

    public void setSkillLevelListBox(SkillLevelListBox skillLevelListBox) {
        this.skillLevelListBox = skillLevelListBox;
    }

    public SkillBookImage getSkillBookImage() {
        return skillBookImage;
    }

    public void setSkillBookImage(SkillBookImage skillBookImage) {
        this.skillBookImage = skillBookImage;
    }

    public SkillLevelImage getSkillLevelImage() {
        return skillLevelImage;
    }

    public void setSkillLevelImage(SkillLevelImage skillLevelImage) {
        this.skillLevelImage = skillLevelImage;
    }
}
