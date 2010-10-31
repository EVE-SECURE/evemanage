package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Label;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

public class AttachedCharacterLabel extends Label {
    private final EveManageMessages messages;

    public AttachedCharacterLabel(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setAttachedCharacter(CharacterNameDto characterNameDto) {
        if (characterNameDto == null) {
            setText(messages.none());
        } else {
            setText(characterNameDto.getName());
        }
    }

    public void setAttachedCharacter(CharacterInfoDto characterInfoDto) {
        if (characterInfoDto == null) {
            setText(messages.none());
        } else {
            setText(characterInfoDto.getName());
        }
    }
}
