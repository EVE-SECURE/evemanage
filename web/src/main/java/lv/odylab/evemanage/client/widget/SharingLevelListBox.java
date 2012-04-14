package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;

public class SharingLevelListBox extends ListBox {
    private final EveManageMessages messages;

    public SharingLevelListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    @Override
    public void addItem(String item) {
        if (item.equals("PERSONAL")) {
            super.addItem(messages.personal(), item);
        } else if (item.equals("CORPORATION")) {
            super.addItem(messages.corporation(), item);
        } else if (item.equals("ALLIANCE")) {
            super.addItem(messages.alliance(), item);
        } else {
            super.addItem("UNKNOWN");
        }
    }

    public void setSharingLevel(String sharingLevel) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getValue(i).equals(sharingLevel)) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public String getSharingLevel() {
        return getValue(getSelectedIndex());
    }
}
