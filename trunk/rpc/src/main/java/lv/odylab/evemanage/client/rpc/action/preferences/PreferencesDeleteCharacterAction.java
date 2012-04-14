package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesDeleteCharacterActionRunner.class)
public class PreferencesDeleteCharacterAction implements Action<PreferencesDeleteCharacterActionResponse> {
    private Long characterID;

    public Long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(Long characterID) {
        this.characterID = characterID;
    }
}