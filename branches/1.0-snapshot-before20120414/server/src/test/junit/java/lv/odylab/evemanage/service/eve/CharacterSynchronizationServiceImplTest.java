package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.background.blueprint.UpdateBlueprintTaskLauncher;
import lv.odylab.evemanage.application.background.priceset.UpdatePriceSetTaskLauncher;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.user.UserSynchronizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSynchronizationServiceImplTest {
    @Mock
    private UserSynchronizationService userSynchronizationService;
    @Mock
    private UpdateBlueprintTaskLauncher updateBlueprintTaskLauncher;
    @Mock
    private UpdatePriceSetTaskLauncher updatePriceSetTaskLauncher;
    private CharacterSynchronizationService characterSynchronizationService;

    @Before
    public void setUp() {
        characterSynchronizationService = new CharacterSynchronizationServiceImpl(userSynchronizationService, updateBlueprintTaskLauncher, updatePriceSetTaskLauncher);
    }

    @Test
    public void testSynchronizeCreateCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        Character character = new Character();
        characterSynchronizationService.synchronizeCreateCharacter(character, userKey);
        verify(userSynchronizationService).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDeleteCharacter() {
        Key<User> userKey = new Key<User>(User.class, 1);
        characterSynchronizationService.synchronizeDeleteCharacter(2L, userKey);
        verify(updateBlueprintTaskLauncher).launchForDelete(1L, 2L);
        verify(updatePriceSetTaskLauncher).launchForDelete(1L, 2L);
        verify(userSynchronizationService).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeDetachCharacters() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        Character character2 = new Character();
        characters.add(character1);
        characters.add(character2);
        characterSynchronizationService.synchronizeDetachCharacters(characters, userKey);
        verify(updateBlueprintTaskLauncher).launchForDetach(1L, character1);
        verify(updateBlueprintTaskLauncher).launchForDetach(1L, character2);
        verify(updatePriceSetTaskLauncher).launchForDetach(1L, character2);
        verify(updatePriceSetTaskLauncher).launchForDetach(1L, character2);
        verify(userSynchronizationService).synchronizeMainCharacter(userKey);
    }

    @Test
    public void testSynchronizeUpdateCharacters() {
        Key<User> userKey = new Key<User>(User.class, 1);
        List<Character> characters = new ArrayList<Character>();
        Character character1 = new Character();
        Character character2 = new Character();
        characters.add(character1);
        characters.add(character2);
        characterSynchronizationService.synchronizeUpdateCharacters(characters, userKey);
        verify(updateBlueprintTaskLauncher).launchForUpdate(1L, character1);
        verify(updateBlueprintTaskLauncher).launchForUpdate(1L, character2);
        verify(updatePriceSetTaskLauncher).launchForUpdate(1L, character2);
        verify(updatePriceSetTaskLauncher).launchForUpdate(1L, character2);
        verify(userSynchronizationService).synchronizeMainCharacter(userKey);
    }
}
