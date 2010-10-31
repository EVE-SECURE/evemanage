package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.util.List;

public class PreferencesAddCharacterActionRunnerImpl implements PreferencesAddCharacterActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesAddCharacterActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesAddCharacterActionResponse execute(PreferencesAddCharacterAction action) throws Exception {
        Long characterID = action.getCharacterID();
        clientFacade.addCharacter(characterID);

        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();
        List<CharacterNameDto> characterNames = clientFacade.getCharacterNames();

        PreferencesAddCharacterActionResponse response = new PreferencesAddCharacterActionResponse();
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        response.setCharacterNames(characterNames);
        return response;
    }
}