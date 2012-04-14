package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;

import java.math.BigDecimal;
import java.util.Map;

public class QuickCalculatorFetchPricesFromEveCentralActionResponse implements Response {
    private Map<Long, BigDecimal> typeIdToPriceMap;

    public Map<Long, BigDecimal> getTypeIdToPriceMap() {
        return typeIdToPriceMap;
    }

    public void setTypeIdToPriceMap(Map<Long, BigDecimal> typeIdToPriceMap) {
        this.typeIdToPriceMap = typeIdToPriceMap;
    }
}