package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PreferencesSetMainCharacterActionRunner.class)
public class PreferencesSetMainCharacterAction implements Action<PreferencesSetMainCharacterActionResponse> {
    private String characterName;

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}