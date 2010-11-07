package lv.odylab.evemanage.application.background.blueprint;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBlueprintTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private BlueprintDao blueprintDao;
    @Mock
    private Queue queue;
    private UpdateBlueprintTaskLauncher updateBlueprintTaskLauncher;

    @Before
    public void setUp() {
        updateBlueprintTaskLauncher = new UpdateBlueprintTaskLauncher(googleAppEngineServices, blueprintDao, "default");
    }

    @Test
    public void testLaunchForDelete() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        Blueprint blueprint1 = new Blueprint();
        Blueprint blueprint2 = new Blueprint();
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);
        when(blueprintDao.getAll(new Key<User>(User.class, 1L), 2L)).thenReturn(blueprints);
        updateBlueprintTaskLauncher.launchForDelete(1L, 2L);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForDetach() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        Blueprint blueprint1 = new Blueprint();
        blueprint1.setId(2L);
        Blueprint blueprint2 = new Blueprint();
        blueprint2.setId(3L);
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);
        when(blueprintDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(blueprints);
        updateBlueprintTaskLauncher.launchForDetach(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        character.setCorporationID(2L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        character.setAllianceID(3L);
        character.setAllianceName("allianceName");
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        Blueprint blueprint1 = new Blueprint();
        blueprint1.setId(4L);
        Blueprint blueprint2 = new Blueprint();
        blueprint2.setId(5L);
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);
        when(blueprintDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(blueprints);
        updateBlueprintTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate_NoCorporation() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        Blueprint blueprint1 = new Blueprint();
        blueprint1.setId(4L);
        Blueprint blueprint2 = new Blueprint();
        blueprint2.setId(5L);
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);
        when(blueprintDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(blueprints);
        updateBlueprintTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate_NoAlliance() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        character.setCorporationID(2L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        Blueprint blueprint1 = new Blueprint();
        blueprint1.setId(4L);
        Blueprint blueprint2 = new Blueprint();
        blueprint2.setId(5L);
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);
        when(blueprintDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(blueprints);
        updateBlueprintTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }
}
