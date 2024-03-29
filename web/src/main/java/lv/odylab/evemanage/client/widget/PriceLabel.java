package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

import java.math.BigDecimal;

public class PriceLabel extends Label {

    public PriceLabel(BigDecimal price) {
        setPrice(price);
    }

    public void setPrice(BigDecimal price) {
        double priceDecimal = EveNumberFormat.DECIMAL_FORMAT.parse(price.toPlainString());
        setText(EveNumberFormat.PRICE_FORMAT.format(priceDecimal));
    }
}
