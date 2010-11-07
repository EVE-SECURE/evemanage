package lv.odylab.evemanage.application.background.apikey;

import com.google.appengine.api.labs.taskqueue.dev.LocalTaskQueue;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.EveManageObjectifyFactory;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class StartApiKeyUpdateTaskServletIntegrationTest {
    private final GoogleAppEngineServices googleAppEngineServices = new GoogleAppEngineServices();
    private final EveManageObjectifyFactory objectifyFactory = new EveManageObjectifyFactory();
    private final ApiKeyDao apiKeyDao = new ApiKeyDao(objectifyFactory);
    private final UserDao userDao = new UserDao(objectifyFactory);
    private final StartApiKeyUpdateCronServlet startApiKeyUpdateCronServlet = new StartApiKeyUpdateCronServlet(googleAppEngineServices, userDao, "default");

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalTaskQueueTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testManyApiKeys() throws IOException, ServletException {
        for (int i = 0; i < 500; i++) {
            User user = new User();
            userDao.put(user);
            Key<User> userKey = new Key<User>(User.class, user.getId());
            for (int j = 0; j < 10; j++) {
                ApiKey apiKey = new ApiKey();
                apiKey.setUser(userKey);
                apiKeyDao.putWithoutChecks(apiKey);
            }
        }

        startApiKeyUpdateCronServlet.doGet(null, null);
        LocalTaskQueue taskQueue = LocalTaskQueueTestConfig.getLocalTaskQueue();
        assertEquals(500, taskQueue.getQueueStateInfo().get("default").getCountTasks());
    }
}
