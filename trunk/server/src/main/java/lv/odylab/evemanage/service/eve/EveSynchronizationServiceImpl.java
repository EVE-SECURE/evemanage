package lv.odylab.evemanage.service.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EveSynchronizationServiceImpl implements EveSynchronizationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CharacterSynchronizationService characterSynchronizationService;
    private final EveApiDataService eveApiDataService;
    private final ApiKeyDao apiKeyDao;
    private final CharacterDao characterDao;

    @Inject
    public EveSynchronizationServiceImpl(CharacterSynchronizationService characterSynchronizationService, EveApiDataService eveApiDataService, ApiKeyDao apiKeyDao, CharacterDao characterDao) {
        this.characterSynchronizationService = characterSynchronizationService;
        this.eveApiDataService = eveApiDataService;
        this.apiKeyDao = apiKeyDao;
        this.characterDao = characterDao;
    }

    @Override
    public void synchronizeCreateCharacter(Character character, Key<User> userKey) {
        logger.debug("Synchronizing on new character creation: {} ({})", character.getName(), character.getCharacterID());
        characterSynchronizationService.synchronizeCreateCharacter(character, userKey);
    }

    @Override
    public void synchronizeDeleteCharacter(Long characterID, Key<User> userKey) {
        logger.debug("Synchronizing on character deletion: ()", characterID);
        characterSynchronizationService.synchronizeDeleteCharacter(characterID, userKey);
    }

    @Override
    public void synchronizeCreateApiKey(ApiKey apiKey, Key<User> userKey) throws EveApiException {
        logger.debug("Synchronizing on api key creation: {}", apiKey.getId());
        logger.debug("Getting all detached characters with apiKey=null, some of them might get linked to new api key");
        List<Character> characters = characterDao.getAllWithoutApiKey(userKey);
        List<Character> updatedCharacters = new ArrayList<Character>();
        logger.debug("Processing new api key character infos and looking for detached characters that is present in new api key");
        for (ApiKeyCharacterInfo apiKeyCharacterInfo : apiKey.getCharacterInfos()) {
            Character character = findCharacterInApiKeyCharacterInfos(characters, apiKeyCharacterInfo);
            if (character != null) {
                logger.debug("Found character than can be linked to new api key: {} ({})", apiKeyCharacterInfo.getName(), apiKeyCharacterInfo.getCharacterID());
                if (character.getApiKey() == null) {
                    character.setApiKey(new Key<ApiKey>(ApiKey.class, apiKey.getId()));
                    eveApiDataService.populateCharacterData(character);
                    characterDao.put(character, userKey);
                    updatedCharacters.add(character);
                } else {
                    logger.debug("Character has api key, so there is no need for relinking");
                }
            } else {
                logger.debug("Character was not found: {} ({}), leaving as is", apiKeyCharacterInfo.getName(), apiKeyCharacterInfo.getCharacterID());
            }
        }

        characterSynchronizationService.synchronizeUpdateCharacters(updatedCharacters, userKey);
    }

    @Override
    public void synchronizeDeleteApiKey(Key<ApiKey> apiKeyKey, Key<User> userKey) {
        logger.debug("Synchronizing on api key deletion: {}", apiKeyKey.getId());
        List<ApiKey> otherApiKeys = apiKeyDao.getAll(userKey);
        Map<Long, Key<ApiKey>> characterIdToApiKeyMap = new HashMap<Long, Key<ApiKey>>();
        logger.debug("Compiling map of available characters left on other api keys");
        for (ApiKey apiKey : otherApiKeys) {
            for (ApiKeyCharacterInfo apiKeyCharacterInfo : apiKey.getCharacterInfos()) {
                logger.debug("Character: {} ({}), is available on api key: {}", new Object[]{apiKeyCharacterInfo.getName(), apiKeyCharacterInfo.getCharacterID(), apiKey.getId()});
                characterIdToApiKeyMap.put(apiKeyCharacterInfo.getCharacterID(), new Key<ApiKey>(ApiKey.class, apiKey.getId()));
            }
        }

        logger.debug("Detaching characters with old api key or relinking them to other api keys that contain corresponding characters");
        List<Character> characters = characterDao.getAll(apiKeyKey, userKey);
        List<Character> detachedCharacters = new ArrayList<Character>();
        for (Character character : characters) {
            if (!characterIdToApiKeyMap.containsKey(character.getCharacterID())) {
                logger.debug("Detaching character: {} ({})", character.getName(), character.getCharacterID());
                detachCharacter(character);
                detachedCharacters.add(character);
            } else {
                Key<ApiKey> newApiKeyKey = characterIdToApiKeyMap.get(character.getCharacterID());
                character.setApiKey(newApiKeyKey);
                logger.debug("Character: {} ({}), is available on api key {}, linking to new api key", new Object[]{character.getName(), character.getCharacterID(), newApiKeyKey.getId()});
            }
            characterDao.putWithoutChecks(character);
        }

        characterSynchronizationService.synchronizeDetachCharacters(detachedCharacters, userKey);
    }

    @Override
    public void synchronizeUpdateApiKeys(List<ApiKey> apiKeys, Key<User> userKey) throws EveApiException {
        logger.info("Synchronizing on api keys update for userID: {}", userKey.getId());
        Map<Long, ApiKey> characterIdToApiKeyMap = new HashMap<Long, ApiKey>();
        for (ApiKey apiKey : apiKeys) {
            for (ApiKeyCharacterInfo apiKeyCharacterInfo : apiKey.getCharacterInfos()) {
                characterIdToApiKeyMap.put(apiKeyCharacterInfo.getCharacterID(), apiKey);
            }
        }

        List<Character> characters = characterDao.getAll(userKey);
        List<Character> detachedCharacters = new ArrayList<Character>();
        List<Character> updatedCharacters = new ArrayList<Character>();
        for (Character character : characters) {
            logger.info("Updating character: {} ({})", character.getName(), character.getCharacterID());
            String previousHash = createCharacterHash(character);
            ApiKey apiKey = characterIdToApiKeyMap.get(character.getCharacterID());
            if (apiKey != null) {
                logger.debug("Character does not have valid API key, will be detached");
                character.setApiKey(new Key<ApiKey>(ApiKey.class, apiKey.getId()));
                eveApiDataService.populateCharacterData(character);
                if (!createCharacterHash(character).equals(previousHash)) {
                    updatedCharacters.add(character);
                }
            } else {
                detachCharacter(character);
                detachedCharacters.add(character);
            }
            characterDao.putWithoutChecks(character);
        }

        characterSynchronizationService.synchronizeDetachCharacters(detachedCharacters, userKey);
        characterSynchronizationService.synchronizeUpdateCharacters(updatedCharacters, userKey);
    }

    private void detachCharacter(Character character) {
        character.setApiKey(null);
        character.setCorporationID(null);
        character.setCorporationName(null);
        character.setCorporationTicker(null);
        character.setCorporationTitles(null);
        character.setAllianceID(null);
        character.setAllianceName(null);
        character.setUpdatedDate(new Date());
    }

    private String createCharacterHash(Character character) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(character.getCharacterID());
        stringBuilder.append(character.getName());
        stringBuilder.append(character.getCorporationID());
        stringBuilder.append(character.getCorporationName());
        stringBuilder.append(character.getAllianceID());
        stringBuilder.append(character.getAllianceName());
        return stringBuilder.toString();
    }

    private Character findCharacterInApiKeyCharacterInfos(List<Character> characters, ApiKeyCharacterInfo apiKeyCharacterInfo) {
        for (Character character : characters) {
            if (character.getCharacterID().equals(apiKeyCharacterInfo.getCharacterID())) {
                return character;
            }
        }
        return null;
    }
}
