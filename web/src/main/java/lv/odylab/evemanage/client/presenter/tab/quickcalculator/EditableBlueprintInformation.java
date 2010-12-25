package lv.odylab.evemanage.client.presenter.tab.quickcalculator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import lv.odylab.evemanage.client.widget.OpaqueLoadableBlueprintImage;
import lv.odylab.evemanage.client.widget.QuantityLabel;

public class EditableBlueprintInformation {
    private OpaqueLoadableBlueprintImage useAllBlueprintsImage;
    private Label meLabel;
    private Label peLabel;
    private QuantityLabel quantityLabel;
    private TextBox meTextBox;
    private TextBox peTextBox;
    private TextBox quantityTextBox;
    private Button applyButton;

    public OpaqueLoadableBlueprintImage getUseAllBlueprintsImage() {
        return useAllBlueprintsImage;
    }

    public void setUseAllBlueprintsImage(OpaqueLoadableBlueprintImage useAllBlueprintsImage) {
        this.useAllBlueprintsImage = useAllBlueprintsImage;
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

    public QuantityLabel getQuantityLabel() {
        return quantityLabel;
    }

    public void setQuantityLabel(QuantityLabel quantityLabel) {
        this.quantityLabel = quantityLabel;
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

    public TextBox getQuantityTextBox() {
        return quantityTextBox;
    }

    public void setQuantityTextBox(TextBox quantityTextBox) {
        this.quantityTextBox = quantityTextBox;
    }

    public Button getApplyButton() {
        return applyButton;
    }

    public void setApplyButton(Button applyButton) {
        this.applyButton = applyButton;
    }
}
