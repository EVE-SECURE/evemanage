package lv.odylab.evemanage.client.rpc.dto.calculation;

import lv.odylab.evemanage.client.rpc.RationalNumber;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculationPriceItemDto implements Serializable {
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private BigDecimal price;
    private RationalNumber priceMultiplier;
    private Long quantity;
    private RationalNumber quantityMultiplier;
    private BigDecimal damagePerJob;
    private BigDecimal totalPrice;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RationalNumber getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(RationalNumber priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public RationalNumber getQuantityMultiplier() {
        return quantityMultiplier;
    }

    public void setQuantityMultiplier(RationalNumber quantityMultiplier) {
        this.quantityMultiplier = quantityMultiplier;
    }

    public BigDecimal getDamagePerJob() {
        return damagePerJob;
    }

    public void setDamagePerJob(BigDecimal damagePerJob) {
        this.damagePerJob = damagePerJob;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
