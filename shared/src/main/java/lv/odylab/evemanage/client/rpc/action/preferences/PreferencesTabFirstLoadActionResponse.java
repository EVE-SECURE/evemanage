package lv.odylab.evemanage.client.rpc.action.preferences;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;

import java.util.List;

public class PreferencesTabFirstLoadActionResponse implements Response {
    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<ApiKeyDto> apiKeys;
    private List<SkillLevelDto> skillLevelsForCalculation;
    private List<RegionDto> regions;
    private RegionDto preferredRegion;
    private List<PriceFetchOptionDto> priceFetchOptions;
    private PriceFetchOptionDto preferredPriceFetchOption;

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterDto> characters) {
        this.characters = characters;
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(CharacterDto mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public List<CharacterNameDto> getNewCharacterNames() {
        return newCharacterNames;
    }

    public void setNewCharacterNames(List<CharacterNameDto> newCharacterNames) {
        this.newCharacterNames = newCharacterNames;
    }

    public List<ApiKeyDto> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(List<ApiKeyDto> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        return skillLevelsForCalculation;
    }

    public void setSkillLevelsForCalculation(List<SkillLevelDto> skillLevelsForCalculation) {
        this.skillLevelsForCalculation = skillLevelsForCalculation;
    }

    public List<RegionDto> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDto> regions) {
        this.regions = regions;
    }

    public RegionDto getPreferredRegion() {
        return preferredRegion;
    }

    public void setPreferredRegion(RegionDto preferredRegion) {
        this.preferredRegion = preferredRegion;
    }

    public List<PriceFetchOptionDto> getPriceFetchOptions() {
        return priceFetchOptions;
    }

    public void setPriceFetchOptions(List<PriceFetchOptionDto> priceFetchOptions) {
        this.priceFetchOptions = priceFetchOptions;
    }

    public PriceFetchOptionDto getPreferredPriceFetchOption() {
        return preferredPriceFetchOption;
    }

    public void setPreferredPriceFetchOption(PriceFetchOptionDto preferredPriceFetchOption) {
        this.preferredPriceFetchOption = preferredPriceFetchOption;
    }
}