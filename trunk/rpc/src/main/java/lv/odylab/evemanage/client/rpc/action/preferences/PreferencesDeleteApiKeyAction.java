package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesDeleteApiKeyActionRunner.class)
public class PreferencesDeleteApiKeyAction implements Action<PreferencesDeleteApiKeyActionResponse> {
    private Long apiKeyID;

    public Long getApiKeyID() {
        return apiKeyID;
    }

    public void setApiKeyID(Long apiKeyID) {
        this.apiKeyID = apiKeyID;
    }
}