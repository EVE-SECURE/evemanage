package lv.odylab.evemanage.service.eve;

import lv.odylab.evemanage.application.exception.ApiKeyNotValidException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;

public interface EveApiDataService {

    void populateCharacterData(lv.odylab.evemanage.domain.eve.Character character) throws EveApiException;

    void populateApiKeyData(ApiKey apiKey) throws EveApiException, ApiKeyNotValidException;

    void populateApiKeyData(ApiKey apiKey, String apiKeyString) throws EveApiException, ApiKeyNotValidException;

}
