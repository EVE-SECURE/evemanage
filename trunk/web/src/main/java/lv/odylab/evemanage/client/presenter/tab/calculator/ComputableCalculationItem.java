package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

public class ComputableCalculationItem {
    private CalculationItemDto calculationItem;
    private QuantityLabel quantityLabel;
    private QuantityLabel quantityForParentLabel;
    private PriceLabel priceLabel;
    private PriceLabel totalPriceLabel;
    private QuantityLabel parentQuantityLabel;
    private PriceLabel totalPriceForParentLabel;

    public CalculationItemDto getCalculationItem() {
        return calculationItem;
    }

    public void setCalculationItem(CalculationItemDto calculationItem) {
        this.calculationItem = calculationItem;
    }

    public void setQuantityLabel(QuantityLabel quantityLabel) {
        this.quantityLabel = quantityLabel;
    }

    public void setQuantityForParentLabel(QuantityLabel quantityForParentLabel) {
        this.quantityForParentLabel = quantityForParentLabel;
    }

    public void setPriceLabel(PriceLabel priceLabel) {
        this.priceLabel = priceLabel;
    }

    public void setTotalPriceLabel(PriceLabel totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    public void setParentQuantityLabel(QuantityLabel parentQuantityLabel) {
        this.parentQuantityLabel = parentQuantityLabel;
    }

    public void setTotalPriceForParentLabel(PriceLabel totalPriceForParentLabel) {
        this.totalPriceForParentLabel = totalPriceForParentLabel;
    }

    public void recalculate() {
        if (quantityLabel != null) {
            quantityLabel.setQuantity(calculationItem.getQuantity());
        }
        if (quantityForParentLabel != null) {
            quantityForParentLabel.setQuantity(calculationItem.getParentQuantity() * calculationItem.getQuantity());
        }
        if (priceLabel != null) {
            priceLabel.setPrice(calculationItem.getPrice());
        }
        if (totalPriceLabel != null) {
            totalPriceLabel.setPrice(calculationItem.getTotalPrice());
        }
        if (parentQuantityLabel != null) {
            parentQuantityLabel.setQuantity(calculationItem.getParentQuantity());
        }
        if (totalPriceForParentLabel != null) {
            totalPriceForParentLabel.setPrice(calculationItem.getTotalPriceForParent());
        }
    }
}
