package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;

import java.util.List;

public interface EveManagementService {

    List<Character> getCharacters(Key<User> userKey);

    List<CharacterNameDto> getAvailableNewCharacterNames(Key<User> userKey);

    List<CharacterNameDto> getCharacterNames(Key<User> userKey);

    void createCharacter(Long characterID, Key<User> userKey) throws EveApiException;

    void deleteCharacter(Long characterID, Key<User> userKey);

    List<ApiKey> getApiKeys(Key<User> userKey);

    void createApiKey(String apiKeyString, Long apiKeyUserID, Key<User> userKey) throws EveApiException, ApiKeyShouldBeRemovedException;

    void deleteApiKey(Long apiKeyID, Key<User> userKey);

}
