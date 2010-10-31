package lv.odylab.evemanage.application;

import com.google.inject.servlet.ServletModule;
import lv.odylab.evemanage.application.admin.ClearCacheServlet;
import lv.odylab.evemanage.application.background.apikey.StartApiKeyUpdateTaskServlet;
import lv.odylab.evemanage.application.background.apikey.UpdateApiKeyTaskServlet;
import lv.odylab.evemanage.application.background.blueprint.AddBlueprintTaskServlet;
import lv.odylab.evemanage.client.rpc.EveManageRemoteServiceImpl;

public class EveManageServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        serve("/evemanage/remoteService").with(EveManageRemoteServiceImpl.class);
        serve("/task/startApiKeyUpdate").with(StartApiKeyUpdateTaskServlet.class);
        serve("/task/updateApiKey").with(UpdateApiKeyTaskServlet.class);
        serve("/task/addBlueprint").with(AddBlueprintTaskServlet.class);
        serve("/admin/clearCache").with(ClearCacheServlet.class);
    }
}
