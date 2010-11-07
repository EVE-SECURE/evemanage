package lv.odylab.evemanage.application.background.blueprint;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import lv.odylab.appengine.GoogleAppEngineServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddBlueprintTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private Queue queue;
    private AddBlueprintTaskLauncher addBlueprintTaskLauncher;

    @Before
    public void setUp() {
        addBlueprintTaskLauncher = new AddBlueprintTaskLauncher(googleAppEngineServices, "default");
    }

    @Test
    public void testLaunch() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        addBlueprintTaskLauncher.launch(1L, 2L, 3L, 4, 5, 6L, "PERSONAL");
        addBlueprintTaskLauncher.launch(2L, 3L, 4L, 5, 6, 7L, "CORPORATION");
        verify(queue, times(2)).add(any(TaskOptions.class));
    }
}
