package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;

public class AccountCharacterDto implements Serializable {
    private String name;
    private Long characterID;
    private String corporationName;
    private Long corporationID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(Long characterID) {
        this.characterID = characterID;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public Long getCorporationID() {
        return corporationID;
    }

    public void setCorporationID(Long corporationID) {
        this.corporationID = corporationID;
    }
}
