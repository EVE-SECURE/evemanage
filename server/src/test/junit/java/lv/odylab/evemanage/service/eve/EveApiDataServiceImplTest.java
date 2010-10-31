package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.exception.ApiKeyNotValidException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.integration.eveapi.EveApiGateway;
import lv.odylab.evemanage.integration.eveapi.dto.AccountBalanceDto;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.security.EveManageSecurityManager;
import lv.odylab.evemanage.security.HashCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EveApiDataServiceImplTest {
    @Mock
    private EveManageSecurityManager securityManager;
    @Mock
    private HashCalculator hashCalculator;
    @Mock
    private ApiKeyDao apiKeyDao;
    @Mock
    private EveApiGateway eveApiGateway;
    private EveApiDataService eveApiDataService;

    @Before
    public void setUp() {
        eveApiDataService = new EveApiDataServiceImpl(securityManager, hashCalculator, apiKeyDao, eveApiGateway);
    }

    @Test
    public void testPopulateCharacterData() throws EveApiException, IOException {
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, 1);
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(2L);
        apiKey.setEncodedApiKeyString(Base64.encodeBytes("encryptedApiKey".getBytes()));
        Character character = new Character();
        character.setCharacterID(1L);
        character.setApiKey(apiKeyKey);
        CharacterSheetDto characterSheetDto = new CharacterSheetDto();
        characterSheetDto.setCharacterID(1L);
        characterSheetDto.setName("characterName");
        characterSheetDto.setCorporationID(4L);
        characterSheetDto.setCorporationName("corporationName");
        List<String> corporationTitles = new ArrayList<String>();
        corporationTitles.add("corporationTitle1");
        corporationTitles.add("corporationTitle2");
        characterSheetDto.setCorporationTitles(corporationTitles);
        CorporationSheetDto corporationSheetDto = new CorporationSheetDto();
        corporationSheetDto.setTicker("ticker");
        corporationSheetDto.setAllianceID(5L);
        corporationSheetDto.setAllianceName("allianceName");

        when(securityManager.decrypt("encryptedApiKey".getBytes())).thenReturn("apiKeyString".getBytes());
        when(apiKeyDao.get(apiKeyKey)).thenReturn(apiKey);
        when(eveApiGateway.getCharacterSheet("apiKeyString", 2L, 1L)).thenReturn(characterSheetDto);
        when(eveApiGateway.getCorporationSheet(4L)).thenReturn(corporationSheetDto);
        eveApiDataService.populateCharacterData(character);

        assertEquals("characterName", character.getName());
        assertEquals(Long.valueOf(4), character.getCorporationID());
        assertEquals("corporationName", character.getCorporationName());
        assertEquals(2, character.getCorporationTitles().size());
        assertEquals("corporationTitle1", character.getCorporationTitles().get(0));
        assertEquals("corporationTitle2", character.getCorporationTitles().get(1));
        assertEquals("corporationTitle2", character.getCorporationTitles().get(1));
        assertEquals("ticker", character.getCorporationTicker());
        assertEquals(Long.valueOf(5), character.getAllianceID());
        assertEquals("allianceName", character.getAllianceName());
        assertNotNull(character.getUpdatedDate());
        assertNull(character.getCreatedDate());
    }

    @Test
    public void testPopulateCharacterData_NoApiKey() throws EveApiException, IOException {
        Character character = new Character();
        eveApiDataService.populateCharacterData(character);
        verify(apiKeyDao, never()).get(any(Key.class));
    }

    @Test
    public void testPopulateApiKeyData_ValidFull() throws EveApiException, ApiKeyNotValidException {
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(1L);
        List<AccountCharacterDto> accountCharacterDtos = new ArrayList<AccountCharacterDto>();
        AccountCharacterDto accountCharacterDto1 = new AccountCharacterDto();
        accountCharacterDto1.setName("character1");
        accountCharacterDto1.setCharacterID(2L);
        accountCharacterDto1.setCorporationName("corporation1");
        accountCharacterDto1.setCorporationID(3L);
        AccountCharacterDto accountCharacterDto2 = new AccountCharacterDto();
        accountCharacterDto2.setName("character2");
        accountCharacterDto2.setCharacterID(4L);
        accountCharacterDto2.setCorporationName("corporation2");
        accountCharacterDto2.setCorporationID(5L);
        accountCharacterDtos.add(accountCharacterDto1);
        accountCharacterDtos.add(accountCharacterDto2);

        when(securityManager.encrypt("apiKeyString".getBytes())).thenReturn("encryptedApiKey".getBytes());
        when(hashCalculator.hashApiKey(1L, "apiKeyString")).thenReturn("apiKeyHash");
        when(eveApiGateway.getApiKeyCharacters("apiKeyString", 1L)).thenReturn(accountCharacterDtos);
        when(eveApiGateway.getAccountBalances("apiKeyString", 1L, 2L)).thenReturn(new ArrayList<AccountBalanceDto>());
        eveApiDataService.populateApiKeyData(apiKey, "apiKeyString");

        assertEquals(Base64.encodeBytes("encryptedApiKey".getBytes()), apiKey.getEncodedApiKeyString());
        assertEquals("apiKeyHash", apiKey.getApiKeyHash());
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = apiKey.getCharacterInfos();
        assertEquals(2, apiKeyCharacterInfos.size());
        ApiKeyCharacterInfo apiKeyCharacterInfo1 = apiKeyCharacterInfos.get(0);
        assertEquals(Long.valueOf(2), apiKeyCharacterInfo1.getCharacterID());
        assertEquals("character1", apiKeyCharacterInfo1.getName());
        assertEquals(Long.valueOf(3), apiKeyCharacterInfo1.getCorporationID());
        assertEquals("corporation1", apiKeyCharacterInfo1.getCorporationName());
        ApiKeyCharacterInfo apiKeyCharacterInfo2 = apiKeyCharacterInfos.get(1);
        assertEquals(Long.valueOf(4), apiKeyCharacterInfo2.getCharacterID());
        assertEquals("character2", apiKeyCharacterInfo2.getName());
        assertEquals(Long.valueOf(5), apiKeyCharacterInfo2.getCorporationID());
        assertEquals("corporation2", apiKeyCharacterInfo2.getCorporationName());
        assertNotNull(apiKey.getLastCheckDate());
        assertTrue(apiKey.isValid());
        assertEquals("FULL", apiKey.getKeyType());
        assertNotNull(apiKey.getUpdatedDate());
        assertNull(apiKey.getCreatedDate());
    }

    @Test
    public void testPopulateApiKeyData_ValidLimitedWithCharacter() throws EveApiException, ApiKeyNotValidException {
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(1L);
        List<AccountCharacterDto> accountCharacterDtos = new ArrayList<AccountCharacterDto>();
        AccountCharacterDto accountCharacterDto = new AccountCharacterDto();
        accountCharacterDto.setName("character1");
        accountCharacterDto.setCharacterID(2L);
        accountCharacterDto.setCorporationName("corporation1");
        accountCharacterDto.setCorporationID(3L);
        accountCharacterDtos.add(accountCharacterDto);

        when(securityManager.encrypt("apiKeyString".getBytes())).thenReturn("encryptedApiKey".getBytes());
        when(eveApiGateway.getApiKeyCharacters("apiKeyString", 1L)).thenReturn(accountCharacterDtos);
        when(eveApiGateway.getAccountBalances("apiKeyString", 1L, 2L)).thenThrow(new EveApiException("errorCode"));
        eveApiDataService.populateApiKeyData(apiKey, "apiKeyString");

        assertEquals("LIMITED", apiKey.getKeyType());
    }

    @Test
    public void testPopulateApiKeyData_ValidLimitedWithoutCharacters() throws EveApiException, ApiKeyNotValidException {
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(1L);

        when(securityManager.encrypt("apiKeyString".getBytes())).thenReturn("encryptedApiKey".getBytes());
        when(eveApiGateway.getApiKeyCharacters("apiKeyString", 1L)).thenReturn(new ArrayList<AccountCharacterDto>());
        eveApiDataService.populateApiKeyData(apiKey, "apiKeyString");

        assertEquals("LIMITED", apiKey.getKeyType());
    }

    @Test
    public void testPopulateApiKeyData_ExistingKey() throws EveApiException, ApiKeyNotValidException {
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKeyUserID(1L);
        String encodedApiKeyString = Base64.encodeBytes("encryptedApiKey".getBytes());
        apiKey.setEncodedApiKeyString(encodedApiKeyString);
        apiKey.setApiKeyHash("apiKeyHash");
        apiKey.setKeyType("FULL");

        when(securityManager.decrypt("encryptedApiKey".getBytes())).thenReturn("apiKeyString".getBytes());
        when(eveApiGateway.getApiKeyCharacters("apiKeyString", 1L)).thenReturn(new ArrayList<AccountCharacterDto>());
        eveApiDataService.populateApiKeyData(apiKey);

        assertEquals(encodedApiKeyString, apiKey.getEncodedApiKeyString());
        assertEquals("apiKeyHash", apiKey.getApiKeyHash());
        assertEquals("FULL", apiKey.getKeyType());
    }
}
