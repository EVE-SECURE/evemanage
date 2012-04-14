package lv.odylab.evemanage.client.event.blueprints;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.blueprints.BlueprintsReloadForCorporationActionResponse;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class BlueprintsReloadedForCorporationEvent extends BlueprintsTabEvent<BlueprintsReloadedForCorporationEventHandler> {
    public static final Type<BlueprintsReloadedForCorporationEventHandler> TYPE = new Type<BlueprintsReloadedForCorporationEventHandler>();
    private List<BlueprintDto> blueprints;

    public BlueprintsReloadedForCorporationEvent(TrackingManager trackingManager, EveManageConstants constants, BlueprintsReloadForCorporationActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.blueprints = response.getBlueprints();
    }

    @Override
    public Type<BlueprintsReloadedForCorporationEventHandler> getAssociatedType() {
        return TYPE;
    }

    public List<BlueprintDto> getBlueprints() {
        return blueprints;
    }

    @Override
    protected void dispatch(BlueprintsReloadedForCorporationEventHandler handler) {
        handler.onBlueprintsReloadedForCorporation(this);

        int blueprintCount = blueprints != null ? blueprints.size() : 0;
        trackEvent(String.valueOf(blueprintCount));
    }
}