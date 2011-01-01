package lv.odylab.evemanage.service.calculation;

import lv.odylab.evemanage.domain.calculation.BlueprintItem;
import lv.odylab.evemanage.domain.eve.Decryptor;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;

import java.util.List;

public class InventedBlueprint {
    private List<BlueprintItem> blueprintItems;
    private List<Decryptor> decryptors;
    private List<SkillLevel> skillLevels;
    private List<ItemTypeDto> baseItems;

    public List<BlueprintItem> getBlueprintItems() {
        return blueprintItems;
    }

    public void setBlueprintItems(List<BlueprintItem> blueprintItems) {
        this.blueprintItems = blueprintItems;
    }

    public List<Decryptor> getDecryptors() {
        return decryptors;
    }

    public void setDecryptors(List<Decryptor> decryptors) {
        this.decryptors = decryptors;
    }

    public List<SkillLevel> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(List<SkillLevel> skillLevels) {
        this.skillLevels = skillLevels;
    }

    public List<ItemTypeDto> getBaseItems() {
        return baseItems;
    }

    public void setBaseItems(List<ItemTypeDto> baseItems) {
        this.baseItems = baseItems;
    }
}
