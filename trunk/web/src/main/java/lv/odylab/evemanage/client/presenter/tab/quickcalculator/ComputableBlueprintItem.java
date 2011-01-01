package lv.odylab.evemanage.client.presenter.tab.quickcalculator;

import lv.odylab.evemanage.client.rpc.dto.calculation.BlueprintItemDto;
import lv.odylab.evemanage.client.widget.MultiplierLabel;
import lv.odylab.evemanage.client.widget.PriceLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;

public class ComputableBlueprintItem {
    private BlueprintItemDto blueprintItem;
    private QuantityLabel originalRunsLabel;
    private QuantityLabel copyRunsLabel;
    private QuantityLabel copyQuantityLabel;
    private MultiplierLabel copyCorrectiveMultiplierLabel;
    private PriceLabel copyTotalPriceLabel;
    private QuantityLabel inventionRunsLabel;
    private QuantityLabel inventionMeLevelLabel;
    private QuantityLabel inventionPeLevelLabel;
    private MultiplierLabel inventionChanceLabel;

    public BlueprintItemDto getBlueprintItem() {
        return blueprintItem;
    }

    public void setBlueprintItem(BlueprintItemDto blueprintItem) {
        this.blueprintItem = blueprintItem;
    }

    public QuantityLabel getOriginalRunsLabel() {
        return originalRunsLabel;
    }

    public void setOriginalRunsLabel(QuantityLabel originalRunsLabel) {
        this.originalRunsLabel = originalRunsLabel;
    }

    public QuantityLabel getCopyRunsLabel() {
        return copyRunsLabel;
    }

    public void setCopyRunsLabel(QuantityLabel copyRunsLabel) {
        this.copyRunsLabel = copyRunsLabel;
    }

    public QuantityLabel getCopyQuantityLabel() {
        return copyQuantityLabel;
    }

    public void setCopyQuantityLabel(QuantityLabel copyQuantityLabel) {
        this.copyQuantityLabel = copyQuantityLabel;
    }

    public MultiplierLabel getCopyCorrectiveMultiplierLabel() {
        return copyCorrectiveMultiplierLabel;
    }

    public void setCopyCorrectiveMultiplierLabel(MultiplierLabel copyCorrectiveMultiplierLabel) {
        this.copyCorrectiveMultiplierLabel = copyCorrectiveMultiplierLabel;
    }

    public PriceLabel getCopyTotalPriceLabel() {
        return copyTotalPriceLabel;
    }

    public void setCopyTotalPriceLabel(PriceLabel copyTotalPriceLabel) {
        this.copyTotalPriceLabel = copyTotalPriceLabel;
    }

    public QuantityLabel getInventionRunsLabel() {
        return inventionRunsLabel;
    }

    public void setInventionRunsLabel(QuantityLabel inventionRunsLabel) {
        this.inventionRunsLabel = inventionRunsLabel;
    }

    public QuantityLabel getInventionMeLevelLabel() {
        return inventionMeLevelLabel;
    }

    public void setInventionMeLevelLabel(QuantityLabel inventionMeLevelLabel) {
        this.inventionMeLevelLabel = inventionMeLevelLabel;
    }

    public QuantityLabel getInventionPeLevelLabel() {
        return inventionPeLevelLabel;
    }

    public void setInventionPeLevelLabel(QuantityLabel inventionPeLevelLabel) {
        this.inventionPeLevelLabel = inventionPeLevelLabel;
    }

    public MultiplierLabel getInventionChanceLabel() {
        return inventionChanceLabel;
    }

    public void setInventionChanceLabel(MultiplierLabel inventionChanceLabel) {
        this.inventionChanceLabel = inventionChanceLabel;
    }

    public void recalculate() {
        Long runs = blueprintItem.getRuns();
        Long quantity = blueprintItem.getQuantity();
        if (originalRunsLabel != null) {
            originalRunsLabel.setQuantity(runs);
        }
        if (copyRunsLabel != null) {
            copyRunsLabel.setQuantity(runs);
            copyQuantityLabel.setQuantity(quantity);
            copyCorrectiveMultiplierLabel.setMultiplier(blueprintItem.getCorrectiveMultiplier().evaluate());
            copyTotalPriceLabel.setPrice(blueprintItem.getTotalPrice());
        }
    }
}
