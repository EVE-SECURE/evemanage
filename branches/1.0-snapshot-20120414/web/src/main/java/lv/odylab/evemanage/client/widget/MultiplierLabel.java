package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

import java.math.BigDecimal;

public class MultiplierLabel extends Label {
    public MultiplierLabel() {
    }

    public void setMultiplier(BigDecimal multiplier) {
        double multiplierDecimal = EveNumberFormat.MULTIPLIER_FORMAT.parse(multiplier.toPlainString());
        setText(EveNumberFormat.MULTIPLIER_FORMAT.format(multiplierDecimal));
    }
}
