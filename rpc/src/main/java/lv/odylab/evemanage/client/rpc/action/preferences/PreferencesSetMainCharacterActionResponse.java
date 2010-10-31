package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;

public class PreferencesSetMainCharacterActionResponse implements Response {
    private CharacterDto mainCharacter;

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(CharacterDto mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
}