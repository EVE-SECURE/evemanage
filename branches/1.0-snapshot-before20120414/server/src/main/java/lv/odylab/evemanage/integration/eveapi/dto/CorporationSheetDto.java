package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;

public class CorporationSheetDto implements Serializable {
    private String ticker;
    private Long allianceID;
    private String allianceName;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Long getAllianceID() {
        return allianceID;
    }

    public void setAllianceID(Long allianceID) {
        this.allianceID = allianceID;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }
}
