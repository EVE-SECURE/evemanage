package lv.odylab.evemanage.application.background.apikey;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateApiKeyTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private UserDao userDao;
    @Mock
    private Queue queue;
    private UpdateApiKeyTaskLauncher updateApiKeyTaskLauncher;

    @Before
    public void setUp() {
        updateApiKeyTaskLauncher = new UpdateApiKeyTaskLauncher(googleAppEngineServices, userDao, "default");
    }

    @Test
    public void test() throws IOException, ServletException {
        List<Key<User>> userKeys = new ArrayList<Key<User>>();
        for (int i = 0; i < 10; i++) {
            userKeys.add(new Key<User>(User.class, i));
        }
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        when(userDao.getAllKeys()).thenReturn(userKeys);
        updateApiKeyTaskLauncher.launchForAllUsers();
        verify(queue, times(10)).add(any(TaskOptions.class));
    }
}
