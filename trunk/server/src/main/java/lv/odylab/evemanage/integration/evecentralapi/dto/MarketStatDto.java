package lv.odylab.evemanage.integration.evecentralapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketStatDto implements Serializable {
    private Long typeID;
    private BigDecimal median;
    private BigDecimal buyMedian;
    private BigDecimal sellMedian;

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

    public BigDecimal getBuyMedian() {
        return buyMedian;
    }

    public void setBuyMedian(BigDecimal buyMedian) {
        this.buyMedian = buyMedian;
    }

    public BigDecimal getSellMedian() {
        return sellMedian;
    }

    public void setSellMedian(BigDecimal sellMedian) {
        this.sellMedian = sellMedian;
    }
}
