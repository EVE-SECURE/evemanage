package lv.odylab.evemanage.client.presenter.tab.blueprint;

import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.MaterialDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.RequirementDto;
import lv.odylab.evemanage.client.widget.AttachedCharacterLabel;
import lv.odylab.evemanage.client.widget.QuantityLabel;
import lv.odylab.evemanage.client.widget.SharingLevelLabel;
import lv.odylab.evemanage.client.widget.TimeLabel;
import lv.odylab.evemanage.client.widget.WasteLabel;
import lv.odylab.evemanage.shared.EveCalculator;

import java.util.Map;

public class ComputableBlueprintDetails {
    private BlueprintDetailsDto blueprintDetails;
    private Map<MaterialDto, QuantityLabel> materialToWidgetMap;
    private Map<RequirementDto, QuantityLabel> requirementToWidgetMap;
    private TimeLabel productionTimeStationText;
    private TimeLabel productionTimePosText;
    private TimeLabel researchProductivityTimeStationText;
    private TimeLabel researchProductivityTimePosText;
    private TimeLabel researchMaterialTimeStationText;
    private TimeLabel researchMaterialTimePosText;
    private TimeLabel researchCopyTimeStationText;
    private TimeLabel researchCopyTimePosText;
    private TimeLabel researchTechTimeStationText;
    private TimeLabel researchTechTimePosText;
    private WasteLabel wasteText;
    private AttachedCharacterLabel attachedCharacterNameLabel;
    private SharingLevelLabel sharingLevelLabel;

    public void setBlueprintDetails(BlueprintDetailsDto blueprintDetails) {
        this.blueprintDetails = blueprintDetails;
    }

    public void setMaterialToWidgetMap(Map<MaterialDto, QuantityLabel> materialToWidgetMap) {
        this.materialToWidgetMap = materialToWidgetMap;
    }

    public void setRequirementToWidgetMap(Map<RequirementDto, QuantityLabel> requirementToWidgetMap) {
        this.requirementToWidgetMap = requirementToWidgetMap;
    }

    public void setProductionTimeStationText(TimeLabel productionTimeStationText) {
        this.productionTimeStationText = productionTimeStationText;
    }

    public void setProductionTimePosText(TimeLabel productionTimePosText) {
        this.productionTimePosText = productionTimePosText;
    }

    public void setResearchProductivityTimeStationText(TimeLabel researchProductivityTimeStationText) {
        this.researchProductivityTimeStationText = researchProductivityTimeStationText;
    }

    public void setResearchProductivityTimePosText(TimeLabel researchProductivityTimePosText) {
        this.researchProductivityTimePosText = researchProductivityTimePosText;
    }

    public void setResearchMaterialTimeStationText(TimeLabel researchMaterialTimeStationText) {
        this.researchMaterialTimeStationText = researchMaterialTimeStationText;
    }

    public void setResearchMaterialTimePosText(TimeLabel researchMaterialTimePosText) {
        this.researchMaterialTimePosText = researchMaterialTimePosText;
    }

    public void setResearchCopyTimeStationText(TimeLabel researchCopyTimeStationText) {
        this.researchCopyTimeStationText = researchCopyTimeStationText;
    }

    public void setResearchCopyTimePosText(TimeLabel researchCopyTimePosText) {
        this.researchCopyTimePosText = researchCopyTimePosText;
    }

    public void setResearchTechTimeStationText(TimeLabel researchTechTimeStationText) {
        this.researchTechTimeStationText = researchTechTimeStationText;
    }

    public void setResearchTechTimePosText(TimeLabel researchTechTimePosText) {
        this.researchTechTimePosText = researchTechTimePosText;
    }

    public void setWasteText(WasteLabel wasteText) {
        this.wasteText = wasteText;
    }

    public void setAttachedCharacterNameLabel(AttachedCharacterLabel attachedCharacterNameLabel) {
        this.attachedCharacterNameLabel = attachedCharacterNameLabel;
    }

    public void setSharingLevelLabel(SharingLevelLabel sharingLevelLabel) {
        this.sharingLevelLabel = sharingLevelLabel;
    }

    public void recalculate(BlueprintDto blueprint, EveCalculator calculator) {
        for (Map.Entry<MaterialDto, QuantityLabel> mapEntry : materialToWidgetMap.entrySet()) {
            MaterialDto material = mapEntry.getKey();
            QuantityLabel quantityLabel = mapEntry.getValue();
            quantityLabel.setQuantity(calculator.calculateMaterialAmount(material.getQuantity(), blueprint.getMaterialLevel(), blueprintDetails.getWasteFactor()));
        }
        for (Map.Entry<RequirementDto, QuantityLabel> mapEntry : requirementToWidgetMap.entrySet()) {
            RequirementDto requirement = mapEntry.getKey();
            QuantityLabel quantityLabel = mapEntry.getValue();
            quantityLabel.setQuantity(requirement.getQuantity());
        }
        productionTimeStationText.setTime(calculator.calculateManufacturingTime(blueprintDetails.getProductionTime(), blueprintDetails.getProductivityModifier(), blueprint.getProductivityLevel(), 1.0));
        productionTimePosText.setTime(calculator.calculateManufacturingTime(blueprintDetails.getProductionTime(), blueprintDetails.getProductivityModifier(), blueprint.getProductivityLevel(), 0.75));
        researchProductivityTimeStationText.setTime(calculator.calculatePeResearchTime(blueprintDetails.getResearchProductivityTime(), 1.0));
        researchProductivityTimePosText.setTime(calculator.calculatePeResearchTime(blueprintDetails.getResearchProductivityTime(), 0.75));
        researchMaterialTimeStationText.setTime(calculator.calculateMeResearchTime(blueprintDetails.getResearchMaterialTime(), 1.0));
        researchMaterialTimePosText.setTime(calculator.calculateMeResearchTime(blueprintDetails.getResearchMaterialTime(), 0.75));
        researchCopyTimeStationText.setTime(calculator.calculateCopyTime(blueprintDetails.getResearchCopyTime(), 1.00));
        researchCopyTimePosText.setTime(calculator.calculateCopyTime(blueprintDetails.getResearchCopyTime(), 0.75));
        researchTechTimeStationText.setTime(calculator.calculateInventionTime(blueprintDetails.getResearchTechTime(), 1.00));
        researchTechTimePosText.setTime(calculator.calculateInventionTime(blueprintDetails.getResearchTechTime(), 0.75));
        wasteText.setWaste(calculator.calculateWaste(blueprint.getMaterialLevel(), blueprintDetails.getWasteFactor()));
        attachedCharacterNameLabel.setAttachedCharacter(blueprint.getAttachedCharacterInfo());
        sharingLevelLabel.setSharingLevel(blueprint.getSharingLevel());
    }
}
