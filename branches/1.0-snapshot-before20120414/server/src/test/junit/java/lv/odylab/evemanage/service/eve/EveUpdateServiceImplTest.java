package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EveUpdateServiceImplTest {
    @Mock
    private EveApiDataService eveApiDataService;
    @Mock
    private EveSynchronizationService eveSynchronizationService;
    @Mock
    private ApiKeyDao apiKeyDao;
    @Mock
    private CharacterDao characterDao;
    private EveUpdateService eveUpdateService;

    @Before
    public void setUp() {
        eveUpdateService = new EveUpdateServiceImpl(eveApiDataService, eveSynchronizationService, apiKeyDao, characterDao);
    }

    @Test
    public void testUpdateApiKeysForUser_NoKeys_NoCharacters() throws EveApiException, ApiKeyShouldBeRemovedException {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        List<Character> characters = new ArrayList<Character>();

        when(apiKeyDao.getAll(userKey)).thenReturn(apiKeys);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        eveUpdateService.updateApiKeysForUser(userKey);

        verify(eveApiDataService, never()).populateApiKeyData(any(ApiKey.class));
        verify(apiKeyDao, never()).putWithoutChecks(any(ApiKey.class));
        verify(eveApiDataService, never()).populateCharacterData(any(Character.class));
        verify(characterDao, never()).putWithoutChecks(any(Character.class));
    }

    @Test
    public void testUpdateApiKeysForUser_OneKey_TwoCharacters() throws EveApiException, ApiKeyShouldBeRemovedException {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        ApiKey apiKey = new ApiKey();
        apiKeys.add(apiKey);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        Character character2 = new Character();
        characters.add(character1);
        characters.add(character2);

        when(apiKeyDao.getAll(userKey)).thenReturn(apiKeys);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        eveUpdateService.updateApiKeysForUser(userKey);

        verify(eveApiDataService, times(1)).populateApiKeyData(any(ApiKey.class));
        verify(apiKeyDao, times(1)).putWithoutChecks(any(ApiKey.class));
/*        verify(eveApiDataService, times(2)).populateCharacterData(any(Character.class));
        verify(characterDao, times(2)).putWithoutChecks(any(Character.class));*/
    }

    @Test
    public void testUpdateApiKeysForUser_OneKey_TwoCharacters_InvalidApiKey() throws EveApiException, ApiKeyShouldBeRemovedException {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        ApiKey apiKey = new ApiKey();
        apiKeys.add(apiKey);
        apiKey.setCharacterInfos(new ArrayList<ApiKeyCharacterInfo>());
        apiKey.setValid(Boolean.TRUE);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        character1.setCorporationID(1L);
        character1.setCorporationName("corporationName");
        List<String> corporationTitles = new ArrayList<String>();
        corporationTitles.add("title1");
        character1.setCorporationTitles(corporationTitles);
        character1.setCorporationTicker("corporationTicker");
        character1.setAllianceID(2L);
        character1.setAllianceName("allianceName");
        Character character2 = new Character();
        characters.add(character1);
        characters.add(character2);

        when(apiKeyDao.getAll(userKey)).thenReturn(apiKeys);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        doThrow(new ApiKeyShouldBeRemovedException("api key not valid")).when(eveApiDataService).populateApiKeyData(any(ApiKey.class));
        doThrow(new EveApiException("api error")).when(eveApiDataService).populateCharacterData(any(Character.class));
        eveUpdateService.updateApiKeysForUser(userKey);

        verify(eveApiDataService, times(1)).populateApiKeyData(any(ApiKey.class));
/*        verify(apiKeyDao, times(1)).putWithoutChecks(any(ApiKey.class));
        verify(eveApiDataService, times(2)).populateCharacterData(any(Character.class));
        verify(characterDao, times(2)).putWithoutChecks(any(Character.class));
        assertNull(apiKey.getCharacterInfos());
        assertNotNull(apiKey.getLastCheckDate());
        assertNotNull(apiKey.getUpdatedDate());
        assertFalse(apiKey.isValid());
        assertNull(character1.getCorporationID());
        assertNull(character1.getCorporationName());
        assertNull(character1.getCorporationTitles());
        assertNull(character1.getCorporationTicker());
        assertNull(character1.getAllianceID());
        assertNull(character1.getAllianceName());
        assertNotNull(character1.getUpdatedDate());*/
    }
}
