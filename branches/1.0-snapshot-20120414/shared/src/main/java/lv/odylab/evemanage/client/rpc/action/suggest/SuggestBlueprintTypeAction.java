package lv.odylab.evemanage.client.rpc.action.suggest;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(SuggestBlueprintTypeActionRunner.class)
public class SuggestBlueprintTypeAction implements Action<SuggestBlueprintTypeActionResponse> {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}