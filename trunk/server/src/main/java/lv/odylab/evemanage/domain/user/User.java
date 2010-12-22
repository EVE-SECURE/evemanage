package lv.odylab.evemanage.domain.user;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Unindexed
public class User implements Serializable {
    @Id
    private Long id;
    @Embedded
    private CharacterInfo mainCharacterInfo;
    @Indexed
    private List<String> mainCharacterCorporationTitles;
    @Embedded
    private List<CharacterInfo> characterInfos;
    @Indexed
    private String userAuthID;
    private String authDomain;
    @Indexed
    private String email;
    @Indexed
    private String nickname;
    @Indexed
    private Date createdDate;
    @Indexed
    private Date lastLoginDate;
    private Set<String> roles = new HashSet<String>();
    @Embedded
    private List<SkillLevel> skillLevelsForCalculation = new ArrayList<SkillLevel>();
    private Long preferredRegionID;
    private String preferredPriceFetchOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacterInfo getMainCharacterInfo() {
        return mainCharacterInfo;
    }

    public void setMainCharacterInfo(CharacterInfo mainCharacterInfo) {
        this.mainCharacterInfo = mainCharacterInfo;
    }

    public List<String> getMainCharacterCorporationTitles() {
        return mainCharacterCorporationTitles;
    }

    public void setMainCharacterCorporationTitles(List<String> mainCharacterCorporationTitles) {
        this.mainCharacterCorporationTitles = mainCharacterCorporationTitles;
    }

    public List<CharacterInfo> getCharacterInfos() {
        return characterInfos;
    }

    public void setCharacterInfos(List<CharacterInfo> characterInfos) {
        this.characterInfos = characterInfos;
    }

    public String getUserAuthID() {
        return userAuthID;
    }

    public void setUserAuthID(String userAuthID) {
        this.userAuthID = userAuthID;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(String authDomain) {
        this.authDomain = authDomain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<SkillLevel> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }

    public void setSkillLevelsForCalculation(List<SkillLevel> skillLevelsForCalculation) {
        this.skillLevelsForCalculation = skillLevelsForCalculation;
    }

    public Long getPreferredRegionID() {
        return preferredRegionID;
    }

    public void setPreferredRegionID(Long preferredRegionID) {
        this.preferredRegionID = preferredRegionID;
    }

    public String getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    public void setPreferredPriceFetchOption(String preferredPriceFetchOption) {
        this.preferredPriceFetchOption = preferredPriceFetchOption;
    }
}
