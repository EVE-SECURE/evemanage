package lv.odylab.evemanage.client.presenter.tab.blueprint;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import lv.odylab.evemanage.client.widget.AttachedCharacterListBox;
import lv.odylab.evemanage.client.widget.SharingLevelListBox;

public class EditableBlueprintDetails {
    private Image spinnerImage;
    private Label meLevelLabel;
    private Label peLevelLabel;
    private TextBox meLevelTextBox;
    private TextBox peLevelTextBox;
    private AttachedCharacterListBox attachedCharacterListBox;
    private SharingLevelListBox sharingLevelListBox;
    private TextBox itemIdTextBox;
    private Button saveButton;
    private Button detailsButton;
    private Button deleteButton;
    private FlexTable detailsTable;

    public Image getSpinnerImage() {
        return spinnerImage;
    }

    public void setSpinnerImage(Image spinnerImage) {
        this.spinnerImage = spinnerImage;
    }

    public Label getMeLevelLabel() {
        return meLevelLabel;
    }

    public void setMeLevelLabel(Label meLevelLabel) {
        this.meLevelLabel = meLevelLabel;
    }

    public Label getPeLevelLabel() {
        return peLevelLabel;
    }

    public void setPeLevelLabel(Label peLevelLabel) {
        this.peLevelLabel = peLevelLabel;
    }

    public TextBox getMeLevelTextBox() {
        return meLevelTextBox;
    }

    public void setMeLevelTextBox(TextBox meLevelTextBox) {
        this.meLevelTextBox = meLevelTextBox;
    }

    public TextBox getPeLevelTextBox() {
        return peLevelTextBox;
    }

    public void setPeLevelTextBox(TextBox peLevelTextBox) {
        this.peLevelTextBox = peLevelTextBox;
    }

    public AttachedCharacterListBox getAttachedCharacterListBox() {
        return attachedCharacterListBox;
    }

    public void setAttachedCharacterListBox(AttachedCharacterListBox attachedCharacterListBox) {
        this.attachedCharacterListBox = attachedCharacterListBox;
    }

    public SharingLevelListBox getSharingLevelListBox() {
        return sharingLevelListBox;
    }

    public void setSharingLevelListBox(SharingLevelListBox sharingLevelListBox) {
        this.sharingLevelListBox = sharingLevelListBox;
    }

    public TextBox getItemIdTextBox() {
        return itemIdTextBox;
    }

    public void setItemIdTextBox(TextBox itemIdTextBox) {
        this.itemIdTextBox = itemIdTextBox;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getDetailsButton() {
        return detailsButton;
    }

    public void setDetailsButton(Button detailsButton) {
        this.detailsButton = detailsButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public FlexTable getDetailsTable() {
        return detailsTable;
    }

    public void setDetailsTable(FlexTable detailsTable) {
        this.detailsTable = detailsTable;
    }
}
