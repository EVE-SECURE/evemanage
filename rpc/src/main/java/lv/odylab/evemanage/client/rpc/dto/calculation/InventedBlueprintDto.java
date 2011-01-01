package lv.odylab.evemanage.client.rpc.dto.calculation;

import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;

import java.io.Serializable;
import java.util.List;

public class InventedBlueprintDto implements Serializable {
    private List<BlueprintItemDto> blueprintItems;
    private List<DecryptorDto> decryptors;
    private List<SkillLevelDto> skillLevels;
    private List<ItemTypeDto> baseItems;

    public List<BlueprintItemDto> getBlueprintItems() {
        return blueprintItems;
    }

    public void setBlueprintItems(List<BlueprintItemDto> blueprintItems) {
        this.blueprintItems = blueprintItems;
    }

    public List<DecryptorDto> getDecryptors() {
        return decryptors;
    }

    public void setDecryptors(List<DecryptorDto> decryptors) {
        this.decryptors = decryptors;
    }

    public List<SkillLevelDto> getSkillLevels() {
        return skillLevels;
    }

    public void setSkillLevels(List<SkillLevelDto> skillLevels) {
        this.skillLevels = skillLevels;
    }

    public List<ItemTypeDto> getBaseItems() {
        return baseItems;
    }

    public void setBaseItems(List<ItemTypeDto> baseItems) {
        this.baseItems = baseItems;
    }
}
