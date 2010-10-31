package lv.odylab.evemanage.client.rpc.action.priceset;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetCopyActionResponse implements Response {
    private static final long serialVersionUID = 6670848904771001913L;

    private List<PriceSetNameDto> priceSetNames;
    private PriceSetDto priceSet;
    private Integer currentPriceSetNameIndex;
    private String originalPriceSetName;

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

    public String getOriginalPriceSetName() {
        return originalPriceSetName;
    }

    public void setOriginalPriceSetName(String originalPriceSetName) {
        this.originalPriceSetName = originalPriceSetName;
    }
}