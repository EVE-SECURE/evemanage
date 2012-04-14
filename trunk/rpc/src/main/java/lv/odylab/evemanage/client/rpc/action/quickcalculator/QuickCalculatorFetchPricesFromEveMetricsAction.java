package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

import java.util.List;

@RunnedBy(QuickCalculatorFetchPricesFromEveMetricsActionRunner.class)
public class QuickCalculatorFetchPricesFromEveMetricsAction implements Action<QuickCalculatorFetchPricesFromEveMetricsActionResponse> {
    private List<Long> typeIDs;

    public List<Long> getTypeIDs() {
        return typeIDs;
    }

    public void setTypeIDs(List<Long> typeIDs) {
        this.typeIDs = typeIDs;
    }
}
