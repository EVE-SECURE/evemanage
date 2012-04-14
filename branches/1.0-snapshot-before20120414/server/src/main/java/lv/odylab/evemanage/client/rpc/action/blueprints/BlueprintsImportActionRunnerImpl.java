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
        if (action.getOneTimeFullApiKey() != null) {
            clientFacade.importBlueprintsUsingOneTimeFullApiKey(action.getOneTimeFullApiKey(), action.getOneTimeUserID(), action.getOneTimeCharacterID(), action.getOneTimeLevel(), action.getAttachedCharacterID(), action.getSharingLevel());
        } else if (action.getImportCsv() != null) {
            clientFacade.importBlueprintsFromCsv(action.getImportCsv(), action.getAttachedCharacterID(), action.getSharingLevel());
        } else if (action.getFullApiKeyCharacterID() != -1) {
            clientFacade.importBlueprintsUsingFullApiKey(action.getFullApiKeyCharacterID(), action.getFullApiKeyLevel(), action.getAttachedCharacterID(), action.getSharingLevel());
        } else {
            clientFacade.importBlueprintsFromXml(action.getImportXml(), action.getAttachedCharacterID(), action.getSharingLevel());
        }
        return new BlueprintsImportActionResponse();
    }
}