package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.shared.eve.SharingLevel;

public class SharingLevelLabel extends Label {
    private final EveManageMessages messages;

    public SharingLevelLabel(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setSharingLevel(SharingLevel sharingLevel) {
        if (SharingLevel.PERSONAL.equals(sharingLevel)) {
            setText(messages.personal());
        } else if (SharingLevel.CORPORATION.equals(sharingLevel)) {
            setText(messages.corporation());
        } else if (SharingLevel.ALLIANCE.equals(sharingLevel)) {
            setText(messages.alliance());
        } else {
            setText("UNKNOWN");
        }
    }
}
