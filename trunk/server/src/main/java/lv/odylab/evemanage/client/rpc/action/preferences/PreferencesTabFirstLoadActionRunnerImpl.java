package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.util.List;

public class PreferencesTabFirstLoadActionRunnerImpl implements PreferencesTabFirstLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesTabFirstLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesTabFirstLoadActionResponse execute(PreferencesTabFirstLoadAction action) throws Exception {
        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<ApiKeyDto> apiKeys = clientFacade.getApiKeys();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();

        PreferencesTabFirstLoadActionResponse response = new PreferencesTabFirstLoadActionResponse();
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        response.setApiKeys(apiKeys);
        return response;
    }
}