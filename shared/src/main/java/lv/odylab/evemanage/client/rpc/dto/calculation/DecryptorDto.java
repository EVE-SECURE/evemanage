package lv.odylab.evemanage.client.rpc.dto.calculation;

import java.io.Serializable;
import java.math.BigDecimal;

public class DecryptorDto implements Serializable {
    private Long typeID;
    private String typeName;
    private Long categoryID;
    private BigDecimal probabilityMultiplier;
    private Integer maxRunModifier;
    private Integer meModifier;
    private Integer peModifier;

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public BigDecimal getProbabilityMultiplier() {
        return probabilityMultiplier;
    }

    public void setProbabilityMultiplier(BigDecimal probabilityMultiplier) {
        this.probabilityMultiplier = probabilityMultiplier;
    }

    public Integer getMaxRunModifier() {
        return maxRunModifier;
    }

    public void setMaxRunModifier(Integer maxRunModifier) {
        this.maxRunModifier = maxRunModifier;
    }

    public Integer getMeModifier() {
        return meModifier;
    }

    public void setMeModifier(Integer meModifier) {
        this.meModifier = meModifier;
    }

    public Integer getPeModifier() {
        return peModifier;
    }

    public void setPeModifier(Integer peModifier) {
        this.peModifier = peModifier;
    }
}
