package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesSaveSkillLevelsForCalculationActionResponse;
import lv.odylab.evemanage.client.tracking.TrackingManager;

public class PreferencesSavedSkillLevelsForCalculationEvent extends PreferencesTabEvent<PreferencesSavedSkillLevelsForCalculationEventHandler> {
    public static final Type<PreferencesSavedSkillLevelsForCalculationEventHandler> TYPE = new Type<PreferencesSavedSkillLevelsForCalculationEventHandler>();

    public PreferencesSavedSkillLevelsForCalculationEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesSaveSkillLevelsForCalculationActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);
    }

    @Override
    public Type<PreferencesSavedSkillLevelsForCalculationEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesSavedSkillLevelsForCalculationEventHandler handler) {
        handler.onSavedSkillLevelsForCalculation(this);

        trackEvent();
    }
}