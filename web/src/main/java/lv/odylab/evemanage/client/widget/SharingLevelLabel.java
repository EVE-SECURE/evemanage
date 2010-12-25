package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageMessages;

public class SharingLevelLabel extends Label {
    private final EveManageMessages messages;

    public SharingLevelLabel(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setSharingLevel(String sharingLevel) {
        // TODO remove string constant usage
        if (sharingLevel.equals("PERSONAL")) {
            setText(messages.personal());
        } else if (sharingLevel.equals("CORPORATION")) {
            setText(messages.corporation());
        } else if (sharingLevel.equals("ALLIANCE")) {
            setText(messages.alliance());
        } else {
            setText("UNKNOWN");
        }
    }
}
