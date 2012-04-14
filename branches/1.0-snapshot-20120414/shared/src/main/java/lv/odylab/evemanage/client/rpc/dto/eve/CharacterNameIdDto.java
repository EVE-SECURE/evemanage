package lv.odylab.evemanage.client.rpc.dto.eve;

import java.io.Serializable;

public class CharacterNameIdDto implements Serializable {
    private Long characterID;
    private String characterName;

    public Long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(Long characterID) {
        this.characterID = characterID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
