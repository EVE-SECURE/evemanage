package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetLoadAllianceNamesActionResponse implements Response {
    private List<PriceSetNameDto> priceSetNames;

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    public void setPriceSetNames(List<PriceSetNameDto> priceSetNames) {
        this.priceSetNames = priceSetNames;
    }
}
