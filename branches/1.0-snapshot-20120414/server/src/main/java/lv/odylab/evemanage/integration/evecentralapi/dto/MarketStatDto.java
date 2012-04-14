package lv.odylab.evemanage.integration.evecentralapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketStatDto implements Serializable {
    private Long typeID;
    private BigDecimal medianBuySell;
    private BigDecimal medianBuy;
    private BigDecimal medianSell;

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }

    public BigDecimal getMedianBuySell() {
        return medianBuySell;
    }

    public void setMedianBuySell(BigDecimal medianBuySell) {
        this.medianBuySell = medianBuySell;
    }

    public BigDecimal getMedianBuy() {
        return medianBuy;
    }

    public void setMedianBuy(BigDecimal medianBuy) {
        this.medianBuy = medianBuy;
    }

    public BigDecimal getMedianSell() {
        return medianSell;
    }

    public void setMedianSell(BigDecimal medianSell) {
        this.medianSell = medianSell;
    }
}
