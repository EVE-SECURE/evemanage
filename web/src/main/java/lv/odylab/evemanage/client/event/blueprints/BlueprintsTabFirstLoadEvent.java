package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.util.List;

public class BlueprintsTabFirstLoadEvent extends BlueprintsTabEvent<BlueprintsTabFirstLoadEventHandler> {
    public static final Type<BlueprintsTabFirstLoadEventHandler> TYPE = new Type<BlueprintsTabFirstLoadEventHandler>();
    private List<BlueprintDto> blueprints;
    private List<CharacterNameDto> attachedCharacterNames;
    private List<SharingLevel> sharingLevels;
    private List<ApiKeyDto> fullApiKeys;

    public BlueprintsTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintsTabFirstLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprints = response.getBlueprints();
        this.attachedCharacterNames = response.getAttachedCharacterNames();
        this.sharingLevels = response.getSharingLevels();
        this.fullApiKeys = response.getFullApiKeys();
    }

    @Override
    public Type<BlueprintsTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<BlueprintDto> getBlueprints() {
        return blueprints;
    }

    public List<CharacterNameDto> getAttachedCharacterNames() {
        return attachedCharacterNames;
    }

    public List<SharingLevel> getSharingLevels() {
        return sharingLevels;
    }

    public List<ApiKeyDto> getFullApiKeys() {
        return fullApiKeys;
    }

    @Override
    protected void dispatch(BlueprintsTabFirstLoadEventHandler handler) {
        handler.onBlueprintsTabFirstLoad(this);

        int blueprintCount = blueprints != null ? blueprints.size() : 0;
        trackEvent(String.valueOf(blueprintCount));
    }
}