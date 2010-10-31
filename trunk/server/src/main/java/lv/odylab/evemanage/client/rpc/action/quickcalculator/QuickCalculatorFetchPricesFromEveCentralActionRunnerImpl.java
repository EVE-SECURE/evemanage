package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.application.exception.validation.NoItemsException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

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
        Map<Long, String> typeIdToPriceMap = clientFacade.fetchPricesFromEveCentralForTypeIDs(typeIDs);

        QuickCalculatorFetchPricesFromEveCentralActionResponse response = new QuickCalculatorFetchPricesFromEveCentralActionResponse();
        response.setTypeIdToPriceMap(typeIdToPriceMap);
        return response;
    }
}