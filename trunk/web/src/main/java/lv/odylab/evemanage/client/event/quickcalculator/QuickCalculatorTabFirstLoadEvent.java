package lv.odylab.evemanage.client.event.quickcalculator;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.quickcalculator.QuickCalculatorTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class QuickCalculatorTabFirstLoadEvent extends QuickCalculatorTabEvent<QuickCalculatorTabFirstLoadEventHandler> {
    public static final Type<QuickCalculatorTabFirstLoadEventHandler> TYPE = new Type<QuickCalculatorTabFirstLoadEventHandler>();
    private List<RegionDto> regions;
    private RegionDto preferredRegion;
    private List<PriceFetchOptionDto> priceFetchOptions;
    private PriceFetchOptionDto preferredPriceFetchOption;

    public QuickCalculatorTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, QuickCalculatorTabFirstLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.regions = response.getRegions();
        this.preferredRegion = response.getPreferredRegion();
        this.priceFetchOptions = response.getPriceFetchOptions();
        this.preferredPriceFetchOption = response.getPreferredPriceFetchOption();
    }

    @Override
    public Type<QuickCalculatorTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<RegionDto> getRegions() {
        return regions;
    }

    public RegionDto getPreferredRegion() {
        return preferredRegion;
    }

    public List<PriceFetchOptionDto> getPriceFetchOptions() {
        return priceFetchOptions;
    }

    public PriceFetchOptionDto getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    @Override
    protected void dispatch(QuickCalculatorTabFirstLoadEventHandler handler) {
        handler.onQuickCalculatorTabFirstLoad(this);

        trackEvent();
    }
}
