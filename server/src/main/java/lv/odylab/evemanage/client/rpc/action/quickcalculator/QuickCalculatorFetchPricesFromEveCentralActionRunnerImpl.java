package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.NoItemsException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class QuickCalculatorFetchPricesFromEveCentralActionRunnerImpl implements QuickCalculatorFetchPricesFromEveCentralActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorFetchPricesFromEveCentralActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorFetchPricesFromEveCentralActionResponse execute(QuickCalculatorFetchPricesFromEveCentralAction action) throws Exception {
        List<Long> typeIDs = action.getTypeIDs();
        if (typeIDs.size() == 0) {
            throw new NoItemsException(ErrorCode.NO_ITEMS);
        }
        Long regionID = action.getPreferredRegionID();
        PriceFetchOption priceFetchOption = PriceFetchOption.valueOf(action.getPreferredPriceFetchOption());
        Map<Long, BigDecimal> typeIdToPriceMap = clientFacade.fetchPricesFromEveCentralForTypeIDs(typeIDs, regionID, priceFetchOption);

        QuickCalculatorFetchPricesFromEveCentralActionResponse response = new QuickCalculatorFetchPricesFromEveCentralActionResponse();
        response.setTypeIdToPriceMap(typeIdToPriceMap);
        return response;
    }
}