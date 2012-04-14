package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.math.BigDecimal;

public class PriceSetAddItemActionRunnerImpl implements PriceSetAddItemActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceSetAddItemActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceSetAddItemActionResponse execute(PriceSetAddItemAction action) throws Exception {
        String typeName = action.getItemTypeName();
        ItemTypeDto itemTypeDto = clientFacade.getItemTypeByName(typeName);
        if (itemTypeDto == null) {
            throw new InvalidItemTypeException(typeName, ErrorCode.INVALID_ITEM_TYPE);
        }
        PriceSetItemDto priceSetItemDto = new PriceSetItemDto();
        priceSetItemDto.setItemTypeName(itemTypeDto.getName());
        priceSetItemDto.setItemTypeIcon(itemTypeDto.getGraphicIcon());
        priceSetItemDto.setItemTypeID(itemTypeDto.getItemTypeID());
        priceSetItemDto.setItemCategoryID(itemTypeDto.getItemCategoryID());
        priceSetItemDto.setPrice(BigDecimal.ZERO);

        PriceSetAddItemActionResponse response = new PriceSetAddItemActionResponse();
        response.setPriceSetItem(priceSetItemDto);
        return response;
    }
}
