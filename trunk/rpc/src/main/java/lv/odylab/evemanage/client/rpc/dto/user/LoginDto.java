package lv.odylab.evemanage.client.rpc.dto.user;

import java.io.Serializable;
import java.util.Set;

public class LoginDto implements Serializable {
    private Long userID;
    private Boolean loggedIn = Boolean.FALSE;
    private String loginUrl;
    private String logoutUrl;
    private String russianUrl;
    private String englishUrl;
    private String emailAddress;
    private String nickname;
    private Boolean isAdmin;
    private Set<String> roles;
    private String bannerMessage;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getRussianUrl() {
        return russianUrl;
    }

    public void setRussianUrl(String russianUrl) {
        this.russianUrl = russianUrl;
    }

    public String getEnglishUrl() {
        return englishUrl;
    }

    public void setEnglishUrl(String englishUrl) {
        this.englishUrl = englishUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getBannerMessage() {
        return bannerMessage;
    }

    public void setBannerMessage(String bannerMessage) {
        this.bannerMessage = bannerMessage;
    }
}
