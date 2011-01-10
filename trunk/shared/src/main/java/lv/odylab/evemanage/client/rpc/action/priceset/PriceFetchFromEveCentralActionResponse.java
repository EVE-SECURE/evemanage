package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.util.List;

public class PriceFetchFromEveCentralActionResponse implements Response {
    private List<PriceSetItemDto> priceSetItems;

    public List<PriceSetItemDto> getPriceSetItems() {
        return priceSetItems;
    }

    public void setPriceSetItems(List<PriceSetItemDto> priceSetItems) {
        this.priceSetItems = priceSetItems;
    }
}
