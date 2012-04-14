package lv.odylab.evemanage.service.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.eveapi.dto.SkillLevelDto;
import lv.odylab.evemanage.shared.eve.SkillForCalculation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EveManagementServiceImpl implements EveManagementService {
    private final EveApiDataService eveApiDataService;
    private final EveSynchronizationService eveSynchronizationService;
    private final CharacterDao characterDao;
    private final ApiKeyDao apiKeyDao;

    @Inject
    public EveManagementServiceImpl(EveApiDataService eveApiDataService, EveSynchronizationService eveSynchronizationService, CharacterDao characterDao, ApiKeyDao apiKeyDao) {
        this.eveApiDataService = eveApiDataService;
        this.eveSynchronizationService = eveSynchronizationService;
        this.characterDao = characterDao;
        this.apiKeyDao = apiKeyDao;
    }

    @Override
    public List<Character> getCharacters(Key<User> userKey) {
        return characterDao.getAll(userKey);
    }

    @Override
    public List<CharacterNameDto> getAvailableNewCharacterNames(Key<User> userKey) {
        List<Character> characters = characterDao.getAll(userKey);
        List<ApiKey> apiKeys = apiKeyDao.getAll(userKey);

        List<String> usedCharacterNames = new ArrayList<String>();
        for (Character character : characters) {
            usedCharacterNames.add(character.getName());
        }

        List<CharacterNameDto> availableNewCharacterNames = new ArrayList<CharacterNameDto>();
        for (ApiKey apiKey : apiKeys) {
            for (ApiKeyCharacterInfo apiKeyCharacterInfo : apiKey.getCharacterInfos()) {
                if (!usedCharacterNames.contains(apiKeyCharacterInfo.getName())) {
                    CharacterNameDto characterName = new CharacterNameDto();
                    characterName.setId(apiKeyCharacterInfo.getCharacterID());
                    characterName.setName(apiKeyCharacterInfo.getName());
                    availableNewCharacterNames.add(characterName);
                }
            }
        }
        return availableNewCharacterNames;
    }

    @Override
    public List<CharacterNameDto> getCharacterNames(Key<User> userKey) {
        List<Character> characters = characterDao.getAll(userKey);
        List<CharacterNameDto> characterNames = new ArrayList<CharacterNameDto>();
        for (Character character : characters) {
            CharacterNameDto characterName = new CharacterNameDto();
            characterName.setId(character.getCharacterID());
            characterName.setName(character.getName());
            characterNames.add(characterName);
        }
        return characterNames;
    }

    @Override
    public void createCharacter(Long characterID, Key<User> userKey) throws EveApiException {
        Key<ApiKey> apiKeyKey = apiKeyDao.getKeyWithCharacterID(characterID, userKey);
        Character character = new Character();
        character.setApiKey(apiKeyKey);
        character.setUser(userKey);
        character.setCharacterID(characterID);
        character.setCreatedDate(new Date());
        eveApiDataService.populateCharacterData(character);
        characterDao.put(character, userKey);

        eveSynchronizationService.synchronizeCreateCharacter(character, userKey);
    }

    @Override
    public void deleteCharacter(Long characterID, Key<User> userKey) {
        Key<Character> characterKey = new Key<Character>(Character.class, characterID);
        Character character = characterDao.get(characterKey);
        characterDao.delete(characterKey, userKey);

        eveSynchronizationService.synchronizeDeleteCharacter(character.getCharacterID(), userKey);
    }

    @Override
    public List<ApiKey> getApiKeys(Key<User> userKey) {
        return apiKeyDao.getAll(userKey);
    }

    @Override
    public List<ApiKey> getFullApiKeys(Key<User> userKey) {
        return apiKeyDao.getAllFull(userKey);
    }

    @Override
    public void createApiKey(String apiKeyString, Long apiKeyUserID, Key<User> userKey) throws EveApiException, ApiKeyShouldBeRemovedException {
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(apiKeyUserID);
        apiKey.setUser(userKey);
        apiKey.setCreatedDate(new Date());
        eveApiDataService.populateApiKeyData(apiKey, apiKeyString);
        apiKeyDao.put(apiKey, userKey);

        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);
    }

    @Override
    public void deleteApiKey(Long apiKeyID, Key<User> userKey) {
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, apiKeyID);
        apiKeyDao.delete(apiKeyKey, userKey);

        eveSynchronizationService.synchronizeDeleteApiKey(apiKeyKey, userKey);
    }

    @Override
    public List<SkillLevel> fetchCalculationSkillLevelsForMainCharacter(User user) throws EveApiException {
        List<SkillLevelDto> mainCharacterSkills = eveApiDataService.getMainCharacterSkills(user);
        Map<Long, SkillLevelDto> typeIdToSkillLevelDtoMap = new HashMap<Long, SkillLevelDto>();
        for (SkillLevelDto skillLevelDto : mainCharacterSkills) {
            typeIdToSkillLevelDtoMap.put(skillLevelDto.getTypeID(), skillLevelDto);
        }

        List<SkillLevel> skillLevelsForCalculation = new ArrayList<SkillLevel>();
        for (SkillForCalculation skillForCalculation : SkillForCalculation.values()) {
            SkillLevelDto mainCharacterSkillLevelDto = typeIdToSkillLevelDtoMap.get(skillForCalculation.getTypeID());
            SkillLevel skillLevel = new SkillLevel();
            skillLevel.setTypeID(skillForCalculation.getTypeID());
            skillLevel.setLevel(mainCharacterSkillLevelDto == null ? 0 : mainCharacterSkillLevelDto.getLevel());
            skillLevelsForCalculation.add(skillLevel);
        }
        return skillLevelsForCalculation;
    }
}
