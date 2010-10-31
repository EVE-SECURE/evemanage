package lv.odylab.evemanage.application.background.blueprint;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.inject.Inject;
import lv.odylab.appengine.GoogleAppEngineServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

public class AddBlueprintTaskLauncher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices appEngineServices;

    @Inject
    public AddBlueprintTaskLauncher(GoogleAppEngineServices appEngineServices) {
        this.appEngineServices = appEngineServices;
    }

    public void launch(Long userID, Long blueprintTypeID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel) {
        logger.info("Scheduling blueprint add task");
        Queue queue = appEngineServices.getQueue("blueprint-indexing");
        TaskOptions taskOptions = url("/task/addBlueprint").param("userID", String.valueOf(userID))
                .param("blueprintTypeID", String.valueOf(blueprintTypeID))
                .param("itemID", String.valueOf(itemID))
                .param("meLevel", String.valueOf(meLevel))
                .param("peLevel", String.valueOf(peLevel))
                .param("sharingLevel", sharingLevel);
        if (attachedCharacterID != null) {
            taskOptions.param("attachedCharacterID", String.valueOf(attachedCharacterID));
        }
        queue.add(taskOptions);
    }
}
