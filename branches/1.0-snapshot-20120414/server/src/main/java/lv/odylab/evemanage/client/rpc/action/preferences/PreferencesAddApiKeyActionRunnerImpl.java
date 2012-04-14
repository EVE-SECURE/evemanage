package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.InvalidApiKeyException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.util.List;

public class PreferencesAddApiKeyActionRunnerImpl implements PreferencesAddApiKeyActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesAddApiKeyActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesAddApiKeyActionResponse execute(PreferencesAddApiKeyAction action) throws Exception {
        Long apiKeyUserID;
        try {
            apiKeyUserID = Long.valueOf(action.getApiKeyUserID());
        } catch (NumberFormatException nfe) {
            throw new InvalidApiKeyException(action.getApiKeyUserID(), ErrorCode.INVALID_API_KEY_ID);
        }
        String apiKeyString = action.getApiKeyString();
        if (apiKeyString.length() != 64) {
            throw new InvalidApiKeyException(action.getApiKeyUserID(), ErrorCode.INVALID_API_KEY_ID);
        }
        clientFacade.createApiKey(apiKeyUserID, apiKeyString);
        List<ApiKeyDto> apiKeys = clientFacade.getApiKeys();
        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();

        PreferencesAddApiKeyActionResponse response = new PreferencesAddApiKeyActionResponse();
        response.setApiKeys(apiKeys);
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        return response;
    }
}
