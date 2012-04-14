package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.util.List;

public class BlueprintsReloadActionResponse implements Response {
    private List<BlueprintDto> blueprints;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<SharingLevel> sharingLevels;

    public List<BlueprintDto> getBlueprints() {
        return blueprints;
    }

    public void setBlueprints(List<BlueprintDto> blueprints) {
        this.blueprints = blueprints;
    }

    public List<CharacterNameDto> getAttachedCharacterNames() {
        return attachedCharacterNames;
    }

    public void setAttachedCharacterNames(List<CharacterNameDto> attachedCharacterNames) {
        this.attachedCharacterNames = attachedCharacterNames;
    }

    public List<SharingLevel> getSharingLevels() {
        return sharingLevels;
    }

    public void setSharingLevels(List<SharingLevel> sharingLevels) {
        this.sharingLevels = sharingLevels;
    }
}