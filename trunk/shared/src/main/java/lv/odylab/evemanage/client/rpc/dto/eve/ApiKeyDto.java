package lv.odylab.evemanage.client.rpc.dto.eve;

import lv.odylab.evemanage.shared.eve.ApiKeyType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApiKeyDto implements Serializable {
    private Long id;
    private List<ApiKeyCharacterInfoDto> characterInfos;
    private Date lastCheckDate;
    private ApiKeyType keyType;
    private Boolean isValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ApiKeyCharacterInfoDto> getCharacterInfos() {
        return characterInfos;
    }

    public void setCharacterInfos(List<ApiKeyCharacterInfoDto> characterInfos) {
        this.characterInfos = characterInfos;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public ApiKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(ApiKeyType keyType) {
        this.keyType = keyType;
    }

    public Boolean isValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}