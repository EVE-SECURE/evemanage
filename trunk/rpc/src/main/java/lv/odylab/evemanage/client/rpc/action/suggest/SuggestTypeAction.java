package lv.odylab.evemanage.client.rpc.action.suggest;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(SuggestTypeActionRunner.class)
public class SuggestTypeAction implements Action<SuggestTypeActionResponse> {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
