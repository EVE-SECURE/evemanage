package lv.odylab.evemanage.client.rpc.action.blueprints;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;

public class BlueprintsImportActionRunnerImpl implements BlueprintsImportActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public BlueprintsImportActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public BlueprintsImportActionResponse execute(BlueprintsImportAction action) throws Exception {
        clientFacade.importBlueprints(action.getImportXml(), action.getAttachedCharacterID(), action.getSharingLevel());
        return new BlueprintsImportActionResponse();
    }
}