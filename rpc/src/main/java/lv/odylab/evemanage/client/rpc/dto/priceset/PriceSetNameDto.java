package lv.odylab.evemanage.client.rpc.dto.priceset;

import java.io.Serializable;

public class PriceSetNameDto implements Serializable {
    private Long priceSetID;
    private String priceSetName;

    public Long getPriceSetID() {
        return priceSetID;
    }

    public void setPriceSetID(Long priceSetID) {
        this.priceSetID = priceSetID;
    }

    public String getPriceSetName() {
        return priceSetName;
    }

    public void setPriceSetName(String priceSetName) {
        this.priceSetName = priceSetName;
    }
}