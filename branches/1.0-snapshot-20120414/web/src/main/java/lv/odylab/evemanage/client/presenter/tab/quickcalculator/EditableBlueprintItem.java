package lv.odylab.evemanage.client.presenter.tab.quickcalculator;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import lv.odylab.evemanage.client.widget.BlueprintUseButton;
import lv.odylab.evemanage.client.widget.PriceTextBox;

public class EditableBlueprintItem {
    private Integer index;
    private BlueprintUseButton blueprintUseButton;
    private Anchor inventAnchor;
    private Anchor useOriginalAnchor;
    private Anchor useCopyAnchor;
    private PriceTextBox copyPriceTextBox;
    private PriceTextBox inventionPriceTextBox;
    private FlexTable inventionTable;
    private FlexTable inventionBlueprintItemTable;
    private Button useDecryptorButton;
    private FlexTable decryptorTable;
    private Button useBaseItemButton;
    private FlexTable baseItemTable;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public BlueprintUseButton getBlueprintUseButton() {
        return blueprintUseButton;
    }

    public void setBlueprintUseButton(BlueprintUseButton blueprintUseButton) {
        this.blueprintUseButton = blueprintUseButton;
    }

    public Anchor getInventAnchor() {
        return inventAnchor;
    }

    public void setInventAnchor(Anchor inventAnchor) {
        this.inventAnchor = inventAnchor;
    }

    public Anchor getUseOriginalAnchor() {
        return useOriginalAnchor;
    }

    public void setUseOriginalAnchor(Anchor useOriginalAnchor) {
        this.useOriginalAnchor = useOriginalAnchor;
    }

    public Anchor getUseCopyAnchor() {
        return useCopyAnchor;
    }

    public void setUseCopyAnchor(Anchor useCopyAnchor) {
        this.useCopyAnchor = useCopyAnchor;
    }

    public PriceTextBox getCopyPriceTextBox() {
        return copyPriceTextBox;
    }

    public void setCopyPriceTextBox(PriceTextBox copyPriceTextBox) {
        this.copyPriceTextBox = copyPriceTextBox;
    }

    public PriceTextBox getInventionPriceTextBox() {
        return inventionPriceTextBox;
    }

    public void setInventionPriceTextBox(PriceTextBox inventionPriceTextBox) {
        this.inventionPriceTextBox = inventionPriceTextBox;
    }

    public FlexTable getInventionTable() {
        return inventionTable;
    }

    public void setInventionTable(FlexTable inventionTable) {
        this.inventionTable = inventionTable;
    }

    public FlexTable getInventionBlueprintItemTable() {
        return inventionBlueprintItemTable;
    }

    public void setInventionBlueprintItemTable(FlexTable inventionBlueprintItemTable) {
        this.inventionBlueprintItemTable = inventionBlueprintItemTable;
    }

    public Button getUseDecryptorButton() {
        return useDecryptorButton;
    }

    public void setUseDecryptorButton(Button useDecryptorButton) {
        this.useDecryptorButton = useDecryptorButton;
    }

    public FlexTable getDecryptorTable() {
        return decryptorTable;
    }

    public void setDecryptorTable(FlexTable decryptorTable) {
        this.decryptorTable = decryptorTable;
    }

    public Button getUseBaseItemButton() {
        return useBaseItemButton;
    }

    public void setUseBaseItemButton(Button useBaseItemButton) {
        this.useBaseItemButton = useBaseItemButton;
    }

    public FlexTable getBaseItemTable() {
        return baseItemTable;
    }

    public void setBaseItemTable(FlexTable baseItemTable) {
        this.baseItemTable = baseItemTable;
    }
}
