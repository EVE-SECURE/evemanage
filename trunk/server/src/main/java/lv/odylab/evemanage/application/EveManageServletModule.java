package lv.odylab.evemanage.application;

import com.google.inject.servlet.ServletModule;
import lv.odylab.evemanage.application.admin.ClearCacheServlet;
import lv.odylab.evemanage.application.background.apikey.StartApiKeyUpdateCronServlet;
import lv.odylab.evemanage.application.background.apikey.UpdateApiKeyTaskServlet;
import lv.odylab.evemanage.application.background.blueprint.AddBlueprintTaskServlet;
import lv.odylab.evemanage.application.background.blueprint.UpdateBlueprintTaskServlet;
import lv.odylab.evemanage.application.background.consistency.CheckBlueprintTaskServlet;
import lv.odylab.evemanage.application.background.consistency.CheckPriceSetTaskServlet;
import lv.odylab.evemanage.application.background.consistency.StartConsistencyCheckServlet;
import lv.odylab.evemanage.application.background.priceset.UpdatePriceSetTaskServlet;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceImpl;

public class EveManageServletModule extends ServletModule implements EveManageServletModuleMapping {

    @Override
    protected void configureServlets() {
        serve(EVEMANAGE_REMOTE_SERVICE).with(EveManageRemoteServiceImpl.class);
        serve(TASK_START_API_KEY_UPDATE).with(StartApiKeyUpdateCronServlet.class);
        serve(TASK_UPDATE_API_KEY).with(UpdateApiKeyTaskServlet.class);
        serve(TASK_ADD_BLUEPRINT).with(AddBlueprintTaskServlet.class);
        serve(TASK_UPDATE_BLUEPRINT).with(UpdateBlueprintTaskServlet.class);
        serve(TASK_UPDATE_PRICE_SET).with(UpdatePriceSetTaskServlet.class);
        serve(TASK_START_CONSISTENCY_CHECK).with(StartConsistencyCheckServlet.class);
        serve(TASK_CHECK_BLUEPRINT).with(CheckBlueprintTaskServlet.class);
        serve(TASK_CHECK_PRICE_SET).with(CheckPriceSetTaskServlet.class);
        serve(ADMIN_CLEAR_CACHE).with(ClearCacheServlet.class);
    }
}
