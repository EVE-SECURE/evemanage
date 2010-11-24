package lv.odylab.evemanage.application.background.blueprint;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class AddBlueprintTaskLauncher implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;
    private final String queueName;

    @Inject
    public AddBlueprintTaskLauncher(GoogleAppEngineServices appEngineServices,
                                    @Named("queue.addBlueprint") String queueName) {
        this.appEngineServices = appEngineServices;
        this.queueName = queueName;
    }

    public void launch(Long userID, Long blueprintTypeID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel) {
        logger.info("Scheduling blueprint add task");
        Queue queue = appEngineServices.getQueue(queueName);
        TaskOptions taskOptions = url(TASK_ADD_BLUEPRINT).param("userID", String.valueOf(userID))
                .param("blueprintTypeID", String.valueOf(blueprintTypeID))
                .param("meLevel", String.valueOf(meLevel))
                .param("peLevel", String.valueOf(peLevel))
                .param("sharingLevel", sharingLevel);
        if (itemID != null) {
            taskOptions.param("itemID", String.valueOf(itemID));
        }
        if (attachedCharacterID != null) {
            taskOptions.param("attachedCharacterID", String.valueOf(attachedCharacterID));
        }
        queue.add(taskOptions);
    }

    public void launch(Long userID, String blueprintTypeName, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel) {
        logger.info("Scheduling blueprint add task");
        Queue queue = appEngineServices.getQueue(queueName);
        TaskOptions taskOptions = url(TASK_ADD_BLUEPRINT).param("userID", String.valueOf(userID))
                .param("blueprintTypeName", String.valueOf(blueprintTypeName))
                .param("meLevel", String.valueOf(meLevel))
                .param("peLevel", String.valueOf(peLevel))
                .param("sharingLevel", sharingLevel);
        if (itemID != null) {
            taskOptions.param("itemID", String.valueOf(itemID));
        }
        if (attachedCharacterID != null) {
            taskOptions.param("attachedCharacterID", String.valueOf(attachedCharacterID));
        }
        queue.add(taskOptions);
    }
}
