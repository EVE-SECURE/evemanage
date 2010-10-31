package lv.odylab.evemanage.client.rpc.action.suggest;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;

import java.util.List;

public class SuggestBlueprintTypeActionRunnerImpl implements SuggestBlueprintTypeActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public SuggestBlueprintTypeActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public SuggestBlueprintTypeActionResponse execute(SuggestBlueprintTypeAction action) throws Exception {
        List<ItemTypeDto> queryResult = clientFacade.lookupBlueprintType(action.getQuery());

        SuggestBlueprintTypeActionResponse response = new SuggestBlueprintTypeActionResponse();
        response.setQueryResult(queryResult);
        return response;
    }
}