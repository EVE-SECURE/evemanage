package lv.odylab.evemanage.client.rpc.dto.calculation;

import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.RationalNumber;

import java.io.Serializable;
import java.math.BigDecimal;

public class BlueprintItemDto implements Serializable {
    private PathExpression pathExpression;
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
    private RationalNumber correctiveMultiplier;
    private Integer meLevel;
    private Integer peLevel;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceForParent;
    private BigDecimal chance;

    public PathExpression getPathExpression() {
        return pathExpression;
    }

    public void setPathExpression(PathExpression pathExpression) {
        this.pathExpression = pathExpression;
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

    public RationalNumber getCorrectiveMultiplier() {
        return correctiveMultiplier;
    }

    public void setCorrectiveMultiplier(RationalNumber correctiveMultiplier) {
        this.correctiveMultiplier = correctiveMultiplier;
    }

    public Integer getMeLevel() {
        return meLevel;
    }

    public void setMeLevel(Integer meLevel) {
        this.meLevel = meLevel;
    }

    public Integer getPeLevel() {
        return peLevel;
    }

    public void setPeLevel(Integer peLevel) {
        this.peLevel = peLevel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceForParent() {
        return totalPriceForParent;
    }

    public void setTotalPriceForParent(BigDecimal totalPriceForParent) {
        this.totalPriceForParent = totalPriceForParent;
    }

    public BigDecimal getChance() {
        return chance;
    }

    public void setChance(BigDecimal chance) {
        this.chance = chance;
    }
}
