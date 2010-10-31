package lv.odylab.evemanage.client.rpc.action.suggest;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;

import java.util.List;

public class SuggestTypeActionRunnerImpl implements SuggestTypeActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public SuggestTypeActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public SuggestTypeActionResponse execute(SuggestTypeAction action) throws Exception {
        List<ItemTypeDto> queryResult = clientFacade.lookupType(action.getQuery());

        SuggestTypeActionResponse response = new SuggestTypeActionResponse();
        response.setQueryResult(queryResult);
        return response;
    }
}
