package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.shared.RationalNumber;

public class QuantityLabel extends Label {
    private EveManageResources resources;
    private Long quantity;
    private RationalNumber multiplier;

    public QuantityLabel() {
    }

    public QuantityLabel(Long quantity) {
        setQuantity(quantity);
    }

    public QuantityLabel(EveManageResources resources, Long quantity, RationalNumber multiplier) {
        this.resources = resources;
        setQuantity(quantity, multiplier);
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        setText(EveNumberFormat.QUANTITY_FORMAT.format(quantity));
    }

    public void setQuantity(Long quantity, RationalNumber multiplier) {
        this.quantity = quantity;
        this.multiplier = multiplier;
        if (hasMultiplier()) {
            RationalNumber multipliedQuantity = multiplier.multiply(quantity);
            if (multipliedQuantity.hasFraction()) {
                addStyleName(resources.css().rationalNumberQuantityLabel());
                setTitle("=" + quantity + "*" + multiplier.toString());
                setText(EveNumberFormat.QUANTITY_APPROX_FORMAT.format(multipliedQuantity.evaluate()));
            } else {
                removeStyleName(resources.css().rationalNumberQuantityLabel());
                setText(EveNumberFormat.QUANTITY_FORMAT.format(multipliedQuantity.evaluate()));
            }
        } else {
            removeStyleName(resources.css().rationalNumberQuantityLabel());
            setText(EveNumberFormat.QUANTITY_FORMAT.format(quantity));
        }
    }

    public Long getActualQuantity() {
        if (hasMultiplier()) {
            return multiplier.multiply(quantity).evaluateToLong();
        } else {
            return quantity;
        }
    }

    private Boolean hasMultiplier() {
        return multiplier != null && !multiplier.equalOne();
    }
}
