package lv.odylab.evemanage.client.rpc.action.quickcalculator;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.util.List;

public class QuickCalculatorTabFirstLoadActionResponse implements Response {
    private List<RegionDto> regions;
    private RegionDto preferredRegion;
    private List<PriceFetchOption> priceFetchOptions;
    private PriceFetchOption preferredPriceFetchOption;

    public List<RegionDto> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDto> regions) {
        this.regions = regions;
    }

    public RegionDto getPreferredRegion() {
        return preferredRegion;
    }

    public void setPreferredRegion(RegionDto preferredRegion) {
        this.preferredRegion = preferredRegion;
    }

    public List<PriceFetchOption> getPriceFetchOptions() {
        return priceFetchOptions;
    }

    public void setPriceFetchOptions(List<PriceFetchOption> priceFetchOptions) {
        this.priceFetchOptions = priceFetchOptions;
    }

    public PriceFetchOption getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    public void setPreferredPriceFetchOption(PriceFetchOption preferredPriceFetchOption) {
        this.preferredPriceFetchOption = preferredPriceFetchOption;
    }
}