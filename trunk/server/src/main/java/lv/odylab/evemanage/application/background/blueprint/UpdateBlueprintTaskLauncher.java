package lv.odylab.evemanage.application.background.blueprint;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class UpdateBlueprintTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final BlueprintDao blueprintDao;
    private final String queueName;

    @Inject
    public UpdateBlueprintTaskLauncher(GoogleAppEngineServices appEngineServices, BlueprintDao blueprintDao,
                                       @Named("queue.updateBlueprint") String queueName) {
        this.appEngineServices = appEngineServices;
        this.blueprintDao = blueprintDao;
        this.queueName = queueName;
    }

    public void launchForUpdate(Long userID, lv.odylab.evemanage.domain.eve.Character character) {
        logger.info("Scheduling blueprint update tasks");
        List<Blueprint> characterBlueprints = blueprintDao.getAll(new Key<User>(User.class, userID), character.getCharacterID());
        for (Blueprint blueprint : characterBlueprints) {
            logger.info("Scheduling blueprint update task for blueprintID: {}, userID: {}", blueprint.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = url(TASK_UPDATE_BLUEPRINT).param("userID", String.valueOf(userID))
                    .param("blueprintID", String.valueOf(blueprint.getId()))
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
        logger.info("Scheduling blueprint update for detach tasks");
        List<Blueprint> characterBlueprints = blueprintDao.getAll(new Key<User>(User.class, userID), character.getCharacterID());
        for (Blueprint blueprint : characterBlueprints) {
            logger.info("Scheduling blueprint update for detach task for blueprintID: {}, userID: {}", blueprint.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = url(TASK_UPDATE_BLUEPRINT).param("userID", String.valueOf(userID))
                    .param("blueprintID", String.valueOf(blueprint.getId()))
                    .param("characterID", String.valueOf(character.getCharacterID()))
                    .param("characterName", character.getName());
            queue.add(taskOptions);
        }
    }

    public void launchForDelete(Long userID, Long characterID) {
        logger.info("Scheduling blueprint update for delete tasks");
        List<Blueprint> characterBlueprints = blueprintDao.getAll(new Key<User>(User.class, userID), characterID);
        for (Blueprint blueprint : characterBlueprints) {
            logger.info("Scheduling blueprint update for delete task for blueprintID: {}, userID: {}", blueprint.getId(), userID);
            Queue queue = appEngineServices.getQueue(queueName);
            TaskOptions taskOptions = url(TASK_UPDATE_BLUEPRINT).param("userID", String.valueOf(userID))
                    .param("blueprintID", String.valueOf(blueprint.getId()));
            queue.add(taskOptions);
        }
    }
}
