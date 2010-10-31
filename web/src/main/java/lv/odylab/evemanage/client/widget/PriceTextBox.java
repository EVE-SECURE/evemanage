package lv.odylab.evemanage.client.widget;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

import java.util.ArrayList;
import java.util.List;

public class PriceTextBox extends TextBox {
    private double price;
    private List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

    public PriceTextBox() {
        handlerRegistrations.add(addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent event) {
                setText(EveNumberFormat.DECIMAL_FORMAT.format(price));
            }
        }));
        handlerRegistrations.add(addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                setPrice(getText());
                setText(EveNumberFormat.PRICE_FORMAT.format(price));
            }
        }));
        handlerRegistrations.add(addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                char charCode = event.getCharCode();
                if (!(Character.isDigit(charCode) || '.' == charCode)) {
                    cancelKey();
                }
            }
        }));
    }

    public void setPrice(String price) {
        this.price = EveNumberFormat.DECIMAL_FORMAT.parse(price);
        setText(EveNumberFormat.PRICE_FORMAT.format(this.price));
    }

    public String getPrice() {
        return EveNumberFormat.DECIMAL_FORMAT.format(price);
    }

    public List<HandlerRegistration> getHandlerRegistrations() {
        return handlerRegistrations;
    }
}
