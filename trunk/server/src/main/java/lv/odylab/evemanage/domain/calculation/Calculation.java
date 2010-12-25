package lv.odylab.evemanage.domain.calculation;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Unindexed
public class Calculation implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Key<User> user;
    @Indexed
    private String name;
    private String price;
    private Long blueprintTypeID;
    private String blueprintTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productTypeCategoryID;
    private String productGraphicIcon;
    private Integer productivityLevel;
    private Integer materialLevel;
    private Integer wasteFactor;
    private Integer maxProductionLimit;
    private String productVolume;
    private Integer productPortionSize;
    @Embedded
    private CharacterInfo attachedCharacterInfo;
    @Indexed
    private String sharingLevel;
    @Indexed
    private Date createdDate;
    @Indexed
    private Date updatedDate;
    @Embedded
    private List<CalculationItem> calculationItems;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getBlueprintTypeID() {
        return blueprintTypeID;
    }

    public void setBlueprintTypeID(Long blueprintTypeID) {
        this.blueprintTypeID = blueprintTypeID;
    }

    public String getBlueprintTypeName() {
        return blueprintTypeName;
    }

    public void setBlueprintTypeName(String blueprintTypeName) {
        this.blueprintTypeName = blueprintTypeName;
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

    public Integer getWasteFactor() {
        return wasteFactor;
    }

    public void setWasteFactor(Integer wasteFactor) {
        this.wasteFactor = wasteFactor;
    }

    public Integer getMaxProductionLimit() {
        return maxProductionLimit;
    }

    public void setMaxProductionLimit(Integer maxProductionLimit) {
        this.maxProductionLimit = maxProductionLimit;
    }

    public String getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(String productVolume) {
        this.productVolume = productVolume;
    }

    public Integer getProductPortionSize() {
        return productPortionSize;
    }

    public void setProductPortionSize(Integer productPortionSize) {
        this.productPortionSize = productPortionSize;
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

    public List<CalculationItem> getCalculationItems() {
        return calculationItems;
    }

    public void setCalculationItems(List<CalculationItem> calculationItems) {
        this.calculationItems = calculationItems;
    }
}
