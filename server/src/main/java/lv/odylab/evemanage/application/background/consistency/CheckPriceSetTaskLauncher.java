package lv.odylab.evemanage.application.background.consistency;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class CheckPriceSetTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final PriceSetDao priceSetDao;
    private final String queueName;

    @Inject
    public CheckPriceSetTaskLauncher(GoogleAppEngineServices appEngineServices, PriceSetDao priceSetDao,
                                     @Named("queue.consistencyCheck") String queueName) {
        this.appEngineServices = appEngineServices;
        this.priceSetDao = priceSetDao;
        this.queueName = queueName;
    }

    public void launchForAll() {
        logger.info("Scheduling price set for consistency check");
        Queue queue = appEngineServices.getQueue(queueName);
        Iterable<Key<PriceSet>> priceSetKeys = priceSetDao.getAllKeys();
        for (Key<PriceSet> priceSetKey : priceSetKeys) {
            logger.info("Scheduling price set for consistency check: {}", priceSetKey.getId());
            queue.add(url(TASK_CHECK_PRICE_SET).param("priceSetID", String.valueOf(priceSetKey.getId())));
        }
    }
}
