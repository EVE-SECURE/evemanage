package lv.odylab.evemanage.application.background.consistency;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
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
public class CheckBlueprintTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private BlueprintDao blueprintDao;
    @Mock
    private Queue queue;
    private CheckBlueprintTaskLauncher checkBlueprintTaskLauncher;

    @Before
    public void setUp() {
        checkBlueprintTaskLauncher = new CheckBlueprintTaskLauncher(googleAppEngineServices, blueprintDao, "default");
    }

    @Test
    public void testLaunchForAll() {
        List<Key<Blueprint>> blueprintKeys = new ArrayList<Key<Blueprint>>();
        for (int i = 0; i < 10; i++) {
            blueprintKeys.add(new Key<Blueprint>(Blueprint.class, i));
        }
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        when(blueprintDao.getAllKeys()).thenReturn(blueprintKeys);
        checkBlueprintTaskLauncher.launchForAll();
        verify(queue, times(10)).add(any(TaskOptions.class));
    }
}
