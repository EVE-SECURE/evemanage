package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.util.List;

public class PreferencesTabFirstLoadActionRunnerImpl implements PreferencesTabFirstLoadActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesTabFirstLoadActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesTabFirstLoadActionResponse execute(PreferencesTabFirstLoadAction action) throws Exception {
        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();
        List<CharacterDto> characters = clientFacade.getCharacters();
        List<ApiKeyDto> apiKeys = clientFacade.getApiKeys();
        List<CharacterNameDto> newCharacterNames = clientFacade.getAvailableNewCharacterNames();
        List<SkillLevelDto> skillLevelsForCalculation = clientFacade.getSkillLevelsForCalculation();
        List<RegionDto> regions = clientFacade.getRegions();
        RegionDto preferredRegion = clientFacade.getPreferredRegion();
        List<PriceFetchOption> priceFetchOptions = clientFacade.getPriceFetchOptions();
        PriceFetchOption preferredPriceFetchOption = clientFacade.getPreferredPriceFetchOption();

        PreferencesTabFirstLoadActionResponse response = new PreferencesTabFirstLoadActionResponse();
        response.setCharacters(characters);
        response.setMainCharacter(mainCharacter);
        response.setNewCharacterNames(newCharacterNames);
        response.setApiKeys(apiKeys);
        response.setSkillLevelsForCalculation(skillLevelsForCalculation);
        response.setRegions(regions);
        response.setPreferredRegion(preferredRegion);
        response.setPriceFetchOptions(priceFetchOptions);
        response.setPreferredPriceFetchOption(preferredPriceFetchOption);
        return response;
    }
}