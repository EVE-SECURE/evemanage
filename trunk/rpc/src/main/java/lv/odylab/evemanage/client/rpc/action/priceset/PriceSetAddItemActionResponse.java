package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

public class PriceSetAddItemActionResponse implements Response {
    private static final long serialVersionUID = 3092110980605067681L;

    private PriceSetItemDto priceSetItem;

    public PriceSetItemDto getPriceSetItem() {
        return priceSetItem;
    }

    public void setPriceSetItem(PriceSetItemDto priceSetItem) {
        this.priceSetItem = priceSetItem;
    }
}
