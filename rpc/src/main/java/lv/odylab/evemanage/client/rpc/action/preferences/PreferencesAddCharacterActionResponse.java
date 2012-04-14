package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.util.List;

public class PreferencesAddCharacterActionResponse implements Response {
    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<CharacterNameDto> characterNames;

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

    public List<CharacterNameDto> getCharacterNames() {
        return characterNames;
    }

    public void setCharacterNames(List<CharacterNameDto> characterNames) {
        this.characterNames = characterNames;
    }
}