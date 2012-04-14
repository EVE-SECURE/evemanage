package lv.odylab.evemanage.client.presenter.tab.calculator.processor;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;

import java.math.BigDecimal;
import java.util.Map;

public class CalculationItemTreeProcessorResult {
    private BigDecimal pricePerUnit;
    private Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap;

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Map<Long, CalculationPriceItemDto> getTypeIdToCalculationPriceSetItemMap() {
        return typeIdToCalculationPriceSetItemMap;
    }

    public void setTypeIdToCalculationPriceSetItemMap(Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap) {
        this.typeIdToCalculationPriceSetItemMap = typeIdToCalculationPriceSetItemMap;
    }
}
