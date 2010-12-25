package lv.odylab.evemanage.service.calculation;

import lv.odylab.evemanage.domain.calculation.CalculationItem;

import java.io.Serializable;
import java.util.List;

public class UsedSchematic implements Serializable {
    private List<CalculationItem> calculationItems;

    public List<CalculationItem> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItem> calculationItems) {
        this.calculationItems = calculationItems;
    }
}
