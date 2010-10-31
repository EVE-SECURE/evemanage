package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.util.List;

public class PreferencesDeleteApiKeyActionRunnerImpl implements PreferencesDeleteApiKeyActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesDeleteApiKeyActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesDeleteApiKeyActionResponse execute(PreferencesDeleteApiKeyAction action) throws Exception {
        Long apiKeyID = action.getApiKeyID();
        clientFacade.deleteApiKey(apiKeyID);
        List<ApiKeyDto> apiKeys = clientFacade.getApiKeys();
        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();

        PreferencesDeleteApiKeyActionResponse response = new PreferencesDeleteApiKeyActionResponse();
        response.setApiKeys(apiKeys);
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        return response;
    }
}