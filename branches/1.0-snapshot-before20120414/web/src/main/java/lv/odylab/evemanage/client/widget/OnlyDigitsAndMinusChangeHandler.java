package lv.odylab.evemanage.client.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class OnlyDigitsAndMinusChangeHandler implements ChangeHandler {
    private TextBoxBase textBoxBase;
    private Integer maxLength;

    public OnlyDigitsAndMinusChangeHandler(TextBoxBase textBoxBase, Integer maxLength) {
        this.textBoxBase = textBoxBase;
        this.maxLength = maxLength;
    }

    @Override
    public void onChange(ChangeEvent event) {
        String inputString = textBoxBase.getText();
        StringBuilder stringBuilder = new StringBuilder();
        if (inputString.length() > 0 && inputString.charAt(0) == '-') {
            stringBuilder.append("-");
        }
        for (int i = 0; i < inputString.length() && i < maxLength; i++) {
            char character = inputString.charAt(i);
            if (Character.isDigit(character)) {
                stringBuilder.append(character);
            }
        }
        if (stringBuilder.length() == 0) {
            textBoxBase.setText("0");
        } else {
            textBoxBase.setText(stringBuilder.toString());
        }
    }
}
