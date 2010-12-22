package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;

public class PreferencesSavePriceFetchConfigurationActionRunnerImpl implements PreferencesSavePriceFetchConfigurationActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesSavePriceFetchConfigurationActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesSavePriceFetchConfigurationActionResponse execute(PreferencesSavePriceFetchConfigurationAction action) throws Exception {
        clientFacade.savePriceFetchConfiguration(action.getPreferredRegionID(), action.getPreferredPriceFetchOption());
        return new PreferencesSavePriceFetchConfigurationActionResponse();
    }
}
