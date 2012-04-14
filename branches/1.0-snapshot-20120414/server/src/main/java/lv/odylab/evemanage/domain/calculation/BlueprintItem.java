package lv.odylab.evemanage.domain.calculation;

import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class BlueprintItem implements Serializable {
    private String path;
    private String blueprintUse;
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private Long parentBlueprintTypeID;
    private String parentBlueprintTypeName;
    private Long parentProductTypeID;
    private String parentProductTypeName;
    private Long quantity;
    private Long parentQuantity;
    private Integer maxProductionLimit;
    private Long runs;
    private String price;
    private String totalPrice;
    private String totalPriceForParent;
    private String chance;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBlueprintUse() {
        return blueprintUse;
    }

    public void setBlueprintUse(String blueprintUse) {
        this.blueprintUse = blueprintUse;
    }

    public Long getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(Long itemTypeID) {
        this.itemTypeID = itemTypeID;
    }

    public Long getItemCategoryID() {
        return itemCategoryID;
    }

    public void setItemCategoryID(Long itemCategoryID) {
        this.itemCategoryID = itemCategoryID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeIcon() {
        return itemTypeIcon;
    }

    public void setItemTypeIcon(String itemTypeIcon) {
        this.itemTypeIcon = itemTypeIcon;
    }

    public Long getParentBlueprintTypeID() {
        return parentBlueprintTypeID;
    }

    public void setParentBlueprintTypeID(Long parentBlueprintTypeID) {
        this.parentBlueprintTypeID = parentBlueprintTypeID;
    }

    public String getParentBlueprintTypeName() {
        return parentBlueprintTypeName;
    }

    public void setParentBlueprintTypeName(String parentBlueprintTypeName) {
        this.parentBlueprintTypeName = parentBlueprintTypeName;
    }

    public Long getParentProductTypeID() {
        return parentProductTypeID;
    }

    public void setParentProductTypeID(Long parentProductTypeID) {
        this.parentProductTypeID = parentProductTypeID;
    }

    public String getParentProductTypeName() {
        return parentProductTypeName;
    }

    public void setParentProductTypeName(String parentProductTypeName) {
        this.parentProductTypeName = parentProductTypeName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getParentQuantity() {
        return parentQuantity;
    }

    public void setParentQuantity(Long parentQuantity) {
        this.parentQuantity = parentQuantity;
    }

    public Integer getMaxProductionLimit() {
        return maxProductionLimit;
    }

    public void setMaxProductionLimit(Integer maxProductionLimit) {
        this.maxProductionLimit = maxProductionLimit;
    }

    public Long getRuns() {
        return runs;
    }

    public void setRuns(Long runs) {
        this.runs = runs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceForParent() {
        return totalPriceForParent;
    }

    public void setTotalPriceForParent(String totalPriceForParent) {
        this.totalPriceForParent = totalPriceForParent;
    }

    public String getChance() {
        return chance;
    }

    public void setChance(String chance) {
        this.chance = chance;
    }
}
