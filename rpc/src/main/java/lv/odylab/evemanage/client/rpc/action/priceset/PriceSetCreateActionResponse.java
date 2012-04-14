package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetCreateActionResponse implements Response {
    private List<PriceSetNameDto> priceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;

    public List<PriceSetNameDto> getPriceSetNames() {
        return priceSetNames;
    }

    public void setPriceSetNames(List<PriceSetNameDto> priceSetNames) {
        this.priceSetNames = priceSetNames;
    }

    public PriceSetDto getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSetDto priceSet) {
        this.priceSet = priceSet;
    }

    public Integer getCurrentPriceSetNameIndex() {
        return currentPriceSetNameIndex;
    }

    public void setCurrentPriceSetNameIndex(Integer currentPriceSetNameIndex) {
        this.currentPriceSetNameIndex = currentPriceSetNameIndex;
    }
}