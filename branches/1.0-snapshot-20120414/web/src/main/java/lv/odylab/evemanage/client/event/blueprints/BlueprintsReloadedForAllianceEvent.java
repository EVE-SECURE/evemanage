package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForAllianceActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class BlueprintsReloadedForAllianceEvent extends BlueprintsTabEvent<BlueprintsReloadedForAllianceEventHandler> {
    public static final Type<BlueprintsReloadedForAllianceEventHandler> TYPE = new Type<BlueprintsReloadedForAllianceEventHandler>();
    private List<BlueprintDto> blueprints;

    public BlueprintsReloadedForAllianceEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintsReloadForAllianceActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprints = response.getBlueprints();
    }

    @Override
    public Type<BlueprintsReloadedForAllianceEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<BlueprintDto> getBlueprints() {
        return blueprints;
    }

    @Override
    protected void dispatch(BlueprintsReloadedForAllianceEventHandler handler) {
        handler.onBlueprintsReloadedForAlliance(this);

        int blueprintCount = blueprints != null ? blueprints.size() : 0;
        trackEvent(String.valueOf(blueprintCount));
    }
}