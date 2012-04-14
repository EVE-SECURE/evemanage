package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;

import java.util.List;

public interface EveSynchronizationService {

    void synchronizeCreateCharacter(Character character, Key<User> userKey);

    void synchronizeDeleteCharacter(Long characterID, Key<User> userKey);

    void synchronizeCreateApiKey(ApiKey apiKey, Key<User> userKey) throws EveApiException;

    void synchronizeDeleteApiKey(Key<ApiKey> apiKeyKey, Key<User> userKey);

    void synchronizeUpdateApiKeys(List<ApiKey> apiKeys, Key<User> userKey) throws EveApiException;

}
