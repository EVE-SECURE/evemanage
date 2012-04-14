package lv.odylab.evemanage.application.background.priceset;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

public class UpdatePriceSetTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final PriceSetDao priceSetDao;
    private final String queueName;

    @Inject
    public UpdatePriceSetTaskLauncher(GoogleAppEngineServices appEngineServices, PriceSetDao priceSetDao,
                                      @Named("queue.updatePriceSet") String queueName) {
        this.appEngineServices = appEngineServices;
        this.priceSetDao = priceSetDao;
        this.queueName = queueName;
    }

    public void launchForUpdate(Long userID, Character character) {
        logger.info("Scheduling price set update tasks");
        List<PriceSet> characterPriceSets = priceSetDao.getAll(new Key<User>(User.class, userID), character.getCharacterID());
        for (PriceSet priceSet : characterPriceSets) {
            logger.info("Scheduling price set update task for priceSetID: {}, userID: {}", priceSet.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = withUrl(TASK_UPDATE_PRICE_SET).param("userID", String.valueOf(userID))
                    .param("priceSetID", String.valueOf(priceSet.getId()))
                    .param("characterID", String.valueOf(character.getCharacterID()))
                    .param("characterName", character.getName());
            if (character.getCorporationID() != null) {
                taskOptions.param("corporationID", String.valueOf(character.getCorporationID()))
                        .param("corporationName", character.getCorporationName())
                        .param("corporationTicker", character.getCorporationTicker());
            }
            if (character.getAllianceID() != null) {
                taskOptions.param("allianceID", String.valueOf(character.getAllianceID()))
                        .param("allianceName", character.getAllianceName());
            }
            queue.add(taskOptions);
        }
    }

    public void launchForDetach(Long userID, Character character) {
        logger.info("Scheduling price set update for detach tasks");
        List<PriceSet> characterPriceSets = priceSetDao.getAll(new Key<User>(User.class, userID), character.getCharacterID());
        for (PriceSet priceSet : characterPriceSets) {
            logger.info("Scheduling price set update for detach task for priceSetID: {}, userID: {}", priceSet.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = withUrl(TASK_UPDATE_PRICE_SET).param("userID", String.valueOf(userID))
                    .param("priceSetID", String.valueOf(priceSet.getId()))
                    .param("characterID", String.valueOf(character.getCharacterID()))
                    .param("characterName", character.getName());
            queue.add(taskOptions);
        }
    }

    public void launchForDelete(Long userID, Long characterID) {
        logger.info("Scheduling price set update for delete tasks");
        List<PriceSet> characterPriceSets = priceSetDao.getAll(new Key<User>(User.class, userID), characterID);
        for (PriceSet priceSet : characterPriceSets) {
            logger.info("Scheduling price set update for delete task for priceSetID: {}, userID: {}", priceSet.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = withUrl(TASK_UPDATE_PRICE_SET).param("userID", String.valueOf(userID))
                    .param("priceSetID", String.valueOf(priceSet.getId()));
            queue.add(taskOptions);
        }
    }
}
