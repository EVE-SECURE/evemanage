package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;

@RunnedBy(PriceSetSaveActionRunner.class)
public class PriceSetSaveAction implements Action<PriceSetSaveActionResponse> {
    private PriceSetDto priceSet;

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSetDto priceSet) {
        this.priceSet = priceSet;
    }
}