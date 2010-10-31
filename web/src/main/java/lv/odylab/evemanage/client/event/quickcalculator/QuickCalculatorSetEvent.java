package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorSetActionResponse;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class QuickCalculatorSetEvent extends QuickCalculatorTabEvent<QuickCalculatorSetEventHandler> {
    public static final Type<QuickCalculatorSetEventHandler> TYPE = new Type<QuickCalculatorSetEventHandler>();

    private CalculationDto calculation;

    public QuickCalculatorSetEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorSetActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.calculation = response.getCalculation();
    }

    @Override
    public Type<QuickCalculatorSetEventHandler> getAssociatedType() {
        return TYPE;
    }

    public CalculationDto getCalculation() {
        return calculation;
    }

    @Override
    protected void dispatch(QuickCalculatorSetEventHandler handler) {
        handler.onQuickCalculatorSet(this);

        trackEvent();
    }
}
