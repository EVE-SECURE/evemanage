package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

import java.math.BigDecimal;

public class DamagePerJobLabel extends Label {
    public DamagePerJobLabel(BigDecimal damage) {
        super.setText("(x" + EveNumberFormat.DAMAGE_FORMAT.format(damage) + ")");
    }
}
