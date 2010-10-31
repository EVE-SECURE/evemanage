package lv.odylab.evemanage.domain.eve;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.User;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Unindexed
public class ApiKey implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long apiKeyUserID;
    private String encodedApiKeyString;
    @Indexed
    private String apiKeyHash;
    @Indexed
    private Key<User> user;
    @Embedded
    private List<ApiKeyCharacterInfo> characterInfos = new ArrayList<ApiKeyCharacterInfo>();
    @Indexed
    private Date createdDate;
    @Indexed
    private Date updatedDate;
    @Indexed
    private Date lastCheckDate;
    @Indexed
    private Boolean isValid;
    @Indexed
    private String keyType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApiKeyUserID() {
        return apiKeyUserID;
    }

    public void setApiKeyUserID(Long apiKeyUserID) {
        this.apiKeyUserID = apiKeyUserID;
    }

    public String getEncodedApiKeyString() {
        return encodedApiKeyString;
    }

    public void setEncodedApiKeyString(String encodedApiKeyString) {
        this.encodedApiKeyString = encodedApiKeyString;
    }

    public String getApiKeyHash() {
        return apiKeyHash;
    }

    public void setApiKeyHash(String apiKeyHash) {
        this.apiKeyHash = apiKeyHash;
    }

    public Key<User> getUser() {
        return user;
    }

    public void setUser(Key<User> user) {
        this.user = user;
    }

    public List<ApiKeyCharacterInfo> getCharacterInfos() {
        return characterInfos;
    }

    public void setCharacterInfos(List<ApiKeyCharacterInfo> characterInfos) {
        this.characterInfos = characterInfos;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public Boolean isValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
}
