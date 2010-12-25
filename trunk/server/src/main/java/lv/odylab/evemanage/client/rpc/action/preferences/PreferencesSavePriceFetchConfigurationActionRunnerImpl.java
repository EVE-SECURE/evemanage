package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.domain.eve.Region;

public class PreferencesSavePriceFetchConfigurationActionRunnerImpl implements PreferencesSavePriceFetchConfigurationActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesSavePriceFetchConfigurationActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesSavePriceFetchConfigurationActionResponse execute(PreferencesSavePriceFetchConfigurationAction action) throws Exception {
        Long preferredRegionID = action.getPreferredRegionID();
        String preferredPriceFetchOption = action.getPreferredPriceFetchOption();
        clientFacade.savePriceFetchConfiguration(Region.getByRegionID(preferredRegionID), preferredPriceFetchOption);
        return new PreferencesSavePriceFetchConfigurationActionResponse();
    }
}
