package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationPriceSetItemDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

public class ComputableCalculationPriceSetItem {
    private CalculationPriceSetItemDto calculationPriceSetItem;
    private QuantityLabel quantityLabel;
    private PriceLabel totalPriceLabel;

    public CalculationPriceSetItemDto getCalculationPriceSetItem() {
        return calculationPriceSetItem;
    }

    public void setCalculationPriceSetItem(CalculationPriceSetItemDto calculationPriceSetItem) {
        this.calculationPriceSetItem = calculationPriceSetItem;
    }

    public void setQuantityLabel(QuantityLabel quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public void setTotalPriceLabel(PriceLabel totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    public void recalculate() {
        quantityLabel.setQuantity(calculationPriceSetItem.getQuantity());
        totalPriceLabel.setPrice(calculationPriceSetItem.getTotalPrice());
    }
}
