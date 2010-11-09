package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;

import java.math.BigDecimal;
import java.util.Map;

public class PricingProcessorResult {
    private BigDecimal totalPrice;
    private Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Long, CalculationPriceSetItemDto> getTypeIdToCalculationPriceSetItemMap() {
        return typeIdToCalculationPriceSetItemMap;
    }

    public void setTypeIdToCalculationPriceSetItemMap(Map<Long, CalculationPriceSetItemDto> typeIdToCalculationPriceSetItemMap) {
        this.typeIdToCalculationPriceSetItemMap = typeIdToCalculationPriceSetItemMap;
    }
}
