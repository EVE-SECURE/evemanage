package lv.odylab.evemanage.integration.evedb.dto;

import java.io.Serializable;

public class BlueprintTypeDto implements Serializable {
    private Long blueprintTypeID;
    private String blueprintTypeName;
    private Long productTypeID;
    private String productTypeName;
    private Long productCategoryID;
    private String productGraphicIcon;
    private Long parentBlueprintTypeID;
    private String parentBlueprintTypeName;
    private Long parentProductTypeID;
    private String parentProductTypeName;
    private Integer techLevel;
    private Integer productionTime;
    private Integer researchProductivityTime;
    private Integer researchMaterialTime;
    private Integer researchCopyTime;
    private Integer researchTechTime;
    private Integer productivityModifier;
    private Integer wasteFactor;
    private Integer maxProductionLimit;
    private String productVolume;
    private Integer productPortionSize;

    public Long getBlueprintTypeID() {
        return blueprintTypeID;
    }

    public void setBlueprintTypeID(Long blueprintTypeID) {
        this.blueprintTypeID = blueprintTypeID;
    }

    public String getBlueprintTypeName() {
        return blueprintTypeName;
    }

    public void setBlueprintTypeName(String blueprintTypeName) {
        this.blueprintTypeName = blueprintTypeName;
    }

    public Long getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Long productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public Long getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Long productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public String getProductGraphicIcon() {
        return productGraphicIcon;
    }

    public void setProductGraphicIcon(String productGraphicIcon) {
        this.productGraphicIcon = productGraphicIcon;
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

    public Integer getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(Integer techLevel) {
        this.techLevel = techLevel;
    }

    public Integer getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(Integer productionTime) {
        this.productionTime = productionTime;
    }

    public Integer getResearchProductivityTime() {
        return researchProductivityTime;
    }

    public void setResearchProductivityTime(Integer researchProductivityTime) {
        this.researchProductivityTime = researchProductivityTime;
    }

    public Integer getResearchMaterialTime() {
        return researchMaterialTime;
    }

    public void setResearchMaterialTime(Integer researchMaterialTime) {
        this.researchMaterialTime = researchMaterialTime;
    }

    public Integer getResearchCopyTime() {
        return researchCopyTime;
    }

    public void setResearchCopyTime(Integer researchCopyTime) {
        this.researchCopyTime = researchCopyTime;
    }

    public Integer getResearchTechTime() {
        return researchTechTime;
    }

    public void setResearchTechTime(Integer researchTechTime) {
        this.researchTechTime = researchTechTime;
    }

    public Integer getProductivityModifier() {
        return productivityModifier;
    }

    public void setProductivityModifier(Integer productivityModifier) {
        this.productivityModifier = productivityModifier;
    }

    public Integer getWasteFactor() {
        return wasteFactor;
    }

    public void setWasteFactor(Integer wasteFactor) {
        this.wasteFactor = wasteFactor;
    }

    public Integer getMaxProductionLimit() {
        return maxProductionLimit;
    }

    public void setMaxProductionLimit(Integer maxProductionLimit) {
        this.maxProductionLimit = maxProductionLimit;
    }

    public String getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(String productVolume) {
        this.productVolume = productVolume;
    }

    public Integer getProductPortionSize() {
        return productPortionSize;
    }

    public void setProductPortionSize(Integer productPortionSize) {
        this.productPortionSize = productPortionSize;
    }
}
