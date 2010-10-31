package lv.odylab.evemanage.integration.evedb.dto;

import java.io.Serializable;
import java.util.List;

public class BlueprintDetailsDto implements Serializable {
    private BlueprintTypeDto blueprintTypeDto;
    private List<TypeMaterialDto> materialDtos;
    private List<TypeRequirementDto> manufacturingRequirementDtos;
    private List<TypeRequirementDto> timeProductivityRequirementDtos;
    private List<TypeRequirementDto> materialProductivityRequirementDtos;
    private List<TypeRequirementDto> copyingRequirementDtos;
    private List<TypeRequirementDto> reverseEngineeringRequirementDtos;
    private List<TypeRequirementDto> inventionRequirementDtos;

    public BlueprintTypeDto getBlueprintTypeDto() {
        return blueprintTypeDto;
    }

    public void setBlueprintTypeDto(BlueprintTypeDto blueprintTypeDto) {
        this.blueprintTypeDto = blueprintTypeDto;
    }

    public List<TypeMaterialDto> getMaterialDtos() {
        return materialDtos;
    }

    public void setMaterialDtos(List<TypeMaterialDto> materialDtos) {
        this.materialDtos = materialDtos;
    }

    public List<TypeRequirementDto> getManufacturingRequirementDtos() {
        return manufacturingRequirementDtos;
    }

    public void setManufacturingRequirementDtos(List<TypeRequirementDto> manufacturingRequirementDtos) {
        this.manufacturingRequirementDtos = manufacturingRequirementDtos;
    }

    public List<TypeRequirementDto> getTimeProductivityRequirementDtos() {
        return timeProductivityRequirementDtos;
    }

    public void setTimeProductivityRequirementDtos(List<TypeRequirementDto> timeProductivityRequirementDtos) {
        this.timeProductivityRequirementDtos = timeProductivityRequirementDtos;
    }

    public List<TypeRequirementDto> getMaterialProductivityRequirementDtos() {
        return materialProductivityRequirementDtos;
    }

    public void setMaterialProductivityRequirementDtos(List<TypeRequirementDto> materialProductivityRequirementDtos) {
        this.materialProductivityRequirementDtos = materialProductivityRequirementDtos;
    }

    public List<TypeRequirementDto> getCopyingRequirementDtos() {
        return copyingRequirementDtos;
    }

    public void setCopyingRequirementDtos(List<TypeRequirementDto> copyingRequirementDtos) {
        this.copyingRequirementDtos = copyingRequirementDtos;
    }

    public List<TypeRequirementDto> getReverseEngineeringRequirementDtos() {
        return reverseEngineeringRequirementDtos;
    }

    public void setReverseEngineeringRequirementDtos(List<TypeRequirementDto> reverseEngineeringRequirementDtos) {
        this.reverseEngineeringRequirementDtos = reverseEngineeringRequirementDtos;
    }

    public List<TypeRequirementDto> getInventionRequirementDtos() {
        return inventionRequirementDtos;
    }

    public void setInventionRequirementDtos(List<TypeRequirementDto> inventionRequirementDtos) {
        this.inventionRequirementDtos = inventionRequirementDtos;
    }
}
