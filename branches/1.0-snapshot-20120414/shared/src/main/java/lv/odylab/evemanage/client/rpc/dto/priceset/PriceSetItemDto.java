package lv.odylab.evemanage.client.rpc.dto.priceset;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceSetItemDto implements Serializable {
    private Long itemTypeID;
    private Long itemCategoryID;
    private String itemTypeName;
    private String itemTypeIcon;
    private BigDecimal price;

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
}