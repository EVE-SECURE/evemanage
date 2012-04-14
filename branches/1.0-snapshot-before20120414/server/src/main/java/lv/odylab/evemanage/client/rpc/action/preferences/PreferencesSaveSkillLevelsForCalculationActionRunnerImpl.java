package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;

public class PreferencesSaveSkillLevelsForCalculationActionRunnerImpl implements PreferencesSaveSkillLevelsForCalculationActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesSaveSkillLevelsForCalculationActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesSaveSkillLevelsForCalculationActionResponse execute(PreferencesSaveSkillLevelsForCalculationAction action) throws Exception {
        clientFacade.saveSkillLevelsForCalculation(action.getSkillLevelsForCalculation());

        return new PreferencesSaveSkillLevelsForCalculationActionResponse();
    }
}
