package lv.odylab.evemanage.service.calculation;

import lv.odylab.evemanage.domain.calculation.CalculationItem;

import java.io.Serializable;
import java.util.List;

public class UsedBlueprint implements Serializable {
    private Integer materialLevel;
    private Integer productivityLevel;
    private List<CalculationItem> calculationItems;

    public Integer getMaterialLevel() {
        return materialLevel;
    }

    public void setMaterialLevel(Integer materialLevel) {
        this.materialLevel = materialLevel;
    }

    public Integer getProductivityLevel() {
        return productivityLevel;
    }

    public void setProductivityLevel(Integer productivityLevel) {
        this.productivityLevel = productivityLevel;
    }

    public List<CalculationItem> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItem> calculationItems) {
        this.calculationItems = calculationItems;
    }
}
