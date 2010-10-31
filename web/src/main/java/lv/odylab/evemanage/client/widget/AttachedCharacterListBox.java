package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.util.List;

public class AttachedCharacterListBox extends ListBox {
    private final EveManageMessages messages;

    public AttachedCharacterListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    public void addItem(CharacterNameDto characterNameDto) {
        addItem(characterNameDto.getName(), String.valueOf(characterNameDto.getId()));
    }

    public void setAttachedCharacter(CharacterNameDto attachedCharacterName) {
        if (attachedCharacterName == null) {
            setSelectedIndex(0);
            return;
        }
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getItemText(i).equals(attachedCharacterName.getName())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public void setAttachedCharacter(CharacterInfoDto attachedCharacterInfo) {
        if (attachedCharacterInfo == null) {
            setSelectedIndex(0);
            return;
        }
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getItemText(i).equals(attachedCharacterInfo.getName())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public void setAttachedCharacterNames(List<CharacterNameDto> attachedCharacterNames) {
        boolean restoreState = getItemCount() > 0;
        String previousState = null;
        if (restoreState) {
            previousState = getValue(getSelectedIndex());
        }
        clear();
        addItem(messages.none(), "-1");
        for (CharacterNameDto attachedCharacterName : attachedCharacterNames) {
            addItem(attachedCharacterName.getName(), String.valueOf(attachedCharacterName.getId()));
        }
        if (restoreState) {
            tryToRestoreState(previousState);
        }
    }

    public void tryToRestoreState(String state) {
        for (int i = 0; i < getItemCount(); i++) {
            if (state.equals(getValue(i))) {
                setSelectedIndex(i);
                return;
            }
        }
        setSelectedIndex(0);
    }

    public CharacterNameDto getAttachedCharacterName() {
        CharacterNameDto attachedCharacterName = new CharacterNameDto();
        if (!"-1".equals(getValue(getSelectedIndex()))) {
            attachedCharacterName.setId(Long.valueOf(getValue(getSelectedIndex())));
        }
        attachedCharacterName.setName(getItemText(getSelectedIndex()));
        return attachedCharacterName;
    }
}
