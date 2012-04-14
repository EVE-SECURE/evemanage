package lv.odylab.evemanage.client.rpc.dto.blueprint;

import java.io.Serializable;
import java.util.List;

public class BlueprintDetailsDto implements Serializable {
    private Long blueprintTypeID;
    private String blueprintTypeName;
    private Integer techLevel;
    private Integer productionTime;
    private Integer researchProductivityTime;
    private Integer researchMaterialTime;
    private Integer researchCopyTime;
    private Integer researchTechTime;
    private Integer productivityModifier;
    private Integer wasteFactor;
    private List<MaterialDto> materials;
    private List<RequirementDto> manufacturingRequirements;
    private List<RequirementDto> timeProductivityRequirements;
    private List<RequirementDto> materialProductivityRequirements;
    private List<RequirementDto> copyingRequirements;
    private List<RequirementDto> reverseEngineeringRequirements;
    private List<RequirementDto> inventionRequirements;

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

    public List<MaterialDto> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialDto> materials) {
        this.materials = materials;
    }

    public List<RequirementDto> getManufacturingRequirements() {
        return manufacturingRequirements;
    }

    public void setManufacturingRequirements(List<RequirementDto> manufacturingRequirements) {
        this.manufacturingRequirements = manufacturingRequirements;
    }

    public List<RequirementDto> getTimeProductivityRequirements() {
        return timeProductivityRequirements;
    }

    public void setTimeProductivityRequirements(List<RequirementDto> timeProductivityRequirements) {
        this.timeProductivityRequirements = timeProductivityRequirements;
    }

    public List<RequirementDto> getMaterialProductivityRequirements() {
        return materialProductivityRequirements;
    }

    public void setMaterialProductivityRequirements(List<RequirementDto> materialProductivityRequirements) {
        this.materialProductivityRequirements = materialProductivityRequirements;
    }

    public List<RequirementDto> getCopyingRequirements() {
        return copyingRequirements;
    }

    public void setCopyingRequirements(List<RequirementDto> copyingRequirements) {
        this.copyingRequirements = copyingRequirements;
    }

    public List<RequirementDto> getReverseEngineeringRequirements() {
        return reverseEngineeringRequirements;
    }

    public void setReverseEngineeringRequirements(List<RequirementDto> reverseEngineeringRequirements) {
        this.reverseEngineeringRequirements = reverseEngineeringRequirements;
    }

    public List<RequirementDto> getInventionRequirements() {
        return inventionRequirements;
    }

    public void setInventionRequirements(List<RequirementDto> inventionRequirements) {
        this.inventionRequirements = inventionRequirements;
    }
}
