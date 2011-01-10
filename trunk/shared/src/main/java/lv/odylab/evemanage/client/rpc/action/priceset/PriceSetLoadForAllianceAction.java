package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PriceSetLoadForAllianceActionRunner.class)
public class PriceSetLoadForAllianceAction implements Action<PriceSetLoadForAllianceActionResponse> {
    private Long priceSetID;

    public Long getPriceSetID() {
        return priceSetID;
    }

    public void setPriceSetID(Long priceSetID) {
        this.priceSetID = priceSetID;
    }
}
