package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;

public class QuantityLabel extends Label {
    public QuantityLabel() {
    }

    public QuantityLabel(Long quantity) {
        super(EveNumberFormat.QUANTITY_FORMAT.format(quantity));
    }

    public void setQuantity(Long quantity) {
        super.setText(EveNumberFormat.QUANTITY_FORMAT.format(quantity));
    }
}
