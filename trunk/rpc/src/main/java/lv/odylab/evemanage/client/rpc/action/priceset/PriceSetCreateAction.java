package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PriceSetCreateActionRunner.class)
public class PriceSetCreateAction implements Action<PriceSetCreateActionResponse> {
    private String priceSetName;

    public String getPriceSetName() {
        return priceSetName;
    }

    public void setPriceSetName(String priceSetName) {
        this.priceSetName = priceSetName;
    }
}