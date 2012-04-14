package lv.odylab.evemanage.domain.blueprint;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Unindexed
public class Blueprint implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Key<User> user;
    private Long itemID;
    @Indexed
    private Long itemTypeID;
    @Indexed
    private String itemTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productTypeCategoryID;
    private String productGraphicIcon;
    @Indexed
    private Integer productivityLevel;
    @Indexed
    private Integer materialLevel;
    @Embedded
    private CharacterInfo attachedCharacterInfo;
    @Indexed
    private String sharingLevel;
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

    public Key<User> getUser() {
        return user;
    }

    public void setUser(Key<User> user) {
        this.user = user;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public Long getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(Long itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Long getProductTypeCategoryID() {
        return productTypeCategoryID;
    }

    public void setProductTypeCategoryID(Long productTypeCategoryID) {
        this.productTypeCategoryID = productTypeCategoryID;
    }

    public String getProductGraphicIcon() {
        return productGraphicIcon;
    }

    public void setProductGraphicIcon(String productGraphicIcon) {
        this.productGraphicIcon = productGraphicIcon;
    }

    public Integer getProductivityLevel() {
        return productivityLevel;
    }

    public void setProductivityLevel(Integer productivityLevel) {
        this.productivityLevel = productivityLevel;
    }

    public Integer getMaterialLevel() {
        return materialLevel;
    }

    public void setMaterialLevel(Integer materialLevel) {
        this.materialLevel = materialLevel;
    }

    public CharacterInfo getAttachedCharacterInfo() {
        return attachedCharacterInfo;
    }

    public void setAttachedCharacterInfo(CharacterInfo attachedCharacterInfo) {
        this.attachedCharacterInfo = attachedCharacterInfo;
    }

    public String getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(String sharingLevel) {
        this.sharingLevel = sharingLevel;
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
