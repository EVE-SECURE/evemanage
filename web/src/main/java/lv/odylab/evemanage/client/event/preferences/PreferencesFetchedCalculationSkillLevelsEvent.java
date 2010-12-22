package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesFetchCalculationSkillLevelsActionResponse;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesFetchedCalculationSkillLevelsEvent extends PreferencesTabEvent<PreferencesFetchedCalculationSkillLevelsEventHandler> {
    public static final Type<PreferencesFetchedCalculationSkillLevelsEventHandler> TYPE = new Type<PreferencesFetchedCalculationSkillLevelsEventHandler>();

    private List<SkillLevelDto> skillLevelsForCalculation;

    public PreferencesFetchedCalculationSkillLevelsEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesFetchCalculationSkillLevelsActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.skillLevelsForCalculation = response.getSkillLevelsForCalculation();
    }

    @Override
    public Type<PreferencesFetchedCalculationSkillLevelsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesFetchedCalculationSkillLevelsEventHandler handler) {
        handler.onFetchedCalculationSkillLevels(this);

        trackEvent();
    }

    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }
}