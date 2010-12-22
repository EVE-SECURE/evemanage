package lv.odylab.evemanage.application;

import com.google.inject.Inject;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.application.exception.validation.InvalidPriceException;
import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDtoComparator;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDtoComparator;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.Region;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.PriceFetchOption;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Logging
public class EveManageClientFacadeImpl implements EveManageClientFacade {
    EveManageApplicationFacade applicationFacade;
    EveManageDtoMapper mapper;

    @Inject
    public EveManageClientFacadeImpl(EveManageApplicationFacade applicationFacade, EveManageDtoMapper mapper) {
        this.applicationFacade = applicationFacade;
        this.mapper = mapper;
    }

    @Override
    public LoginDto login(String requestUri, String locale) {
        return applicationFacade.login(requestUri, locale);
    }

    @Override
    public UserDto getCurrentUser() {
        User user = applicationFacade.getCurrentUser();
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<CharacterNameDto> getAvailableAttachedCharacterNames() {
        User user = applicationFacade.getCurrentUser();
        List<CharacterNameDto> attachedCharacterNameDtos = new ArrayList<CharacterNameDto>();
        if (user.getCharacterInfos() != null) {
            for (CharacterInfo characterInfo : user.getCharacterInfos()) {
                attachedCharacterNameDtos.add(mapper.map(characterInfo, CharacterNameDto.class));
            }
        }
        return attachedCharacterNameDtos;
    }

    @Override
    public List<String> getAvailableSharingLevels() {
        return applicationFacade.getAvailableSharingLevels();
    }

    @Override
    public List<ApiKeyDto> getFullApiKeys() {
        List<ApiKey> fullApiKeys = applicationFacade.getFullApiKeys();
        List<ApiKeyDto> fullApiKeyDtos = new ArrayList<ApiKeyDto>();
        for (ApiKey fullApiKey : fullApiKeys) {
            fullApiKeyDtos.add(mapper.map(fullApiKey, ApiKeyDto.class));
        }
        return fullApiKeyDtos;
    }

    @Override
    public List<SkillLevelDto> getSkillLevelsForCalculation() {
        List<SkillLevel> skillLevelsForCalculation = applicationFacade.getSkillLevelsForCalculation();
        List<SkillLevelDto> skillLevelDtosForCalculation = new ArrayList<SkillLevelDto>();
        for (SkillLevel skillLevel : skillLevelsForCalculation) {
            skillLevelDtosForCalculation.add(mapper.map(skillLevel, SkillLevelDto.class));
        }
        Collections.sort(skillLevelDtosForCalculation, new SkillLevelDtoComparator());
        return skillLevelDtosForCalculation;
    }

    @Override
    public void saveSkillLevelsForCalculation(List<SkillLevelDto> skillLevelDtosForCalculation) {
        List<SkillLevel> skillLevelsForCalculation = new ArrayList<SkillLevel>();
        for (SkillLevelDto skillLevelDto : skillLevelDtosForCalculation) {
            skillLevelsForCalculation.add(mapper.map(skillLevelDto, SkillLevel.class));
        }
        applicationFacade.saveSkillLevelsForCalculation(skillLevelsForCalculation);
    }

    @Override
    public List<SkillLevelDto> fetchCalculationSkillLevelsForMainCharacter() throws EveApiException {
        List<SkillLevel> skillLevelsForCalculation = applicationFacade.fetchCalculationSkillLevelsForMainCharacter();
        List<SkillLevelDto> skillLevelDtosForCalculation = new ArrayList<SkillLevelDto>();
        for (SkillLevel skillLevel : skillLevelsForCalculation) {
            skillLevelDtosForCalculation.add(mapper.map(skillLevel, SkillLevelDto.class));
        }
        return skillLevelDtosForCalculation;
    }

    @Override
    public List<RegionDto> getRegions() {
        List<Region> regions = applicationFacade.getRegions();
        List<RegionDto> regionDtos = new ArrayList<RegionDto>();
        for (Region region : regions) {
            regionDtos.add(mapper.map(region, RegionDto.class));
        }
        Collections.sort(regionDtos, new RegionDtoComparator());
        return regionDtos;
    }

    @Override
    public RegionDto getPreferredRegion() {
        Region preferredRegion = applicationFacade.getPreferredRegion();
        return mapper.map(preferredRegion, RegionDto.class);
    }

    @Override
    public List<PriceFetchOptionDto> getPriceFetchOptions() {
        List<PriceFetchOption> priceFetchOptions = applicationFacade.getPriceFetchOptions();
        List<PriceFetchOptionDto> priceFetchOptionDtos = new ArrayList<PriceFetchOptionDto>();
        for (PriceFetchOption priceFetchOption : priceFetchOptions) {
            priceFetchOptionDtos.add(mapper.map(priceFetchOption, PriceFetchOptionDto.class));
        }
        return priceFetchOptionDtos;
    }

    @Override
    public PriceFetchOptionDto getPreferredPriceFetchOption() {
        PriceFetchOption preferredPriceFetchOption = applicationFacade.getPreferredPriceFetchOption();
        return mapper.map(preferredPriceFetchOption, PriceFetchOptionDto.class);
    }

    @Override
    public void savePriceFetchConfiguration(Long preferredRegionID, String preferredPriceFetchOption) {
        applicationFacade.savePriceFetchConfiguration(preferredRegionID, PriceFetchOption.valueOf(preferredPriceFetchOption));
    }

    @Override
    public BlueprintDto createBlueprint(String blueprintTypeName, Integer meLevel, Integer peLevel) throws EveDbException, InvalidNameException {
        Blueprint blueprint = applicationFacade.createBlueprint(blueprintTypeName, meLevel, peLevel);
        return mapper.map(blueprint, BlueprintDto.class);
    }

    @Override
    public List<BlueprintDto> getBlueprints() {
        List<Blueprint> blueprints = applicationFacade.getBlueprints();
        List<BlueprintDto> blueprintDtos = new ArrayList<BlueprintDto>();
        for (Blueprint blueprint : blueprints) {
            blueprintDtos.add(mapper.map(blueprint, BlueprintDto.class));
        }
        return blueprintDtos;
    }

    @Override
    public List<BlueprintDto> getCorporationBlueprints() {
        List<Blueprint> blueprints = applicationFacade.getCorporationBlueprints();
        List<BlueprintDto> blueprintDtos = new ArrayList<BlueprintDto>();
        for (Blueprint blueprint : blueprints) {
            blueprintDtos.add(mapper.map(blueprint, BlueprintDto.class));
        }
        return blueprintDtos;
    }

    @Override
    public List<BlueprintDto> getAllianceBlueprints() {
        List<Blueprint> blueprints = applicationFacade.getAllianceBlueprints();
        List<BlueprintDto> blueprintDtos = new ArrayList<BlueprintDto>();
        for (Blueprint blueprint : blueprints) {
            blueprintDtos.add(mapper.map(blueprint, BlueprintDto.class));
        }
        return blueprintDtos;
    }

    @Override
    public BlueprintDto saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel) {
        Blueprint blueprint = applicationFacade.saveBlueprint(blueprintID, itemID, meLevel, peLevel, attachedCharacterID, sharingLevel);
        return mapper.map(blueprint, BlueprintDto.class);
    }

    @Override
    public void deleteBlueprint(Long blueprintID) {
        applicationFacade.deleteBlueprint(blueprintID);
    }

    @Override
    public BlueprintDetailsDto getBlueprintDetails(Long blueprintTypeID) throws EveDbException {
        lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto blueprintDetails = applicationFacade.getBlueprintDetails(blueprintTypeID);
        return mapper.map(blueprintDetails, BlueprintDetailsDto.class);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsFromXml(String importXml, Long attachedCharacterID, String sharingLevel) throws EveApiException {
        applicationFacade.importBlueprintsFromXml(importXml, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsFromCsv(String importCsv, Long attachedCharacterID, String sharingLevel) {
        applicationFacade.importBlueprintsFromCsv(importCsv, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsUsingOneTimeFullApiKey(String fullApiKey, Long userID, Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException {
        applicationFacade.importBlueprintsUsingOneTimeFullApiKey(fullApiKey, userID, characterID, level, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsUsingFullApiKey(Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException {
        applicationFacade.importBlueprintsUsingFullApiKey(characterID, level, attachedCharacterID, sharingLevel);
    }

    @Override
    public PriceSetDto getPriceSet(Long priceSetID) {
        PriceSet priceSet = applicationFacade.getPriceSet(priceSetID);
        return priceSet == null ? null : mapper.map(priceSet, PriceSetDto.class);
    }

    @Override
    public PriceSetDto getCorporationPriceSet(Long priceSetID) {
        PriceSet priceSet = applicationFacade.getCorporationPriceSet(priceSetID);
        return priceSet == null ? null : mapper.map(priceSet, PriceSetDto.class);
    }

    @Override
    public PriceSetDto getAlliancePriceSet(Long priceSetID) {
        PriceSet priceSet = applicationFacade.getAlliancePriceSet(priceSetID);
        return priceSet == null ? null : mapper.map(priceSet, PriceSetDto.class);
    }

    @Override
    public List<PriceSetNameDto> getPriceSetNames() {
        List<PriceSet> priceSets = applicationFacade.getPriceSets();
        List<PriceSetNameDto> priceSetNameDtos = new ArrayList<PriceSetNameDto>();
        for (PriceSet priceSet : priceSets) {
            priceSetNameDtos.add(mapper.map(priceSet, PriceSetNameDto.class));
        }
        return priceSetNameDtos;
    }

    @Override
    public List<PriceSetNameDto> getCorporationPriceSetNames() {
        List<PriceSet> priceSets = applicationFacade.getCorporationPriceSets();
        List<PriceSetNameDto> priceSetNameDtos = new ArrayList<PriceSetNameDto>();
        for (PriceSet priceSet : priceSets) {
            priceSetNameDtos.add(mapper.map(priceSet, PriceSetNameDto.class));
        }
        return priceSetNameDtos;
    }

    @Override
    public List<PriceSetNameDto> getAlliancePriceSetNames() {
        List<PriceSet> priceSets = applicationFacade.getAlliancePriceSets();
        List<PriceSetNameDto> priceSetNameDtos = new ArrayList<PriceSetNameDto>();
        for (PriceSet priceSet : priceSets) {
            priceSetNameDtos.add(mapper.map(priceSet, PriceSetNameDto.class));
        }
        return priceSetNameDtos;
    }

    @Override
    public PriceSetDto createPriceSet(String priceSetName) throws InvalidNameException {
        PriceSet priceSet = applicationFacade.createPriceSet(priceSetName);
        return mapper.map(priceSet, PriceSetDto.class);
    }

    @Override
    public void renamePriceSet(Long priceSetID, String priceSetName) throws InvalidNameException {
        applicationFacade.renamePriceSet(priceSetID, priceSetName);
    }

    @Override
    public void savePriceSet(Long priceSetID, List<PriceSetItemDto> priceSetItemDtos, String sharingLevel, Long attachedCharacterID) throws InvalidPriceException {
        Set<PriceSetItem> priceSetItems = new HashSet<PriceSetItem>();
        for (PriceSetItemDto priceSetItemDto : priceSetItemDtos) {
            priceSetItems.add(mapper.map(priceSetItemDto, PriceSetItem.class));
        }
        applicationFacade.savePriceSet(priceSetID, priceSetItems, sharingLevel, attachedCharacterID);
    }

    @Override
    @Logging(logArguments = false)
    public List<PriceSetItemDto> fetchPricesFromEveCentral(List<PriceSetItemDto> priceSetItemDtos) throws InvalidPriceException, EveCentralApiException {
        List<PriceSetItem> priceSetItems = new ArrayList<PriceSetItem>();
        for (PriceSetItemDto priceSetItemDto : priceSetItemDtos) {
            priceSetItems.add(mapper.map(priceSetItemDto, PriceSetItem.class));
        }
        List<PriceSetItem> updatedPriceSetItems = applicationFacade.fetchPricesFromEveCentral(priceSetItems);
        List<PriceSetItemDto> updatedPriceSetItemDtos = new ArrayList<PriceSetItemDto>();
        for (PriceSetItem updatedPriceSetItem : updatedPriceSetItems) {
            updatedPriceSetItemDtos.add(mapper.map(updatedPriceSetItem, PriceSetItemDto.class));
        }
        return updatedPriceSetItemDtos;
    }

    @Override
    public Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs) throws EveCentralApiException {
        return applicationFacade.fetchPricesFromEveCentralForTypeIDs(typeIDs);
    }

    @Override
    @Logging(logArguments = false)
    public List<PriceSetItemDto> fetchPricesFromEveMetrics(List<PriceSetItemDto> priceSetItemDtos) throws InvalidPriceException, EveMetricsApiException {
        List<PriceSetItem> priceSetItems = new ArrayList<PriceSetItem>();
        for (PriceSetItemDto priceSetItemDto : priceSetItemDtos) {
            priceSetItems.add(mapper.map(priceSetItemDto, PriceSetItem.class));
        }
        List<PriceSetItem> updatedPriceSetItems = applicationFacade.fetchPricesFromEveMetrics(priceSetItems);
        List<PriceSetItemDto> updatedPriceSetItemDtos = new ArrayList<PriceSetItemDto>();
        for (PriceSetItem updatedPriceSetItem : updatedPriceSetItems) {
            updatedPriceSetItemDtos.add(mapper.map(updatedPriceSetItem, PriceSetItemDto.class));
        }
        return updatedPriceSetItemDtos;
    }

    @Override
    public Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs) throws EveMetricsApiException {
        return applicationFacade.fetchPricesFromEveMetricsForTypeIDs(typeIDs);
    }

    @Override
    public void deletePriceSet(Long priceSetID) {
        applicationFacade.deletePriceSet(priceSetID);
    }

    @Override
    public List<CharacterDto> getCharacters() {
        List<Character> characters = applicationFacade.getCharacters();
        List<CharacterDto> characterDtos = new ArrayList<CharacterDto>();
        for (Character character : characters) {
            characterDtos.add(mapper.map(character, CharacterDto.class));
        }
        return characterDtos;
    }

    @Override
    public List<CharacterNameDto> getAvailableNewCharacterNames() {
        return applicationFacade.getAvailableNewCharacterNames();
    }

    @Override
    public List<CharacterNameDto> getCharacterNames() {
        return applicationFacade.getCharacterNames();
    }

    @Override
    public void addCharacter(Long characterID) throws EveApiException {
        applicationFacade.addCharacter(characterID);
    }

    @Override
    public void deleteCharacter(Long characterID) {
        applicationFacade.deleteCharacter(characterID);
    }

    @Override
    public void setMainCharacter(String characterName) {
        applicationFacade.setMainCharacter(characterName);
    }

    @Override
    public List<ApiKeyDto> getApiKeys() {
        List<ApiKey> apiKeys = applicationFacade.getApiKeys();
        List<ApiKeyDto> apiKeyDtos = new ArrayList<ApiKeyDto>();
        for (ApiKey apiKey : apiKeys) {
            apiKeyDtos.add(mapper.map(apiKey, ApiKeyDto.class));
        }
        return apiKeyDtos;
    }

    @Override
    @Logging(logArguments = false)
    public void createApiKey(Long apiKeyUserID, String apiKeyString) throws EveApiException, ApiKeyShouldBeRemovedException {
        applicationFacade.createApiKey(apiKeyString, apiKeyUserID);
    }

    @Override
    public void deleteApiKey(Long apiKeyID) {
        applicationFacade.deleteApiKey(apiKeyID);
    }

    @Override
    public lv.odylab.evemanage.client.rpc.dto.ItemTypeDto getItemTypeByName(String itemTypeName) throws EveDbException, InvalidItemTypeException {
        ItemTypeDto itemTypeDto = applicationFacade.getItemTypeByName(itemTypeName);
        return mapper.map(itemTypeDto, lv.odylab.evemanage.client.rpc.dto.ItemTypeDto.class);
    }

    @Override
    public List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> lookupType(String query) throws EveDbException {
        List<ItemTypeDto> itemTypeDtos = applicationFacade.lookupType(query);
        List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> itemTypeDtosForClient = new ArrayList<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto>();
        for (ItemTypeDto itemTypeDto : itemTypeDtos) {
            itemTypeDtosForClient.add(mapper.map(itemTypeDto, lv.odylab.evemanage.client.rpc.dto.ItemTypeDto.class));
        }
        return itemTypeDtosForClient;
    }

    @Override
    public List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> lookupBlueprintType(String query) throws EveDbException {
        List<ItemTypeDto> itemTypeDtos = applicationFacade.lookupBlueprintType(query);
        List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> itemTypeDtosForClient = new ArrayList<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto>();
        for (ItemTypeDto itemTypeDto : itemTypeDtos) {
            itemTypeDtosForClient.add(mapper.map(itemTypeDto, lv.odylab.evemanage.client.rpc.dto.ItemTypeDto.class));
        }
        return itemTypeDtosForClient;
    }

    @Override
    public CalculationDto getQuickCalculation(String blueprintName) throws EveDbException, InvalidNameException {
        Calculation calculation = applicationFacade.getCalculation(blueprintName);
        return mapper.map(calculation, CalculationDto.class);
    }

    @Override
    public CalculationDto getQuickCalculationForExpression(CalculationExpression calculationExpression) throws EveDbException, InvalidNameException {
        Calculation calculation = applicationFacade.getCalculationForExpression(calculationExpression);
        return mapper.map(calculation, CalculationDto.class);
    }

    @Override
    public CalculationDto getQuickCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        Calculation calculation = applicationFacade.getCalculation(pathNodes, blueprintName);
        return mapper.map(calculation, CalculationDto.class);
    }

    @Override
    public CalculationDto getQuickCalculation(Long[] pathNodes, Long blueprintProductTypeID) throws EveDbException, InvalidNameException, InvalidItemTypeException {
        Calculation calculation = applicationFacade.getCalculation(pathNodes, blueprintProductTypeID);
        return mapper.map(calculation, CalculationDto.class);
    }

    @Override
    public String getEveManageVersion() {
        return applicationFacade.getEveManageVersion();
    }

    @Override
    public String getEveDbVersion() throws EveDbException {
        return applicationFacade.getEveDbVersion();
    }

}