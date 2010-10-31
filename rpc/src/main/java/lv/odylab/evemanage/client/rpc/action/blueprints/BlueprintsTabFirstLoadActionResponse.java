package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.util.List;

public class BlueprintsTabFirstLoadActionResponse implements Response {
    private List<BlueprintDto> blueprints;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<String> sharingLevels;

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

    public List<String> getSharingLevels() {
        return sharingLevels;
    }

    public void setSharingLevels(List<String> sharingLevels) {
        this.sharingLevels = sharingLevels;
    }
}