package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(QuickCalculatorDirectSetActionRunner.class)
public class QuickCalculatorDirectSetAction implements Action<QuickCalculatorDirectSetActionResponse> {
    private CalculationExpression calculationExpression;
    private String historyToken;

    public CalculationExpression getCalculationExpression() {
        return calculationExpression;
    }

    public void setCalculationExpression(CalculationExpression calculationExpression) {
        this.calculationExpression = calculationExpression;
    }

    public String getHistoryToken() {
        return historyToken;
    }

    public void setHistoryToken(String historyToken) {
        this.historyToken = historyToken;
    }
}
