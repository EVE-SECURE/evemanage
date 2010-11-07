package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EveManagementServiceImplTest {
    @Mock
    private EveApiDataService eveApiDataService;
    @Mock
    private EveSynchronizationService synchronizationService;
    @Mock
    private CharacterDao characterDao;
    @Mock
    private ApiKeyDao apiKeyDao;
    private EveManagementService eveManagementService;

    @Captor
    private ArgumentCaptor<Character> characterCaptor;
    @Captor
    private ArgumentCaptor<Key<Character>> characterKeyCaptor;
    @Captor
    private ArgumentCaptor<ApiKey> apiKeyCaptor;
    @Captor
    private ArgumentCaptor<Key<ApiKey>> apiKeyKeyCaptor;
    @Captor
    private ArgumentCaptor<String> apiKeyStringCaptor;
    @Captor
    private ArgumentCaptor<Key<User>> userKeyCaptor;

    @Before
    public void setUp() {
        eveManagementService = new EveManagementServiceImpl(eveApiDataService, synchronizationService, characterDao, apiKeyDao);
    }

    @Test
    public void testGetCharacters() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<Character> characters = new ArrayList<Character>();
        when(characterDao.getAll(userKey)).thenReturn(characters);
        assertEquals(characters, eveManagementService.getCharacters(userKey));
    }

    @Test
    public void testGetAvailableNewCharacterNames() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        Character character2 = new Character();
        character1.setName("hcydo");
        character2.setName("pcydo");
        characters.add(character1);
        characters.add(character2);
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        ApiKey apiKey1 = new ApiKey();
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos1 = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo1 = new ApiKeyCharacterInfo();
        ApiKeyCharacterInfo apiKeyCharacterInfo2 = new ApiKeyCharacterInfo();
        ApiKeyCharacterInfo apiKeyCharacterInfo3 = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo1.setName("fcydo");
        apiKeyCharacterInfo2.setName("pcydo");
        apiKeyCharacterInfo3.setName("hcydo");
        apiKeyCharacterInfos1.add(apiKeyCharacterInfo1);
        apiKeyCharacterInfos1.add(apiKeyCharacterInfo2);
        apiKeyCharacterInfos1.add(apiKeyCharacterInfo3);
        apiKey1.setCharacterInfos(apiKeyCharacterInfos1);
        ApiKey apiKey2 = new ApiKey();
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos2 = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo4 = new ApiKeyCharacterInfo();
        ApiKeyCharacterInfo apiKeyCharacterInfo5 = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo4.setName("icydo");
        apiKeyCharacterInfo5.setName("tcydo");
        apiKeyCharacterInfos2.add(apiKeyCharacterInfo4);
        apiKeyCharacterInfos2.add(apiKeyCharacterInfo5);
        apiKey2.setCharacterInfos(apiKeyCharacterInfos2);
        apiKeys.add(apiKey1);
        apiKeys.add(apiKey2);

        when(characterDao.getAll(userKey)).thenReturn(characters);
        when(apiKeyDao.getAll(userKey)).thenReturn(apiKeys);
        List<CharacterNameDto> newCharacterNames = eveManagementService.getAvailableNewCharacterNames(userKey);

        assertEquals(3, newCharacterNames.size());
        assertEquals("fcydo", newCharacterNames.get(0).getName());
        assertEquals("icydo", newCharacterNames.get(1).getName());
        assertEquals("tcydo", newCharacterNames.get(2).getName());
    }

    @Test
    public void testCreateCharacter() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, 2);

        when(apiKeyDao.getWithCharacterID(1L, userKey)).thenReturn(apiKeyKey);
        eveManagementService.createCharacter(1L, userKey);

        verify(eveApiDataService, times(1)).populateCharacterData(characterCaptor.capture());
        Character character = characterCaptor.getValue();
        assertEquals(apiKeyKey, character.getApiKey());
        assertEquals(userKey, character.getUser());
        assertEquals(Long.valueOf(1), character.getCharacterID());
        assertNotNull(character.getCreatedDate());
        verify(characterDao, times(1)).put(character, userKey);
        verify(synchronizationService, times(1)).synchronizeCreateCharacter(character, userKey);
    }

    @Test
    public void testDeleteCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        eveManagementService.deleteCharacter(1L, userKey);
        verify(characterDao, times(1)).delete(characterKeyCaptor.capture(), eq(userKey));
        assertEquals(1, characterKeyCaptor.getValue().getId());
        verify(synchronizationService).synchronizeDeleteCharacter(1L, userKey);
    }

    @Test
    public void testGetApiKeys() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        when(apiKeyDao.getAll(userKey)).thenReturn(apiKeys);
        assertEquals(apiKeys, eveManagementService.getApiKeys(userKey));
    }

    @Test
    public void testCreateApiKey() throws EveApiException, ApiKeyShouldBeRemovedException {
        Key<User> userKey = new Key<User>(User.class, 1);
        eveManagementService.createApiKey("apiKeyString", 1L, userKey);
        verify(eveApiDataService, times(1)).populateApiKeyData(apiKeyCaptor.capture(), eq("apiKeyString"));
        ApiKey apiKey = apiKeyCaptor.getValue();
        assertEquals(Long.valueOf(1), apiKey.getApiKeyUserID());
        assertEquals(userKey, apiKey.getUser());
        assertNotNull(apiKey.getCreatedDate());
        verify(apiKeyDao, times(1)).put(apiKey, userKey);
        verify(synchronizationService, times(1)).synchronizeCreateApiKey(apiKey, userKey);
    }

    @Test
    public void testDeleteApiKey() {
        Key<User> userKey = new Key<User>(User.class, 1);
        eveManagementService.deleteApiKey(1L, userKey);
        verify(apiKeyDao, times(1)).delete(apiKeyKeyCaptor.capture(), userKeyCaptor.capture());
        Key<ApiKey> apiKeyKey = apiKeyKeyCaptor.getValue();
        assertEquals(1, apiKeyKey.getId());
        assertEquals(userKey, userKeyCaptor.getValue());
        verify(synchronizationService, times(1)).synchronizeDeleteApiKey(apiKeyKey, userKey);
    }
}
