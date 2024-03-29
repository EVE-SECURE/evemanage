package lv.odylab.evemanage.client.rpc;

public class EveCalculator {
    public Double calculateWaste(Integer meLevel, Integer wasteFactor) {
        if (meLevel < 0) {
            return wasteFactor / 100.0 * (1 - meLevel);
        } else {
            return wasteFactor / 100.0 / (1 + meLevel);
        }
    }

    public Long calculateMaterialAmount(Long quantity, Integer meLevel, Integer wasteFactor) {
        if (meLevel < 0) {
            return Math.round(quantity * (1.0 + wasteFactor / 100.0 * (1 - meLevel)));
        } else {
            return Math.round(quantity * (1.0 + wasteFactor / 100.0 / (1 + meLevel)));
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
}
