package lv.odylab.evemanage.integration.evedb.dto;

import java.io.Serializable;

public class SchematicItemDto implements Serializable {
    private Long requiredTypeID;
    private String requiredTypeName;
    private Long requiredGroupID;
    private String requiredGroupName;
    private String requiredIcon;
    private Long requiredQuantity;
    private Long schematicQuantity;

    public Long getRequiredTypeID() {
        return requiredTypeID;
    }

    public void setRequiredTypeID(Long requiredTypeID) {
        this.requiredTypeID = requiredTypeID;
    }

    public String getRequiredTypeName() {
        return requiredTypeName;
    }

    public void setRequiredTypeName(String requiredTypeName) {
        this.requiredTypeName = requiredTypeName;
    }

    public Long getRequiredGroupID() {
        return requiredGroupID;
    }

    public void setRequiredGroupID(Long requiredGroupID) {
        this.requiredGroupID = requiredGroupID;
    }

    public String getRequiredGroupName() {
        return requiredGroupName;
    }

    public void setRequiredGroupName(String requiredGroupName) {
        this.requiredGroupName = requiredGroupName;
    }

    public String getRequiredIcon() {
        return requiredIcon;
    }

    public void setRequiredIcon(String requiredIcon) {
        this.requiredIcon = requiredIcon;
    }

    public Long getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Long requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Long getSchematicQuantity() {
        return schematicQuantity;
    }

    public void setSchematicQuantity(Long schematicQuantity) {
        this.schematicQuantity = schematicQuantity;
    }
}
