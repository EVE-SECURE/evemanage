package lv.odylab.evemanage.application;

public interface EveManageServletModuleMapping {

    String EVEMANAGE_REMOTE_SERVICE = "/evemanage/remoteService";

    String ADMIN_CLEAR_CACHE = "/admin/clearCache";

    String TASK_START_API_KEY_UPDATE = "/task/startApiKeyUpdate";

    String TASK_UPDATE_API_KEY = "/task/updateApiKey";

    String TASK_ADD_BLUEPRINT = "/task/addBlueprint";

    String TASK_UPDATE_BLUEPRINT = "/task/updateBlueprint";

    String TASK_UPDATE_PRICE_SET = "/task/updatePriceSet";

    String TASK_START_CONSISTENCY_CHECK = "/task/startConsistencyCheck";

    String TASK_CHECK_BLUEPRINT = "/task/checkBlueprint";

    String TASK_CHECK_PRICE_SET = "/task/checkPriceSet";

}
