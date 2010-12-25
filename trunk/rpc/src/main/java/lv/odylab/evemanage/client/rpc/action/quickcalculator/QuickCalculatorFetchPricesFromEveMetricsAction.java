package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

import java.util.List;

@RunnedBy(QuickCalculatorFetchPricesFromEveMetricsActionRunner.class)
public class QuickCalculatorFetchPricesFromEveMetricsAction implements Action<QuickCalculatorFetchPricesFromEveMetricsActionResponse> {
    private List<Long> typeIDs;
    private Long preferredRegionID;
    private String preferredPriceFetchOption;

    public List<Long> getTypeIDs() {
        return typeIDs;
    }

    public void setTypeIDs(List<Long> typeIDs) {
        this.typeIDs = typeIDs;
    }

    public Long getPreferredRegionID() {
        return preferredRegionID;
    }

    public void setPreferredRegionID(Long preferredRegionID) {
        this.preferredRegionID = preferredRegionID;
    }

    public String getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    public void setPreferredPriceFetchOption(String preferredPriceFetchOption) {
        this.preferredPriceFetchOption = preferredPriceFetchOption;
    }
}
