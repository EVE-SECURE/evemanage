package lv.odylab.evemanage.service.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyNotValidException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class EveUpdateServiceImpl implements EveUpdateService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveApiDataService eveApiDataService;
    private final EveSynchronizationService eveSynchronizationService;
    private final ApiKeyDao apiKeyDao;
    private final CharacterDao characterDao;

    @Inject
    public EveUpdateServiceImpl(EveApiDataService eveApiDataService, EveSynchronizationService eveSynchronizationService, ApiKeyDao apiKeyDao, CharacterDao characterDao) {
        this.eveApiDataService = eveApiDataService;
        this.eveSynchronizationService = eveSynchronizationService;
        this.apiKeyDao = apiKeyDao;
        this.characterDao = characterDao;
    }

    @Override
    public void updateApiKeysForUser(Key<User> userKey) throws EveApiException {
        logger.info("Starting api keys update for userID: {}", userKey.getId());
        List<ApiKey> apiKeys = apiKeyDao.getAll(userKey);
        for (ApiKey apiKey : apiKeys) {
            updateApiKey(apiKey);
        }

        List<Character> characters = characterDao.getAll(userKey);
        for (Character character : characters) {
            updateCharacter(character);
        }

        eveSynchronizationService.synchronizeUpdateCharacters(characters, userKey);
    }

    private void updateApiKey(ApiKey apiKey) throws EveApiException {
        logger.info("Updating api key with id: {}", apiKey.getId());
        try {
            eveApiDataService.populateApiKeyData(apiKey);
        } catch (ApiKeyNotValidException e) {
            logger.info("Api key is not valid anymore, going to detach characters from it and set as invalid. Api returned error: {}", e.getMessage());
            apiKey.setCharacterInfos(null);
            apiKey.setUpdatedDate(new Date());
            apiKey.setLastCheckDate(new Date());
            apiKey.setValid(Boolean.FALSE);
        }
        apiKeyDao.putWithoutChecks(apiKey);
    }

    private void updateCharacter(Character character) {
        logger.info("Updating character: {} ({})", character.getName(), character.getCharacterID());
        try {
            eveApiDataService.populateCharacterData(character);
        } catch (EveApiException e) {
            logger.info("Caugh EveApiException during update, going to detach character", e);
            character.setApiKey(null);
            character.setCorporationID(null);
            character.setCorporationName(null);
            character.setCorporationTicker(null);
            character.setCorporationTitles(null);
            character.setAllianceID(null);
            character.setAllianceName(null);
            character.setUpdatedDate(new Date());
        }
        characterDao.putWithoutChecks(character);
    }
}
