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
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDtoComparator;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDtoComparator;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;
import lv.odylab.evemanage.service.calculation.InventedBlueprint;
import lv.odylab.evemanage.service.calculation.UsedBlueprint;
import lv.odylab.evemanage.service.calculation.UsedSchematic;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;
import lv.odylab.evemanage.shared.eve.Region;
import lv.odylab.evemanage.shared.eve.SharingLevel;

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
    public List<SharingLevel> getAvailableSharingLevels() {
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
    public List<PriceFetchOption> getPriceFetchOptions() {
        return applicationFacade.getPriceFetchOptions();
    }

    @Override
    public PriceFetchOption getPreferredPriceFetchOption() {
        return applicationFacade.getPreferredPriceFetchOption();
    }

    @Override
    public void savePriceFetchConfiguration(Region preferredRegion, String preferredPriceFetchOption) {
        applicationFacade.savePriceFetchConfiguration(preferredRegion, PriceFetchOption.valueOf(preferredPriceFetchOption));
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
    public BlueprintDto saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, SharingLevel sharingLevel) {
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
    public void importBlueprintsFromXml(String importXml, Long attachedCharacterID, SharingLevel sharingLevel) throws EveApiException {
        applicationFacade.importBlueprintsFromXml(importXml, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsFromCsv(String importCsv, Long attachedCharacterID, SharingLevel sharingLevel) {
        applicationFacade.importBlueprintsFromCsv(importCsv, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsUsingOneTimeFullApiKey(String fullApiKey, Long userID, Long characterID, String level, Long attachedCharacterID, SharingLevel sharingLevel) throws EveApiException {
        applicationFacade.importBlueprintsUsingOneTimeFullApiKey(fullApiKey, userID, characterID, level, attachedCharacterID, sharingLevel);
    }

    @Override
    @Logging(logArguments = false)
    public void importBlueprintsUsingFullApiKey(Long characterID, String level, Long attachedCharacterID, SharingLevel sharingLevel) throws EveApiException {
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
    public void savePriceSet(Long priceSetID, List<PriceSetItemDto> priceSetItemDtos, SharingLevel sharingLevel, Long attachedCharacterID) throws InvalidPriceException {
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
    public Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveCentralApiException {
        return applicationFacade.fetchPricesFromEveCentralForTypeIDs(typeIDs, regionID, priceFetchOption);
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
    public Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveMetricsApiException {
        return applicationFacade.fetchPricesFromEveMetricsForTypeIDs(typeIDs, regionID, priceFetchOption);
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
    public CalculationDto getNewCalculation(String blueprintName) throws EveDbException, InvalidNameException {
        Calculation calculation = applicationFacade.getNewCalculation(blueprintName);
        return mapper.map(calculation, CalculationDto.class);
    }

    @Override
    public UsedBlueprintDto useBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        UsedBlueprint usedBlueprint = applicationFacade.useBlueprint(pathNodes, blueprintName);
        return mapper.map(usedBlueprint, UsedBlueprintDto.class);
    }

    @Override
    public UsedBlueprintDto useBlueprint(Long[] pathNodes, Long blueprintProductTypeID) throws EveDbException, InvalidNameException, InvalidItemTypeException {
        UsedBlueprint usedBlueprint = applicationFacade.useBlueprint(pathNodes, blueprintProductTypeID);
        return mapper.map(usedBlueprint, UsedBlueprintDto.class);
    }

    @Override
    public InventedBlueprintDto inventBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        InventedBlueprint inventedBlueprint = applicationFacade.inventBlueprint(pathNodes, blueprintName);
        return mapper.map(inventedBlueprint, InventedBlueprintDto.class);
    }

    @Override
    public UsedSchematicDto useSchematic(Long[] pathNodes, String schematicName) throws InvalidItemTypeException, EveDbException {
        UsedSchematic usedSchematic = applicationFacade.useSchematic(pathNodes, schematicName);
        return mapper.map(usedSchematic, UsedSchematicDto.class);
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