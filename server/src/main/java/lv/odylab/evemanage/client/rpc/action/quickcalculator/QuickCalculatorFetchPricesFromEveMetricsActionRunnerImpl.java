package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.NoItemsException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class QuickCalculatorFetchPricesFromEveMetricsActionRunnerImpl implements QuickCalculatorFetchPricesFromEveMetricsActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorFetchPricesFromEveMetricsActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorFetchPricesFromEveMetricsActionResponse execute(QuickCalculatorFetchPricesFromEveMetricsAction action) throws Exception {
        List<Long> typeIDs = action.getTypeIDs();
        if (typeIDs.size() == 0) {
            throw new NoItemsException(ErrorCode.NO_ITEMS);
        }
        Map<Long, BigDecimal> typeIdToPriceMap = clientFacade.fetchPricesFromEveMetricsForTypeIDs(typeIDs);

        QuickCalculatorFetchPricesFromEveMetricsActionResponse response = new QuickCalculatorFetchPricesFromEveMetricsActionResponse();
        response.setTypeIdToPriceMap(typeIdToPriceMap);
        return response;
    }
}