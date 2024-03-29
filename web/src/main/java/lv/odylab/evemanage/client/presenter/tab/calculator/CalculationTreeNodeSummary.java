package lv.odylab.evemanage.client.presenter.tab.calculator;

import java.math.BigDecimal;

public class CalculationTreeNodeSummary {
    private String pathNodesString;
    private Long[] pathNodes;
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private Long quantity;
    private Long parentQuantity;
    private BigDecimal damagePerJob;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceForParent;

    public String getPathNodesString() {
        return pathNodesString;
    }

    public void setPathNodesString(String pathNodesString) {
        this.pathNodesString = pathNodesString;
    }

    public Long[] getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(Long[] pathNodes) {
        this.pathNodes = pathNodes;
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

    public BigDecimal getDamagePerJob() {
        return damagePerJob;
    }

    public void setDamagePerJob(BigDecimal damagePerJob) {
        this.damagePerJob = damagePerJob;
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
