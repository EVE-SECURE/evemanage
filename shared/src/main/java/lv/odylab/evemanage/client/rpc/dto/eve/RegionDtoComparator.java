package lv.odylab.evemanage.client.rpc.dto.eve;

import java.util.Comparator;

public class RegionDtoComparator implements Comparator<RegionDto> {

    @Override
    public int compare(RegionDto regionDto1, RegionDto regionDto2) {
        return regionDto1.getRegionName().compareTo(regionDto2.getRegionName());
    }
}
