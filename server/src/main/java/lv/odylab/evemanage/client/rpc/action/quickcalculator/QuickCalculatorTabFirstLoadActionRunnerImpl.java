package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;

import java.util.List;

public class QuickCalculatorTabFirstLoadActionRunnerImpl implements QuickCalculatorTabFirstLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public QuickCalculatorTabFirstLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public QuickCalculatorTabFirstLoadActionResponse execute(QuickCalculatorTabFirstLoadAction action) throws Exception {
        List<RegionDto> regions = clientFacade.getRegions();
        RegionDto preferredRegion = clientFacade.getPreferredRegion();
        List<PriceFetchOptionDto> priceFetchOptions = clientFacade.getPriceFetchOptions();
        PriceFetchOptionDto preferredPriceFetchOption = clientFacade.getPreferredPriceFetchOption();

        QuickCalculatorTabFirstLoadActionResponse response = new QuickCalculatorTabFirstLoadActionResponse();
        response.setRegions(regions);
        response.setPreferredRegion(preferredRegion);
        response.setPriceFetchOptions(priceFetchOptions);
        response.setPreferredPriceFetchOption(preferredPriceFetchOption);
        return response;
    }
}