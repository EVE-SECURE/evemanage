package lv.odylab.evemanage.client.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

public class DefaultNumberChangeHandler implements ChangeHandler {
    private TextBoxBase textBoxBase;
    private String defaultNumber = "0";

    public DefaultNumberChangeHandler(TextBoxBase textBoxBase) {
        this.textBoxBase = textBoxBase;
    }

    public DefaultNumberChangeHandler(TextBoxBase textBoxBase, String defaultNumber) {
        this.textBoxBase = textBoxBase;
        this.defaultNumber = defaultNumber;
    }

    @Override
    public void onChange(ChangeEvent event) {
        if (textBoxBase.getText().length() == 0) {
            textBoxBase.setText(defaultNumber);
        }
    }
}
