package lv.odylab.evemanage.service.user;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.system.SystemPropertyDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.UserDao;
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
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceImplTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private UserService userService;
    @Mock
    private SystemPropertyDao systemPropertyDao;
    @Mock
    private UserDao userDao;
    @Mock
    private CharacterDao characterDao;
    private UserManagementService userManagementService;

    @Captor
    private ArgumentCaptor<lv.odylab.evemanage.domain.user.User> userCaptor;

    @Before
    public void setUp() {
        userManagementService = new UserManagementServiceImpl(googleAppEngineServices, systemPropertyDao, userDao, characterDao);
        when(googleAppEngineServices.getUserService()).thenReturn(userService);
    }

    @Test
    public void testLogin_NoUser() {
        when(userService.getCurrentUser()).thenReturn(null);
        when(userService.createLoginURL("requestUri")).thenReturn("loginURL");

        LoginDto loginDto = userManagementService.login("requestUri", "en");
        assertFalse(loginDto.isLoggedIn());
        assertEquals("loginURL", loginDto.getLoginUrl());
    }

    @Test
    public void testLogin_NoUser_GwtDevelopment() {
        when(userService.getCurrentUser()).thenReturn(null);
        when(userService.createLoginURL("http://127.0.0.1:8888/")).thenReturn("loginURL");
        LoginDto loginDto = userManagementService.login("http://127.0.0.1:8888/", "en");
        assertFalse(loginDto.isLoggedIn());
        assertEquals("loginURL?gwt.codesvr=127.0.0.1:9997", loginDto.getLoginUrl());
    }

    @Test
    public void testLogin_NewUser() {
        User googleUser = new User("vleushin@gmail.com", "gmail.com", "1234567890");
        when(userService.getCurrentUser()).thenReturn(googleUser);
        when(userDao.getByUserAuthID("1234567890")).thenReturn(null);
        when(userService.createLogoutURL("requestUri")).thenReturn("logoutURL");
        when(userService.isUserAdmin()).thenReturn(false);
        LoginDto loginDto = userManagementService.login("requestUri", "en");

        verify(userDao, times(1)).put(userCaptor.capture());
        assertTrue(loginDto.isLoggedIn());
        assertEquals("vleushin@gmail.com", loginDto.getEmailAddress());
        assertEquals("vleushin", loginDto.getNickname());
        assertEquals("logoutURL", loginDto.getLogoutUrl());
        assertFalse(loginDto.isAdmin());
        assertEquals(0, loginDto.getRoles().size());
    }

    @Test
    public void testLogin_ExistingUser() {
        User googleUser = new User("vleushin@gmail.com", "gmail.com", "1234567890");
        when(userService.getCurrentUser()).thenReturn(googleUser);
        lv.odylab.evemanage.domain.user.User user = new lv.odylab.evemanage.domain.user.User();
        user.setId(1L);
        user.setUserAuthID("1234567890");
        user.setAuthDomain("gmail.com");
        user.setEmail("vleushin@gmail.com");
        user.setNickname("nickname");

        when(userDao.getByUserAuthID("1234567890")).thenReturn(user);
        when(userService.createLogoutURL("requestUri")).thenReturn("logoutURL");
        when(userService.isUserAdmin()).thenReturn(true);
        LoginDto loginDto = userManagementService.login("requestUri", "en");

        verify(userDao, times(1)).put(user);
        assertTrue(loginDto.isLoggedIn());
        assertEquals(Long.valueOf(1), loginDto.getUserID());
        assertEquals("vleushin@gmail.com", loginDto.getEmailAddress());
        assertEquals("vleushin", loginDto.getNickname());
        assertEquals("logoutURL", loginDto.getLogoutUrl());
        assertTrue(loginDto.isAdmin());
        assertEquals(0, loginDto.getRoles().size());
    }

    @Test
    public void testGetUser() {
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        lv.odylab.evemanage.domain.user.User user = new lv.odylab.evemanage.domain.user.User();
        when(userDao.get(userKey)).thenReturn(user);
        assertEquals(user, userManagementService.getUser(userKey));
    }

    @Test
    public void testGetCurrentUserKey() {
        User googleUser = new User("vleushin@gmail.com", "gmail.com", "1234567890");
        when(userService.getCurrentUser()).thenReturn(googleUser);
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        when(userDao.geKeyByUserAuthID("1234567890")).thenReturn(userKey);
        assertEquals(userKey, userManagementService.getCurrentUserKey());
    }

    @Test
    public void testGetCurrentUserKey_NoCurrentUser() {
        when(userService.getCurrentUser()).thenReturn(null);

        assertNull(userManagementService.getCurrentUserKey());
    }

    @Test
    public void testGetUserKeyByUserID() {
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        when(userDao.getKey(1L)).thenReturn(userKey);

        assertEquals(userKey, userManagementService.getUserKeyByUserID(1L));
    }

    @Test
    public void testSetMainCharacter() {
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        lv.odylab.evemanage.domain.user.User user = new lv.odylab.evemanage.domain.user.User();
        List<CharacterInfo> characterInfos = new ArrayList<CharacterInfo>();
        CharacterInfo characterInfo1 = new CharacterInfo();
        characterInfo1.setId(1L);
        characterInfo1.setName("characterName1");
        CharacterInfo characterInfo2 = new CharacterInfo();
        characterInfo2.setId(2L);
        characterInfo2.setName("characterName2");
        characterInfos.add(characterInfo1);
        characterInfos.add(characterInfo2);
        user.setCharacterInfos(characterInfos);

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.get(new Key<Character>(Character.class, 2L))).thenReturn(new Character());
        userManagementService.setMainCharacter("characterName2", userKey);

        verify(userDao, times(1)).put(any(lv.odylab.evemanage.domain.user.User.class));
        assertEquals(characterInfo2, user.getMainCharacterInfo());
    }

    @Test
    public void testSetMainCharacter_NotExisting() {
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        lv.odylab.evemanage.domain.user.User user = new lv.odylab.evemanage.domain.user.User();
        List<CharacterInfo> characterInfos = new ArrayList<CharacterInfo>();
        CharacterInfo characterInfo1 = new CharacterInfo();
        characterInfo1.setName("characterName1");
        characterInfos.add(characterInfo1);
        user.setCharacterInfos(characterInfos);

        when(userDao.get(userKey)).thenReturn(user);
        userManagementService.setMainCharacter("characterNameX", userKey);

        verify(userDao, never()).put(userCaptor.capture());
        assertNull(user.getMainCharacterInfo());
    }

    @Test
    public void testSetMainCharacter_NoCharacters() {
        Key<lv.odylab.evemanage.domain.user.User> userKey = new Key<lv.odylab.evemanage.domain.user.User>(lv.odylab.evemanage.domain.user.User.class, 1);
        lv.odylab.evemanage.domain.user.User user = new lv.odylab.evemanage.domain.user.User();
        user.setCharacterInfos(new ArrayList<CharacterInfo>());

        when(userDao.get(userKey)).thenReturn(user);
        userManagementService.setMainCharacter("characterName1", userKey);

        verify(userDao, never()).put(userCaptor.capture());
        assertNull(user.getMainCharacterInfo());
    }
}
