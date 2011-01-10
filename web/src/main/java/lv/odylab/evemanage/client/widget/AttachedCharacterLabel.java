package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;

public class AttachedCharacterLabel extends Label {
    private final EveManageMessages messages;

    public AttachedCharacterLabel(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setAttachedCharacter(CharacterInfoDto characterInfoDto) {
        if (characterInfoDto == null) {
            setText(messages.none());
        } else {
            setText(characterInfoDto.getName());
        }
    }
}
