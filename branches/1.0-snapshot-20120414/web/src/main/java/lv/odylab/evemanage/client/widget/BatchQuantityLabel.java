package lv.odylab.evemanage.client.widget;

public class BatchQuantityLabel extends QuantityLabel {
    private Integer batchSize = 1;

    public BatchQuantityLabel(Long quantity, Integer batchSize) {
        this.batchSize = batchSize;
        setQuantity(quantity);
    }

    @Override
    public void setQuantity(Long quantity) {
        super.setText(EveNumberFormat.QUANTITY_FORMAT.format(quantity * batchSize));
    }
}
