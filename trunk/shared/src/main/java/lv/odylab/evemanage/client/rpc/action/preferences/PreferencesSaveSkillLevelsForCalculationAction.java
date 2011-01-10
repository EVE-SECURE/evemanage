package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;

import java.util.List;

@RunnedBy(PreferencesSaveSkillLevelsForCalculationActionRunner.class)
public class PreferencesSaveSkillLevelsForCalculationAction implements Action<PreferencesSaveSkillLevelsForCalculationActionResponse> {
    private List<SkillLevelDto> skillLevelsForCalculation;

    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }

    public void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        this.skillLevelsForCalculation = skillLevelsForCalculation;
    }
}
