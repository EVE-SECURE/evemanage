package lv.odylab.evemanage.client.rpc.action.suggest;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;

import java.util.List;

public class SuggestBlueprintTypeActionResponse implements Response {
    private List<ItemTypeDto> queryResult;

    public List<ItemTypeDto> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(List<ItemTypeDto> queryResult) {
        this.queryResult = queryResult;
    }
}