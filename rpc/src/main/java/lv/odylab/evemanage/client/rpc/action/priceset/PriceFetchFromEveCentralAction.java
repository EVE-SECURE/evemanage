package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.util.List;

@RunnedBy(PriceFetchFromEveCentralActionRunner.class)
public class PriceFetchFromEveCentralAction implements Action<PriceFetchFromEveCentralActionResponse> {
    private static final long serialVersionUID = -6158234375749054832L;

    private List<PriceSetItemDto> priceSetItems;

    public void setPriceSetItems(List<PriceSetItemDto> priceSetItems) {
        this.priceSetItems = priceSetItems;
    }

    public List<PriceSetItemDto> getPriceSetItems() {
        return priceSetItems;
    }
}
