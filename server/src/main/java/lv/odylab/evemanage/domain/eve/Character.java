package lv.odylab.evemanage.domain.eve;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.User;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Unindexed
public class Character implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Key<ApiKey> apiKey;
    @Indexed
    private Key<User> user;
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
    private List<String> corporationTitles;
    @Indexed
    private Long allianceID;
    @Indexed
    private String allianceName;
    @Indexed
    private Date createdDate;
    @Indexed
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<ApiKey> getApiKey() {
        return apiKey;
    }

    public void setApiKey(Key<ApiKey> apiKey) {
        this.apiKey = apiKey;
    }

    public Key<User> getUser() {
        return user;
    }

    public void setUser(Key<User> user) {
        this.user = user;
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

    public List<String> getCorporationTitles() {
        return corporationTitles;
    }

    public void setCorporationTitles(List<String> corporationTitles) {
        this.corporationTitles = corporationTitles;
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
}
