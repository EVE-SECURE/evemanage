package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageMessages;

public class WasteLabel extends Label {
    private final EveManageMessages messages;

    public WasteLabel(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setWaste(Double waste) {
        super.setText(messages.waste() + ": " + EveNumberFormat.WASTE_FORMAT.format(waste * 100.0) + "%");
    }
}
