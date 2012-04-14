package lv.odylab.evemanage.integration.evemetricsapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPriceDto implements Serializable {
    private Long typeID;
    private BigDecimal median;

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }

    public BigDecimal getMedian() {
        return median;
    }

    public void setMedian(BigDecimal median) {
        this.median = median;
    }
}
