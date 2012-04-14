package lv.odylab.evemanage.client.rpc.dto.blueprint;

import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.io.Serializable;

public class BlueprintDto implements Serializable {
    private Long id;
    private Long itemID;
    private Long itemTypeID;
    private String itemTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productCategoryID;
    private String productGraphicIcon;
    private Integer productivityLevel;
    private Integer materialLevel;
    private CharacterInfoDto attachedCharacterInfo;
    private SharingLevel sharingLevel;
    private String createdDate;
    private String updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Long productCategoryID) {
        this.productCategoryID = productCategoryID;
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

    public CharacterInfoDto getAttachedCharacterInfo() {
        return attachedCharacterInfo;
    }

    public void setAttachedCharacterInfo(CharacterInfoDto attachedCharacterInfo) {
        this.attachedCharacterInfo = attachedCharacterInfo;
    }

    public SharingLevel getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(SharingLevel sharingLevel) {
        this.sharingLevel = sharingLevel;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
