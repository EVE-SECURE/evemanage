package lv.odylab.evemanage.shared;

public class EveCalculator {
    public Double calculateWaste(Integer meLevel, Integer wasteFactor) {
        if (meLevel < 0) {
            return wasteFactor / 100.0 * (1 - meLevel);
        } else {
            return wasteFactor / 100.0 / (1 + meLevel);
        }
    }

    public Long calculateMaterialAmount(Long quantity, Integer meLevel, Integer wasteFactor) {
        return calculateMaterialAmount(quantity, meLevel, wasteFactor, 5);
    }

    public Long calculateMaterialAmount(Long quantity, Integer meLevel, Integer wasteFactor, Integer productionEfficiencySkillLevel) {
        if (meLevel < 0) {
            return Math.round(quantity * (1.25 - 0.05 * productionEfficiencySkillLevel + wasteFactor / 100.0 * (1 - meLevel)));
        } else {
            return Math.round(quantity * (1.25 - 0.05 * productionEfficiencySkillLevel + wasteFactor / 100.0 / (1 + meLevel)));
        }
    }

    public Integer calculateMeResearchTime(Integer baseResearchTime, Double researchSlotMultiplier) {
        return (int) Math.round(baseResearchTime * 0.75 * researchSlotMultiplier);
    }

    public Integer calculatePeResearchTime(Integer baseResearchTime, Double researchSlotMultiplier) {
        return (int) Math.round(baseResearchTime * 0.75 * researchSlotMultiplier);
    }

    public Integer calculateManufacturingTime(Integer baseProductionTime, Integer productivityModifier, Integer peLevel, Double manufacturingSlotMultiplier) {
        if (peLevel < 0) {
            return (int) Math.round(baseProductionTime * 0.8 * (1.0 - 1.0 * productivityModifier / baseProductionTime * (peLevel - 1.0)) * manufacturingSlotMultiplier);
        } else {
            return (int) Math.round(baseProductionTime * 0.8 * (1.0 - 1.0 * productivityModifier / baseProductionTime * peLevel / (1.0 + peLevel)) * manufacturingSlotMultiplier);
        }
    }

    public Integer calculateCopyTime(Integer researchCopyTime, double copySlotMultiplier) {
        return (int) Math.round(researchCopyTime * copySlotMultiplier);
    }

    public Integer calculateInventionTime(Integer researchTechTime, double inventionSlotMultiplier) {
        return (int) Math.round(researchTechTime * inventionSlotMultiplier);
    }

    public Long calculateBlueprintCopyQuantity(Long parentQuantity, Integer maxProductionLimit) {
        return (long) Math.ceil(1.0 * parentQuantity / maxProductionLimit);
    }

    public RationalNumber calculateBlueprintQuantityCorrectiveMultiplier(Long requiredQuantity, Long quantity, Integer maxProductionLimit) {
        return new RationalNumber(requiredQuantity, quantity * maxProductionLimit);
    }
}
