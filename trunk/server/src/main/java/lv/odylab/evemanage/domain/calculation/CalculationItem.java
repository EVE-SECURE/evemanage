package lv.odylab.evemanage.domain.calculation;

import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class CalculationItem implements Serializable {
    private String path;
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private Long quantity;
    private Long parentQuantity;
    private Long perfectQuantity;
    private Integer wasteFactor;
    private String damagePerJob;
    private String price;
    private String totalPrice;
    private String totalPriceForParent;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Long getPerfectQuantity() {
        return perfectQuantity;
    }

    public void setPerfectQuantity(Long perfectQuantity) {
        this.perfectQuantity = perfectQuantity;
    }

    public Integer getWasteFactor() {
        return wasteFactor;
    }

    public void setWasteFactor(Integer wasteFactor) {
        this.wasteFactor = wasteFactor;
    }

    public String getDamagePerJob() {
        return damagePerJob;
    }

    public void setDamagePerJob(String damagePerJob) {
        this.damagePerJob = damagePerJob;
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
}
