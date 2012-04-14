package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesAddApiKeyActionRunner.class)
public class PreferencesAddApiKeyAction implements Action<PreferencesAddApiKeyActionResponse> {
    private String apiKeyUserID;
    private String apiKeyString;

    public String getApiKeyUserID() {
        return apiKeyUserID;
    }

    public void setApiKeyUserID(String apiKeyUserID) {
        this.apiKeyUserID = apiKeyUserID;
    }

    public String getApiKeyString() {
        return apiKeyString;
    }

    public void setApiKeyString(String apiKeyString) {
        this.apiKeyString = apiKeyString;
    }
}
