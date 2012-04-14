package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.NoItemsException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.util.List;

public class PriceFetchFromEveCentralActionRunnerImpl implements PriceFetchFromEveCentralActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceFetchFromEveCentralActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceFetchFromEveCentralActionResponse execute(PriceFetchFromEveCentralAction action) throws Exception {
        List<PriceSetItemDto> priceSetItemDtos = action.getPriceSetItems();
        if (priceSetItemDtos.size() == 0) {
            throw new NoItemsException(ErrorCode.NO_ITEMS);
        }
        List<PriceSetItemDto> updatePriceSetItemDtos = clientFacade.fetchPricesFromEveCentral(priceSetItemDtos);

        PriceFetchFromEveCentralActionResponse response = new PriceFetchFromEveCentralActionResponse();
        response.setPriceSetItems(updatePriceSetItemDtos);
        return response;
    }
}
