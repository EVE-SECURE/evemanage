package lv.odylab.evemanage.client.widget;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class OnlyDigitsAndMinusKeyPressHandler implements KeyPressHandler {
    private TextBoxBase textBoxBase;
    private Integer maxLength;

    public OnlyDigitsAndMinusKeyPressHandler(TextBoxBase textBoxBase, Integer maxLength) {
        this.textBoxBase = textBoxBase;
        this.maxLength = maxLength;
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        char charCode = event.getCharCode();
        switch (charCode) {
            case KeyCodes.KEY_LEFT:
            case KeyCodes.KEY_DOWN:
            case KeyCodes.KEY_RIGHT:
            case KeyCodes.KEY_UP:
            case KeyCodes.KEY_BACKSPACE:
            case KeyCodes.KEY_DELETE:
            case KeyCodes.KEY_HOME:
            case KeyCodes.KEY_END:
            case KeyCodes.KEY_TAB:
            case KeyCodes.KEY_ENTER:
                return;
        }
        if (!(Character.isDigit(charCode) || '-' == charCode) || textBoxBase.getText().length() >= maxLength) {
            textBoxBase.cancelKey();
        }
    }
}
