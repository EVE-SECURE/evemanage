package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSetMainCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PreferencesMainCharacterSetEvent extends PreferencesTabEvent<PreferencesMainCharacterSetEventHandler> {
    public static final Type<PreferencesMainCharacterSetEventHandler> TYPE = new Type<PreferencesMainCharacterSetEventHandler>();

    private CharacterDto mainCharacter;

    public PreferencesMainCharacterSetEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesSetMainCharacterActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.mainCharacter = response.getMainCharacter();
    }

    @Override
    public Type<PreferencesMainCharacterSetEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesMainCharacterSetEventHandler handler) {
        handler.onMainCharacterSet(this);

        trackEvent();
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }
}