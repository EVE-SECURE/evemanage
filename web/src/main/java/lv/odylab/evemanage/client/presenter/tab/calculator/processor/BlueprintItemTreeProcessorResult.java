package lv.odylab.evemanage.client.presenter.tab.calculator.processor;

import java.math.BigDecimal;

public class BlueprintItemTreeProcessorResult {
    private BigDecimal pricePerUnit;

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
