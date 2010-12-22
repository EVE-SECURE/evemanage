package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;

import java.util.List;

public class PreferencesFetchCalculationSkillLevelsActionRunnerImpl implements PreferencesFetchCalculationSkillLevelsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesFetchCalculationSkillLevelsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesFetchCalculationSkillLevelsActionResponse execute(PreferencesFetchCalculationSkillLevelsAction action) throws Exception {
        List<SkillLevelDto> skillLevels = clientFacade.fetchCalculationSkillLevelsForMainCharacter();

        PreferencesFetchCalculationSkillLevelsActionResponse response = new PreferencesFetchCalculationSkillLevelsActionResponse();
        response.setSkillLevelsForCalculation(skillLevels);
        return response;
    }
}
