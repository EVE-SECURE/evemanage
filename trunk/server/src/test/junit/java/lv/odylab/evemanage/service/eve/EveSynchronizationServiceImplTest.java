package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.user.UserSynchronizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EveSynchronizationServiceImplTest {
    @Mock
    private UserSynchronizationService userSynchronizationService;
    @Mock
    private EveApiDataService eveApiDataService;
    @Mock
    private ApiKeyDao apiKeyDao;
    @Mock
    private CharacterDao characterDao;
    private EveSynchronizationService eveSynchronizationService;

    @Captor
    private ArgumentCaptor<Character> characterCaptor;

    @Before
    public void setUp() {
        eveSynchronizationService = new EveSynchronizationServiceImpl(userSynchronizationService, eveApiDataService, apiKeyDao, characterDao);
    }

    @Test
    public void testSynchronizeCreateCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        eveSynchronizationService.synchronizeCreateCharacter(new Character(), userKey);
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDeleteCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        eveSynchronizationService.synchronizeDeleteCharacter(1L, userKey);
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeCreateApiKey_NoKeyCharacters_NoDetachedCharacters() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        ApiKey apiKey = new ApiKey();
        when(characterDao.getAllWithoutApiKey(userKey)).thenReturn(new ArrayList<Character>());
        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeCreateApiKey_NoKeyCharacter_TwoDetachedCharacters() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        ApiKey apiKey = new ApiKey();
        ArrayList<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        character1.setCharacterID(1L);
        Character character2 = new Character();
        character2.setCharacterID(2L);
        characters.add(character1);
        characters.add(character2);

        when(characterDao.getAllWithoutApiKey(userKey)).thenReturn(characters);
        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);

        verify(characterDao, never()).put(any(Character.class), eq(userKey));
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeCreateApiKey_OneKeyCharacter_NoDetachedCharacters() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        ApiKey apiKey = new ApiKey();
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo.setCharacterID(1L);
        apiKeyCharacterInfos.add(apiKeyCharacterInfo);
        apiKey.setCharacterInfos(apiKeyCharacterInfos);

        when(characterDao.getAllWithoutApiKey(userKey)).thenReturn(new ArrayList<Character>());
        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);

        verify(characterDao, never()).put(any(Character.class), eq(userKey));
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeCreateApiKey_OneKeyCharacter_OneMatchingDetachedCharacter() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        ApiKey apiKey = new ApiKey();
        apiKey.setId(1L);
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo.setCharacterID(2L);
        apiKeyCharacterInfos.add(apiKeyCharacterInfo);
        apiKey.setCharacterInfos(apiKeyCharacterInfos);
        ArrayList<Character> characters = new ArrayList<Character>();
        Character character = new Character();
        character.setCharacterID(2L);
        characters.add(character);

        when(characterDao.getAllWithoutApiKey(userKey)).thenReturn(characters);
        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);

        verify(characterDao, times(1)).put(any(Character.class), eq(userKey));
        verify(eveApiDataService, times(1)).populateCharacterData(character);
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeCreateApiKey_OneKeyCharacter_OneNotMatchingDetachedCharacter() throws EveApiException {
        Key<User> userKey = new Key<User>(User.class, 1);
        ApiKey apiKey = new ApiKey();
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo.setCharacterID(2L);
        apiKeyCharacterInfos.add(apiKeyCharacterInfo);
        apiKey.setCharacterInfos(apiKeyCharacterInfos);
        ArrayList<Character> characters = new ArrayList<Character>();
        Character character = new Character();
        character.setCharacterID(3L);
        characters.add(character);

        when(characterDao.getAllWithoutApiKey(userKey)).thenReturn(characters);
        eveSynchronizationService.synchronizeCreateApiKey(apiKey, userKey);

        verify(characterDao, never()).put(any(Character.class), eq(userKey));
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDeleteApiKey_NoCharacters() {
        Key<User> userKey = new Key<User>(User.class, 1);
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, 1);

        when(characterDao.getAll(apiKeyKey, userKey)).thenReturn(new ArrayList<Character>());
        eveSynchronizationService.synchronizeDeleteApiKey(apiKeyKey, userKey);

        verify(characterDao, never()).put(any(Character.class), eq(userKey));
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDeleteApiKey_OneCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, 1);
        ArrayList<Character> characters = new ArrayList<Character>();
        Character character = new Character();
        character.setApiKey(apiKeyKey);
        character.setCharacterID(1L);
        character.setCorporationID(2L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        ArrayList<String> corporationTitles = new ArrayList<String>();
        corporationTitles.add("corporationTitle");
        character.setCorporationTitles(corporationTitles);
        character.setAllianceID(3L);
        character.setAllianceName("allianceNAme");
        character.setCreatedDate(new Date());
        characters.add(character);

        when(characterDao.getAll(apiKeyKey, userKey)).thenReturn(characters);
        eveSynchronizationService.synchronizeDeleteApiKey(apiKeyKey, userKey);

        verify(characterDao, times(1)).putWithoutChecks(characterCaptor.capture());
        Character capturedCharacter = characterCaptor.getValue();
        assertNull(capturedCharacter.getApiKey());
        assertNull(capturedCharacter.getCorporationID());
        assertNull(capturedCharacter.getCorporationName());
        assertNull(capturedCharacter.getCorporationTicker());
        assertNull(capturedCharacter.getCorporationTitles());
        assertNull(capturedCharacter.getAllianceID());
        assertNull(capturedCharacter.getAllianceName());
        assertNotNull(capturedCharacter.getCreatedDate());
        assertNotNull(capturedCharacter.getUpdatedDate());
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDeleteApiKey_OneCharacter_OtherKey() {
        Key<User> userKey = new Key<User>(User.class, 1);
        Key<ApiKey> apiKeyKey = new Key<ApiKey>(ApiKey.class, 1);
        List<ApiKey> otherApiKeys = new ArrayList<ApiKey>();
        ApiKey otherApiKey = new ApiKey();
        otherApiKey.setId(2L);
        List<ApiKeyCharacterInfo> apiKeyCharacterInfos = new ArrayList<ApiKeyCharacterInfo>();
        ApiKeyCharacterInfo apiKeyCharacterInfo = new ApiKeyCharacterInfo();
        apiKeyCharacterInfo.setCharacterID(1L);
        apiKeyCharacterInfos.add(apiKeyCharacterInfo);
        otherApiKey.setCharacterInfos(apiKeyCharacterInfos);
        otherApiKeys.add(otherApiKey);

        ArrayList<Character> characters = new ArrayList<Character>();
        Character character = new Character();
        character.setApiKey(apiKeyKey);
        character.setCharacterID(1L);
        characters.add(character);

        when(apiKeyDao.getAll(userKey)).thenReturn(otherApiKeys);
        when(characterDao.getAll(apiKeyKey, userKey)).thenReturn(characters);
        eveSynchronizationService.synchronizeDeleteApiKey(apiKeyKey, userKey);

        verify(characterDao, times(1)).putWithoutChecks(characterCaptor.capture());
        assertEquals(2, characterCaptor.getValue().getApiKey().getId());
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeUpdateCharacters() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<Character> characters = new ArrayList<Character>();
        characters.add(new Character());
        characters.add(new Character());

        eveSynchronizationService.synchronizeUpdateCharacters(characters, userKey);

        verify(characterDao, never()).put(any(Character.class), eq(userKey));
        verify(userSynchronizationService, times(1)).synchronizeMainCharacter(userKey);
    }
}
