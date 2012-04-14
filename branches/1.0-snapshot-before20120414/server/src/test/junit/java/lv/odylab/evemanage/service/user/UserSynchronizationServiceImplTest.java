package lv.odylab.evemanage.service.user;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
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
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSynchronizationServiceImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private CharacterDao characterDao;
    private UserSynchronizationService userSynchronizationService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Before
    public void setUp() {
        userSynchronizationService = new UserSynchronizationServiceImpl(userDao, characterDao);
    }

    @Test
    public void testSynchronizeMainCharacter_NoCharacters_NoMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        List<Character> characters = new ArrayList<Character>();

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertNull(capturedUser.getMainCharacterInfo());
        assertNull(capturedUser.getCharacterInfos());
    }

    @Test
    public void testSynchronizeMainCharacter_NoCharacters_WithMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(1L);
        characterInfo.setCharacterID(1L);
        user.setMainCharacterInfo(characterInfo);
        List<Character> characters = new ArrayList<Character>();

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertNull(capturedUser.getMainCharacterInfo());
        assertNull(capturedUser.getCharacterInfos());
    }

    @Test
    public void testSynchronizeMainCharacter_OneCharacter_NoMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        List<Character> characters = new ArrayList<Character>();
        Character character = new Character();
        character.setId(1L);
        character.setCharacterID(1L);
        characters.add(character);

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        when(characterDao.get(new Key<Character>(Character.class, 1))).thenReturn(character);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(Long.valueOf(1), capturedUser.getMainCharacterInfo().getCharacterID());
        assertEquals(1, capturedUser.getCharacterInfos().size());
    }

    @Test
    public void testSynchronizeMainCharacter_TwoCharacters_NoMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        character1.setId(1L);
        character1.setCharacterID(1L);
        Character character2 = new Character();
        character2.setId(2L);
        character2.setCharacterID(2L);
        characters.add(character1);
        characters.add(character2);

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        when(characterDao.get(new Key<Character>(Character.class, 1))).thenReturn(character1);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(Long.valueOf(1), capturedUser.getMainCharacterInfo().getCharacterID());
        assertEquals(2, capturedUser.getCharacterInfos().size());
    }

    @Test
    public void testSynchronizeMainCharacter_TwoCharacters_WithMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(2L);
        characterInfo.setCharacterID(2L);
        user.setMainCharacterInfo(characterInfo);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        character1.setId(1L);
        character1.setCharacterID(1L);
        Character character2 = new Character();
        character2.setId(2L);
        character2.setCharacterID(2L);
        characters.add(character1);
        characters.add(character2);

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        when(characterDao.get(new Key<Character>(Character.class, 2))).thenReturn(character2);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(Long.valueOf(2), capturedUser.getMainCharacterInfo().getCharacterID());
        assertEquals(2, capturedUser.getCharacterInfos().size());
    }

    @Test
    public void testSynchronizeMainCharacter_TwoCharacters_NotExistingMainCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        User user = new User();
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(3L);
        characterInfo.setCharacterID(3L);
        user.setMainCharacterInfo(characterInfo);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        character1.setId(1L);
        character1.setCharacterID(1L);
        Character character2 = new Character();
        character2.setId(2L);
        character2.setCharacterID(2L);
        characters.add(character1);
        characters.add(character2);

        when(userDao.get(userKey)).thenReturn(user);
        when(characterDao.getAll(userKey)).thenReturn(characters);
        when(characterDao.get(new Key<Character>(Character.class, 1))).thenReturn(character1);
        userSynchronizationService.synchronizeMainCharacter(userKey);

        verify(userDao, times(1)).put(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals(Long.valueOf(1), capturedUser.getMainCharacterInfo().getCharacterID());
        assertEquals(2, capturedUser.getCharacterInfos().size());
    }
}
