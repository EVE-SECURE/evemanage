package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(PriceSetCreateActionRunner.class)
public class PriceSetCreateAction implements Action<PriceSetCreateActionResponse> {
    private static final long serialVersionUID = -478815097257021792L;

    private String priceSetName;

    public String getPriceSetName() {
        return priceSetName;
    }

    public void setPriceSetName(String priceSetName) {
        this.priceSetName = priceSetName;
    }
}