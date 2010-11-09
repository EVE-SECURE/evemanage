package lv.odylab.evemanage.client.rpc.dto.calculation;

import lv.odylab.evemanage.client.rpc.PathExpression;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculationItemDto implements Serializable {
    private PathExpression pathExpression;
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private Long quantity;
    private Long parentQuantity;
    private Long perfectQuantity;
    private Integer wasteFactor;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceForParent;

    public PathExpression getPathExpression() {
        return pathExpression;
    }

    public void setPathExpression(PathExpression pathExpression) {
        this.pathExpression = pathExpression;
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
}
