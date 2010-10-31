package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;
import java.util.List;

public class CharacterSheetDto implements Serializable {
    private Long characterID;
    private String name;
    private String corporationName;
    private Long corporationID;
    private List<String> corporationTitles;

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

    public List<String> getCorporationTitles() {
        return corporationTitles;
    }

    public void setCorporationTitles(List<String> corporationTitles) {
        this.corporationTitles = corporationTitles;
    }
}
