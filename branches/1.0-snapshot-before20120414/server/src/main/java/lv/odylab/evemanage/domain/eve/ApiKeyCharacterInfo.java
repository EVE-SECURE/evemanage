package lv.odylab.evemanage.domain.eve;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class ApiKeyCharacterInfo implements Serializable {
    @Indexed
    private Long characterID;
    @Indexed
    private String name;
    private Long corporationID;
    private String corporationName;

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
}
