package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ComputableCalculationItem {
    private List<CalculationItemDto> calculationItems = new ArrayList<CalculationItemDto>();
    private CalculationTreeNodeSummary calculationTreeNodeSummary;
    private QuantityLabel quantityLabel;
    private QuantityLabel quantityForParentLabel;
    private PriceLabel priceLabel;
    private PriceLabel totalPriceLabel;
    private QuantityLabel parentQuantityLabel;
    private PriceLabel totalPriceForParentLabel;

    public void setCalculationItems(List<CalculationItemDto> calculationItems) {
        this.calculationItems = calculationItems;
    }

    public CalculationTreeNodeSummary getCalculationTreeNodeSummary() {
        return calculationTreeNodeSummary;
    }

    public void setCalculationTreeNodeSummary(CalculationTreeNodeSummary calculationTreeNodeSummary) {
        this.calculationTreeNodeSummary = calculationTreeNodeSummary;
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
        Long quantity = 0L;
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalPriceForParent = BigDecimal.ZERO;
        CalculationItemDto firstCalculationItem = calculationItems.get(0);
        Long parentQuantity = firstCalculationItem.getParentQuantity();
        BigDecimal price = firstCalculationItem.getPrice();
        for (CalculationItemDto calculationItem : calculationItems) {
            quantity += calculationItem.getQuantity();
            totalPrice = totalPrice.add(calculationItem.getTotalPrice());
            totalPriceForParent = totalPriceForParent.add(calculationItem.getTotalPriceForParent());
        }
        if (quantityLabel != null) {
            quantityLabel.setQuantity(quantity);
        }
        if (quantityForParentLabel != null) {
            quantityForParentLabel.setQuantity(parentQuantity * quantity);
        }
        if (priceLabel != null) {
            priceLabel.setPrice(price);
        }
        if (totalPriceLabel != null) {
            totalPriceLabel.setPrice(totalPrice);
        }
        if (parentQuantityLabel != null) {
            parentQuantityLabel.setQuantity(parentQuantity);
        }
        if (totalPriceForParentLabel != null) {
            totalPriceForParentLabel.setPrice(totalPriceForParent);
        }
    }
}
