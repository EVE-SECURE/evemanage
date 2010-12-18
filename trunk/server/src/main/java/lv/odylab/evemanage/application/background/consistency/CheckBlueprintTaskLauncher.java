package lv.odylab.evemanage.application.background.consistency;

import com.google.appengine.api.taskqueue.Queue;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

public class CheckBlueprintTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final BlueprintDao blueprintDao;
    private final String queueName;

    @Inject
    public CheckBlueprintTaskLauncher(GoogleAppEngineServices appEngineServices, BlueprintDao blueprintDao,
                                      @Named("queue.consistencyCheck") String queueName) {
        this.appEngineServices = appEngineServices;
        this.blueprintDao = blueprintDao;
        this.queueName = queueName;
    }

    public void launchForAll() {
        logger.info("Scheduling blueprint for consistency check");
        Queue queue = appEngineServices.getQueue(queueName);
        Iterable<Key<Blueprint>> blueprintKeys = blueprintDao.getAllKeys();
        for (Key<Blueprint> blueprintKey : blueprintKeys) {
            logger.info("Scheduling blueprint for consistency check: {}", blueprintKey.getId());
            queue.add(withUrl(TASK_CHECK_BLUEPRINT).param("blueprintID", String.valueOf(blueprintKey.getId())));
        }
    }
}
