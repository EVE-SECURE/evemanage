package lv.odylab.evemanage.client.presenter.tab.calculator;

import lv.odylab.evemanage.client.rpc.EveCalculator;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;

public class ComputableCalculation {
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
