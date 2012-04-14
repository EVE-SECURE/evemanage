package lv.odylab.evemanage.client.rpc.dto.eve;

import java.io.Serializable;

public class CharacterInfoDto implements Serializable {
    private Long id;
    private Long characterID;
    private String name;
    private Long corporationID;
    private String corporationName;
    private String corporationTicker;
    private Long allianceID;
    private String allianceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(Long characterID) {
        this.characterID = characterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCorporationID() {
        return corporationID;
    }

    public void setCorporationID(Long corporationID) {
        this.corporationID = corporationID;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getCorporationTicker() {
        return corporationTicker;
    }

    public void setCorporationTicker(String corporationTicker) {
        this.corporationTicker = corporationTicker;
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
