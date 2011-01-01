package lv.odylab.evemanage.client.presenter.tab.quickcalculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceItemDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

public class ComputableCalculationPriceSetItem {
    private CalculationPriceItemDto calculationPriceItem;
    private QuantityLabel quantityLabel;
    private PriceLabel totalPriceLabel;

    public CalculationPriceItemDto getCalculationPriceItem() {
        return calculationPriceItem;
    }

    public void setCalculationPriceItem(CalculationPriceItemDto calculationPriceItem) {
        this.calculationPriceItem = calculationPriceItem;
    }

    public void setQuantityLabel(QuantityLabel quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public void setTotalPriceLabel(PriceLabel totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    public void recalculate() {
        quantityLabel.setQuantity(calculationPriceItem.getQuantity(), calculationPriceItem.getQuantityMultiplier());
        totalPriceLabel.setPrice(calculationPriceItem.getTotalPrice());
    }
}
