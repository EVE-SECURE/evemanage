package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

public class PriceLabel extends Label {
    public PriceLabel(String price) {
        setPrice(price);
    }

    public void setPrice(String price) {
        double priceDecimal = EveNumberFormat.DECIMAL_FORMAT.parse(price);
        setText(EveNumberFormat.PRICE_FORMAT.format(priceDecimal));
    }
}
