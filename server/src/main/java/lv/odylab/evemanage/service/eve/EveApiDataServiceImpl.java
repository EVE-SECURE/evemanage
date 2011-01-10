package lv.odylab.evemanage.service.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.EveManageSecurityException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.eveapi.EveApiGateway;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.SkillLevelDto;
import lv.odylab.evemanage.security.EveManageSecurityManager;
import lv.odylab.evemanage.security.HashCalculator;
import lv.odylab.evemanage.shared.eve.ApiKeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EveApiDataServiceImpl implements EveApiDataService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageSecurityManager securityManager;
    private final HashCalculator hashCalculator;
    private final ApiKeyDao apiKeyDao;
    private final EveApiGateway eveApiGateway;

    @Inject
    public EveApiDataServiceImpl(EveManageSecurityManager securityManager, HashCalculator hashCalculator, ApiKeyDao apiKeyDao, EveApiGateway eveApiGateway) {
        this.securityManager = securityManager;
        this.hashCalculator = hashCalculator;
        this.eveApiGateway = eveApiGateway;
        this.apiKeyDao = apiKeyDao;
    }

    @Override
    public void populateCharacterData(Character character) throws EveApiException {
        logger.debug("Populating information from EVE api to character: {} ({})", character.getName(), character.getCharacterID());
        Key<ApiKey> apiKeyKey = character.getApiKey();
        if (apiKeyKey == null) {
            logger.debug("Character does not have api key, skipping");
            return;
        }
        ApiKey apiKey;
        try {
            apiKey = apiKeyDao.get(apiKeyKey);
        } catch (NotFoundException e) {
            logger.error("Caught NotFoundException", e);
            logger.error("Api key was not found in datastore despite character having reference to it, this is inconsistency, character: {} ({})", character.getName(), character.getCharacterID());
            throw new EveApiException("Character has invalid api key");
        }

        CharacterSheetDto characterSheetDto = eveApiGateway.getCharacterSheet(decodeApiKeyString(apiKey.getEncodedApiKeyString()), apiKey.getApiKeyUserID(), character.getCharacterID());
        CorporationSheetDto corporationSheetDto = eveApiGateway.getCorporationSheet(characterSheetDto.getCorporationID());
        character.setName(characterSheetDto.getName());
        character.setCorporationID(characterSheetDto.getCorporationID());
        character.setCorporationName(characterSheetDto.getCorporationName());
        character.setCorporationTitles(characterSheetDto.getCorporationTitles());
        character.setCorporationTicker(corporationSheetDto.getTicker());
        character.setAllianceID(corporationSheetDto.getAllianceID());
        character.setAllianceName(corporationSheetDto.getAllianceName());
        character.setUpdatedDate(new Date());
    }

    @Override
    public void populateApiKeyData(ApiKey apiKey) throws EveApiException, ApiKeyShouldBeRemovedException {
        populateApiKeyData(apiKey, decodeApiKeyString(apiKey.getEncodedApiKeyString()));
    }

    @Override
    public void populateApiKeyData(ApiKey apiKey, String apiKeyString) throws EveApiException, ApiKeyShouldBeRemovedException {
        logger.debug("Populating information from EVE api to api key");

        Long apiKeyUserID = apiKey.getApiKeyUserID();
        if (apiKey.getEncodedApiKeyString() == null) {
            logger.debug("Encoded api key string is not set, going to encode and set it");
            apiKey.setEncodedApiKeyString(encodeApiKeyString(apiKeyString));
        }
        if (apiKey.getApiKeyHash() == null) {
            logger.debug("Api key hash is not set, going to calculate and set it");
            apiKey.setApiKeyHash(hashCalculator.hashApiKey(apiKeyUserID, apiKeyString));
        }
        List<AccountCharacterDto> apiCharacters = eveApiGateway.getApiKeyCharacters(apiKeyString, apiKeyUserID);
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = new ArrayList<ApiKeyCharacterInfo>();
        logger.debug("Api key has {} characters", apiCharacters.size());
        for (AccountCharacterDto apiCharacter : apiCharacters) {
            logger.debug("Api key has character: {} ({})", apiCharacter.getName(), apiCharacter.getCharacterID());
            ApiKeyCharacterInfo apiKeyCharacterInfo = new ApiKeyCharacterInfo();
            apiKeyCharacterInfo.setCharacterID(apiCharacter.getCharacterID());
            apiKeyCharacterInfo.setName(apiCharacter.getName());
            apiKeyCharacterInfo.setCorporationID(apiCharacter.getCorporationID());
            apiKeyCharacterInfo.setCorporationName(apiCharacter.getCorporationName());
            apiKeyCharacterInfos.add(apiKeyCharacterInfo);
        }
        apiKey.setCharacterInfos(apiKeyCharacterInfos);
        apiKey.setLastCheckDate(new Date());
        apiKey.setValid(Boolean.TRUE);
        if (apiKey.getKeyType() == null) {
            logger.debug("Api key type is not set, setting api key type to LIMITED, going to check if it is possible to retrieve account balances");
            apiKey.setKeyType(ApiKeyType.LIMITED.toString());
            if (apiKeyCharacterInfos.size() > 0) {
                try {
                    eveApiGateway.getAccountBalances(apiKeyString, apiKeyUserID, apiKeyCharacterInfos.get(0).getCharacterID());
                    apiKey.setKeyType(ApiKeyType.FULL.toString());
                    logger.debug("Able to retrieve account balances, going to set api key type to FULL");
                } catch (EveApiException e) {
                    logger.debug("Leaving api key type as LIMITED, caught EveApiException on getting account balances");
                }
            } else {
                logger.debug("Key does not have any characters, unable to determine type, going to leave LIMITED");
            }
        } else {
            logger.debug("Api key type is already set to: {}, not going to recheck", apiKey.getKeyType());
        }
        apiKey.setUpdatedDate(new Date());
    }

    @Override
    public List<SkillLevelDto> getMainCharacterSkills(User user) throws EveApiException {
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        Long mainCharacterID = mainCharacterInfo.getCharacterID();
        ApiKey apiKey = apiKeyDao.getWithCharacterID(mainCharacterID, new Key<User>(User.class, user.getId()));
        CharacterSheetDto characterSheetDto = eveApiGateway.getCharacterSheet(decodeApiKeyString(apiKey.getEncodedApiKeyString()), apiKey.getApiKeyUserID(), mainCharacterID);
        return characterSheetDto.getSkillLevels();
    }

    private String encodeApiKeyString(String apiKeyString) {
        byte[] encryptedApiKeyBytes = securityManager.encrypt(apiKeyString.getBytes());
        return Base64.encodeBytes(encryptedApiKeyBytes);
    }

    private String decodeApiKeyString(String encodedApikeyString) {
        try {
            byte[] decodedApiKeyBytes = Base64.decode(encodedApikeyString);
            byte[] decryptedApiKeyBytes = securityManager.decrypt(decodedApiKeyBytes);
            return new String(decryptedApiKeyBytes);
        } catch (IOException e) {
            throw new EveManageSecurityException(e);
        }
    }
}
