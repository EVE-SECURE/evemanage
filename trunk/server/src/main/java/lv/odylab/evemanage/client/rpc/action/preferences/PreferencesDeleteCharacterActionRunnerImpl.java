package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.util.List;

public class PreferencesDeleteCharacterActionRunnerImpl implements PreferencesDeleteCharacterActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesDeleteCharacterActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesDeleteCharacterActionResponse execute(PreferencesDeleteCharacterAction action) throws Exception {
        clientFacade.deleteCharacter(action.getCharacterID());

        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();
        List<CharacterNameDto> characterNames = clientFacade.getCharacterNames();

        PreferencesDeleteCharacterActionResponse response = new PreferencesDeleteCharacterActionResponse();
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        response.setCharacterNames(characterNames);
        return response;
    }
}