package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.rpc.RationalNumberProductExpression;

import java.math.BigDecimal;

public class QuantityLabel extends Label {
    private RationalNumberProductExpression rationalNumberProductMultiplier;

    public QuantityLabel() {
    }

    public QuantityLabel(Long quantity) {
        setQuantity(quantity);
    }

    public QuantityLabel(Long quantity, RationalNumberProductExpression rationalNumberProductMultiplier) {
        this.rationalNumberProductMultiplier = rationalNumberProductMultiplier;
        setQuantity(quantity);
    }

    public void setQuantity(Long quantity) {
        if (rationalNumberProductMultiplier != null) {
            BigDecimal bigDecimal = rationalNumberProductMultiplier.evaluateAndMultiply(quantity);
            setText("~" + EveNumberFormat.QUANTITY_FORMAT.format(bigDecimal));
            setTitle(quantity + "*" + rationalNumberProductMultiplier.getExpression());
        } else {
            setText(EveNumberFormat.QUANTITY_FORMAT.format(quantity));
        }
    }
}
