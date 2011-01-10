package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

public class PriceSetLoadForAllianceActionResponse implements Response {
    private PriceSetDto priceSet;

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSetDto priceSet) {
        this.priceSet = priceSet;
    }
}
