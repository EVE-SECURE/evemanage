package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesAddCharacterActionRunner.class)
public class PreferencesAddCharacterAction implements Action<PreferencesAddCharacterActionResponse> {
    private Long characterID;

    public Long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(Long characterID) {
        this.characterID = characterID;
    }
}