package lv.odylab.evemanage.service.priceset;

import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.util.Comparator;

public class PriceSetItemDtoComparator implements Comparator<PriceSetItemDto> {

    @Override
    public int compare(PriceSetItemDto priceSetItemDto1, PriceSetItemDto priceSetItemDto2) {
        return priceSetItemDto1.getItemTypeID().compareTo(priceSetItemDto2.getItemTypeID());
    }
}
