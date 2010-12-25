package lv.odylab.evemanage.client.presenter.tab.preferences;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class EditablePreferencesCharacter {
    private Image characterImage;
    private Button deleteButton;

    public Image getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
