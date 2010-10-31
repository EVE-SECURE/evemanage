package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;

import java.util.Map;

public class PricingProcessorResult {
    private String totalPrice;
    private Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap;

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Long, CalculationPriceSetItemDto> getTypeIdToCalculationPriceSetItemMap() {
        return typeIdToCalculationPriceSetItemMap;
    }

    public void setTypeIdToCalculationPriceSetItemMap(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap) {
        this.typeIdToCalculationPriceSetItemMap = typeIdToCalculationPriceSetItemMap;
    }
}
