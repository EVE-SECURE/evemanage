package lv.odylab.evemanage.client.rpc.dto.calculation;

import java.io.Serializable;
import java.util.List;

public class UsedSchematicDto implements Serializable {
    private List<CalculationItemDto> calculationItems;

    public List<CalculationItemDto> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItemDto> calculationItems) {
        this.calculationItems = calculationItems;
    }
}
