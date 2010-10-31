package lv.odylab.evemanage.application.background.apikey;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class StartApiKeyUpdateTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final UserDao userDao;
    private final String queueName;

    @Inject
    public StartApiKeyUpdateTaskServlet(GoogleAppEngineServices appEngineServices, UserDao userDao,
                                        @Named("apiKeyUpdate.queue") String queueName) {
        this.appEngineServices = appEngineServices;
        this.userDao = userDao;
        this.queueName = queueName;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Starting api key update tasks");
        Queue queue = appEngineServices.getQueue(queueName);
        Iterable<Key<User>> userKeys = userDao.getAllKeys();
        for (Key<User> userKey : userKeys) {
            logger.info("Scheduling api keys update for userID: {}", userKey.getId());
            queue.add(url("/task/updateApiKey").param("userID", String.valueOf(userKey.getId())));
        }
        logger.info("Finished api key update tasks scheduling");
    }
}
