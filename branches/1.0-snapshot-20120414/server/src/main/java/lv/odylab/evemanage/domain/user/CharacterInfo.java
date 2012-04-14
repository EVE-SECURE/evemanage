package lv.odylab.evemanage.domain.user;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class CharacterInfo implements Serializable {
    @Indexed
    private Long id;
    @Indexed
    private Long characterID;
    @Indexed
    private String name;
    @Indexed
    private Long corporationID;
    @Indexed
    private String corporationName;
    @Indexed
    private String corporationTicker;
    @Indexed
    private Long allianceID;
    @Indexed
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
