package lv.odylab.evemanage.domain.priceset;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Unindexed
public class PriceSet implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Key<User> user;
    @Indexed
    private String name;
    @Indexed
    private String sharingLevel;
    @Embedded
    private CharacterInfo attachedCharacterInfo;
    @Indexed
    private Date createdDate;
    @Indexed
    private Date updatedDate;
    @Embedded
    private Set<PriceSetItem> items = new HashSet<PriceSetItem>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<User> getUser() {
        return user;
    }

    public void setUser(Key<User> user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(String sharingLevel) {
        this.sharingLevel = sharingLevel;
    }

    public CharacterInfo getAttachedCharacterInfo() {
        return attachedCharacterInfo;
    }

    public void setAttachedCharacterInfo(CharacterInfo attachedCharacterInfo) {
        this.attachedCharacterInfo = attachedCharacterInfo;
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

    public Set<PriceSetItem> getItems() {
        return items;
    }

    public void setItems(Set<PriceSetItem> items) {
        this.items = items;
    }
}
