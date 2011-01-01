package lv.odylab.evemanage.client.rpc.dto;

import java.io.Serializable;

public class ItemTypeDto implements Serializable {
    private Long itemTypeID;
    private Long itemCategoryID;
    private String name;
    private String graphicIcon;
    private Integer metaLevel;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphicIcon() {
        return graphicIcon;
    }

    public void setGraphicIcon(String graphicIcon) {
        this.graphicIcon = graphicIcon;
    }

    public Integer getMetaLevel() {
        return metaLevel;
    }

    public void setMetaLevel(Integer metaLevel) {
        this.metaLevel = metaLevel;
    }
}
