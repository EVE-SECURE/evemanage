package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;

public class CharacterOrCorporationLevelListBox extends ListBox {
    public CharacterOrCorporationLevelListBox(EveManageMessages messages) {
        // TODO remove constant usage
        addItem(messages.character(), "CHARACTER");
        addItem(messages.corporation(), "CORPORATION");
    }
}
