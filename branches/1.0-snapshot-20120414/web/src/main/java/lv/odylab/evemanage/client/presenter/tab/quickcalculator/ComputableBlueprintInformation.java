package lv.odylab.evemanage.client.presenter.tab.quickcalculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;
import lv.odylab.evemanage.shared.EveCalculator;

public class ComputableBlueprintInformation {
    private CalculationDto calculation;
    private PriceLabel totalPriceLabel;
    private WasteLabel wasteLabel;

    public CalculationDto getCalculation() {
        return calculation;
    }

    public void setCalculation(CalculationDto calculation) {
        this.calculation = calculation;
    }

    public void setTotalPriceLabel(PriceLabel totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    public void setWasteLabel(WasteLabel wasteLabel) {
        this.wasteLabel = wasteLabel;
    }

    public void recalculate(EveCalculator calculator) {
        totalPriceLabel.setPrice(calculation.getPrice());
        wasteLabel.setWaste(calculator.calculateWaste(calculation.getMaterialLevel(), calculation.getWasteFactor()));
    }
}