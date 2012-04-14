package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PriceSetLoadActionRunner.class)
public class PriceSetLoadAction implements Action<PriceSetLoadActionResponse> {
    private Long priceSetID;

    public Long getPriceSetID() {
        return priceSetID;
    }

    public void setPriceSetID(Long priceSetID) {
        this.priceSetID = priceSetID;
    }
}
