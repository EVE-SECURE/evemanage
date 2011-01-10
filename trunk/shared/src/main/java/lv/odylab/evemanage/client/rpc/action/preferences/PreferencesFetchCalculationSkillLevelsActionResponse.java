package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;

import java.util.List;

public class PreferencesFetchCalculationSkillLevelsActionResponse implements Response {
    private List<SkillLevelDto> skillLevelsForCalculation;

    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }

    public void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        this.skillLevelsForCalculation = skillLevelsForCalculation;
    }
}
