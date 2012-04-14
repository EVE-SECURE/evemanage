package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesSavePriceFetchConfigurationActionRunner.class)
public class PreferencesSavePriceFetchConfigurationAction implements Action<PreferencesSavePriceFetchConfigurationActionResponse> {
    private Long preferredRegionID;
    private String preferredPriceFetchOption;

    public Long getPreferredRegionID() {
        return preferredRegionID;
    }

    public void setPreferredRegionID(Long preferredRegionID) {
        this.preferredRegionID = preferredRegionID;
    }

    public String getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    public void setPreferredPriceFetchOption(String preferredPriceFetchOption) {
        this.preferredPriceFetchOption = preferredPriceFetchOption;
    }
}
