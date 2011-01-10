package lv.odylab.evemanage.client.rpc.dto.calculation;

import java.io.Serializable;
import java.util.List;

public class UsedBlueprintDto implements Serializable {
    private Integer materialLevel;
    private Integer productivityLevel;
    private List<CalculationItemDto> calculationItems;
    private BlueprintItemDto blueprintItem;

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

    public List<CalculationItemDto> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItemDto> calculationItems) {
        this.calculationItems = calculationItems;
    }

    public BlueprintItemDto getBlueprintItem() {
        return blueprintItem;
    }

    public void setBlueprintItem(BlueprintItemDto blueprintItem) {
        this.blueprintItem = blueprintItem;
    }
}
