package lv.odylab.evemanage.integration.evedb.dto;

import java.io.Serializable;

public class TypeMaterialDto implements Serializable {
    private Long materialTypeID;
    private String materialTypeName;
    private Long materialTypeCategoryID;
    private Long quantity;
    private String materialTypeGraphicIcon;

    public Long getMaterialTypeID() {
        return materialTypeID;
    }

    public void setMaterialTypeID(Long materialTypeID) {
        this.materialTypeID = materialTypeID;
    }

    public String getMaterialTypeName() {
        return materialTypeName;
    }

    public void setMaterialTypeName(String materialTypeName) {
        this.materialTypeName = materialTypeName;
    }

    public Long getMaterialTypeCategoryID() {
        return materialTypeCategoryID;
    }

    public void setMaterialTypeCategoryID(Long materialTypeCategoryID) {
        this.materialTypeCategoryID = materialTypeCategoryID;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getMaterialTypeGraphicIcon() {
        return materialTypeGraphicIcon;
    }

    public void setMaterialTypeGraphicIcon(String materialTypeGraphicIcon) {
        this.materialTypeGraphicIcon = materialTypeGraphicIcon;
    }
}
