package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

import java.util.ArrayList;
import java.util.List;

public class ComputableCalculationItem {
    private List<CalculationItemDto> calculationItems = new ArrayList<CalculationItemDto>();
    private CalculationItemDto mergedCalculationItem;
    private QuantityLabel quantityLabel;
    private QuantityLabel quantityForParentLabel;
    private PriceLabel priceLabel;
    private PriceLabel totalPriceLabel;
    private QuantityLabel parentQuantityLabel;
    private PriceLabel totalPriceForParentLabel;

    public void setCalculationItems(List<CalculationItemDto> calculationItems) {
        this.calculationItems = calculationItems;
    }

    public CalculationItemDto getMergedCalculationItem() {
        return mergedCalculationItem;
    }

    public void setMergedCalculationItem(CalculationItemDto mergedCalculationItem) {
        this.mergedCalculationItem = mergedCalculationItem;
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

    public void recalculate(EveCalculator calculator) {
        List<Long> sumQuantity = new ArrayList<Long>();
        List<String> sumTotalPrice = new ArrayList<String>();
        List<String> sumTotalPriceForParent = new ArrayList<String>();
        Long parentQuantity = calculationItems.get(0).getParentQuantity();
        String price = calculationItems.get(0).getPrice();
        for (CalculationItemDto calculationItem : calculationItems) {
            sumQuantity.add(calculationItem.getQuantity());
            sumTotalPrice.add(calculationItem.getTotalPrice());
            sumTotalPriceForParent.add(calculationItem.getTotalPriceForParent());
        }
        Long quantity = calculator.sum(sumQuantity);
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
            totalPriceLabel.setPrice(calculator.sum(sumTotalPrice));
        }
        if (parentQuantityLabel != null) {
            parentQuantityLabel.setQuantity(parentQuantity);
        }
        if (totalPriceForParentLabel != null) {
            totalPriceForParentLabel.setPrice(calculator.sum(sumTotalPriceForParent));
        }
    }
}
