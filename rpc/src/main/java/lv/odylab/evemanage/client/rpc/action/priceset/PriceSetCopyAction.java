package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PriceSetCopyActionRunner.class)
public class PriceSetCopyAction implements Action<PriceSetCopyActionResponse> {
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