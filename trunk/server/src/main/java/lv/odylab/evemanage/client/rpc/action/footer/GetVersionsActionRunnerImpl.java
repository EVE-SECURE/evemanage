package lv.odylab.evemanage.client.rpc.action.footer;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;

public class GetVersionsActionRunnerImpl implements GetVersionsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public GetVersionsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public GetVersionsActionResponse execute(GetVersionsAction action) throws Exception {
        String eveManageVersion = clientFacade.getEveManageVersion();
        String eveDbVersion = clientFacade.getEveDbVersion();

        GetVersionsActionResponse response = new GetVersionsActionResponse();
        response.setEveManageVersion(eveManageVersion);
        response.setEveDbVersion(eveDbVersion);
        return response;
    }
}
