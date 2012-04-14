package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.util.List;

public class PreferencesTabFirstLoadActionResponse implements Response {
    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<ApiKeyDto> apiKeys;

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDto> characters) {
        this.characters = characters;
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(CharacterDto mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public List<CharacterNameDto> getNewCharacterNames() {
        return newCharacterNames;
    }

    public void setNewCharacterNames(List<CharacterNameDto> newCharacterNames) {
        this.newCharacterNames = newCharacterNames;
    }

    public List<ApiKeyDto> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(List<ApiKeyDto> apiKeys) {
        this.apiKeys = apiKeys;
    }
}