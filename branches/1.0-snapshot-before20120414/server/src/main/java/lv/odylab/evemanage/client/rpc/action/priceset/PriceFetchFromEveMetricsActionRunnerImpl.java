package lv.odylab.evemanage.client.rpc.action.priceset;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.NoItemsException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;

import java.util.List;

public class PriceFetchFromEveMetricsActionRunnerImpl implements PriceFetchFromEveMetricsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PriceFetchFromEveMetricsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PriceFetchFromEveMetricsActionResponse execute(PriceFetchFromEveMetricsAction action) throws Exception {
        List<PriceSetItemDto> priceSetItemDtos = action.getPriceSetItems();
        if (priceSetItemDtos.size() == 0) {
            throw new NoItemsException(ErrorCode.NO_ITEMS);
        }
        List<PriceSetItemDto> updatePriceSetItemDtos = clientFacade.fetchPricesFromEveMetrics(priceSetItemDtos);

        PriceFetchFromEveMetricsActionResponse response = new PriceFetchFromEveMetricsActionResponse();
        response.setPriceSetItems(updatePriceSetItemDtos);
        return response;
    }
}
