package lv.odylab.evemanage.client.rpc.dto.eve;

import java.io.Serializable;

public class RegionDto implements Serializable {
    private Long regionID;
    private String regionName;

    public Long getRegionID() {
        return regionID;
    }

    public void setRegionID(Long regionID) {
        this.regionID = regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
