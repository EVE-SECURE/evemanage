package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;

import java.math.BigDecimal;
import java.util.Map;

public class CalculationProcessorResult {
    private CalculationDto calculationDto;
    private BigDecimal totalPrice;
    private BigDecimal pricePerUnit;
    private Map<Long, CalculationPriceItemDto> typeIdToCalculationPriceSetItemMap;

    public CalculationDto getCalculationDto() {
        return calculationDto;
    }

    public void setCalculationDto(CalculationDto calculationDto) {
        this.calculationDto = calculationDto;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

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
