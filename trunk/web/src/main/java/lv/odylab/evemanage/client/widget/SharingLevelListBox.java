package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.shared.eve.SharingLevel;

public class SharingLevelListBox extends ListBox {
    private final EveManageMessages messages;

    public SharingLevelListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    public void addItem(SharingLevel sharingLevel) {
        if (SharingLevel.PERSONAL.equals(sharingLevel)) {
            super.addItem(messages.personal(), sharingLevel.toString());
        } else if (SharingLevel.CORPORATION.equals(sharingLevel)) {
            super.addItem(messages.corporation(), sharingLevel.toString());
        } else if (SharingLevel.ALLIANCE.equals(sharingLevel)) {
            super.addItem(messages.alliance(), sharingLevel.toString());
        } else {
            super.addItem("UNKNOWN");
        }
    }

    public void setSharingLevel(SharingLevel sharingLevel) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getValue(i).equals(sharingLevel.toString())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public SharingLevel getSharingLevel() {
        return SharingLevel.valueOf(getValue(getSelectedIndex()));
    }
}
