package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;

import java.util.Map;

public class QuickCalculatorFetchPricesFromEveCentralActionResponse implements Response {
    private Map<Long, String> typeIdToPriceMap;

    public Map<Long, String> getTypeIdToPriceMap() {
        return typeIdToPriceMap;
    }

    public void setTypeIdToPriceMap(Map<Long, String> typeIdToPriceMap) {
        this.typeIdToPriceMap = typeIdToPriceMap;
    }
}