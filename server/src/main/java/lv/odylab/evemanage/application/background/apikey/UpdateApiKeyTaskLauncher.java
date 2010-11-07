package lv.odylab.evemanage.application.background.apikey;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class UpdateApiKeyTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final UserDao userDao;
    private final String queueName;

    @Inject
    public UpdateApiKeyTaskLauncher(GoogleAppEngineServices appEngineServices, UserDao userDao,
                                    @Named("queue.updateApiKey") String queueName) {
        this.appEngineServices = appEngineServices;
        this.userDao = userDao;
        this.queueName = queueName;
    }

    public void launchForAll() {
        logger.info("Scheduling api key update tasks");
        Queue queue = appEngineServices.getQueue(queueName);
        Iterable<Key<User>> userKeys = userDao.getAllKeys();
        for (Key<User> userKey : userKeys) {
            logger.info("Scheduling api keys update for userID: {}", userKey.getId());
            queue.add(url(TASK_UPDATE_API_KEY).param("userID", String.valueOf(userKey.getId())));
        }
    }
}
