package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesTabFirstLoadActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesTabFirstLoadEvent extends PreferencesTabEvent<PreferencesTabFirstLoadEventHandler> {
    public static final Type<PreferencesTabFirstLoadEventHandler> TYPE = new Type<PreferencesTabFirstLoadEventHandler>();

    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<ApiKeyDto> apiKeys;
    private List<SkillLevelDto> skillLevelsForCalculation;
    private List<RegionDto> regions;
    private RegionDto preferredRegion;
    private List<PriceFetchOptionDto> priceFetchOptions;
    private PriceFetchOptionDto preferredPriceFetchOption;

    public PreferencesTabFirstLoadEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesTabFirstLoadActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.characters = response.getCharacters();
        this.mainCharacter = response.getMainCharacter();
        this.newCharacterNames = response.getNewCharacterNames();
        this.apiKeys = response.getApiKeys();
        this.skillLevelsForCalculation = response.getSkillLevelsForCalculation();
        this.regions = response.getRegions();
        this.preferredRegion = response.getPreferredRegion();
        this.priceFetchOptions = response.getPriceFetchOptions();
        this.preferredPriceFetchOption = response.getPreferredPriceFetchOption();
    }

    @Override
    public Type<PreferencesTabFirstLoadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesTabFirstLoadEventHandler handler) {
        handler.onPreferencesTabFirstLoad(this);

        trackEvent();
    }

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public List<CharacterNameDto> getNewCharacterNames() {
        return newCharacterNames;
    }

    public List<ApiKeyDto> getApiKeys() {
        return apiKeys;
    }

    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }

    public List<RegionDto> getRegions() {
        return regions;
    }

    public RegionDto getPreferredRegion() {
        return preferredRegion;
    }

    public List<PriceFetchOptionDto> getPriceFetchOptions() {
        return priceFetchOptions;
    }

    public PriceFetchOptionDto getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }
}