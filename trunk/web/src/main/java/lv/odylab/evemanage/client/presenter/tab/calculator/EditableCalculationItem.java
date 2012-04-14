package lv.odylab.evemanage.client.presenter.tab.calculator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;

public class EditableCalculationItem {
    private Integer index;
    private OpaqueLoadableBlueprintImage blueprintImage;
    private Label meLabel;
    private Label peLabel;
    private TextBox meTextBox;
    private TextBox peTextBox;
    private Button applyButton;
    private FlexTable calculationItemDetailsTable;
    private FlexTable calculationItemTable;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public OpaqueLoadableBlueprintImage getBlueprintImage() {
        return blueprintImage;
    }

    public void setBlueprintImage(OpaqueLoadableBlueprintImage blueprintImage) {
        this.blueprintImage = blueprintImage;
    }

    public Label getMeLabel() {
        return meLabel;
    }

    public void setMeLabel(Label meLabel) {
        this.meLabel = meLabel;
    }

    public Label getPeLabel() {
        return peLabel;
    }

    public void setPeLabel(Label peLabel) {
        this.peLabel = peLabel;
    }

    public TextBox getMeTextBox() {
        return meTextBox;
    }

    public void setMeTextBox(TextBox meTextBox) {
        this.meTextBox = meTextBox;
    }

    public TextBox getPeTextBox() {
        return peTextBox;
    }

    public void setPeTextBox(TextBox peTextBox) {
        this.peTextBox = peTextBox;
    }

    public Button getApplyButton() {
        return applyButton;
    }

    public void setApplyButton(Button applyButton) {
        this.applyButton = applyButton;
    }

    public FlexTable getCalculationItemDetailsTable() {
        return calculationItemDetailsTable;
    }

    public void setCalculationItemDetailsTable(FlexTable calculationItemDetailsTable) {
        this.calculationItemDetailsTable = calculationItemDetailsTable;
    }

    public FlexTable getCalculationItemTable() {
        return calculationItemTable;
    }

    public void setCalculationItemTable(FlexTable calculationItemTable) {
        this.calculationItemTable = calculationItemTable;
    }
}
