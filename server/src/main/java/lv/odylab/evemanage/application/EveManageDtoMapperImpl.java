package lv.odylab.evemanage.application;

import lv.odylab.eveapi.parser.method.account.character.ApiAccountCharacterRow;
import lv.odylab.eveapi.parser.method.character.sheet.ApiCharacterGenericRow;
import lv.odylab.eveapi.parser.method.character.sheet.ApiCharacterSheetResult;
import lv.odylab.eveapi.parser.method.common.accbalance.ApiAccountBalanceRow;
import lv.odylab.eveapi.parser.method.common.industryjob.ApiIndustryJobRow;
import lv.odylab.eveapi.parser.method.corporation.sheet.ApiCorporationSheetResult;
import lv.odylab.evecentralapi.parser.method.marketstat.MarketStatType;
import lv.odylab.evedb.rpc.dto.InvBlueprintTypeDto;
import lv.odylab.evedb.rpc.dto.InvTypeBasicInfoDto;
import lv.odylab.evedb.rpc.dto.InvTypeMaterialDto;
import lv.odylab.evedb.rpc.dto.RamTypeRequirementDto;
import lv.odylab.evemanage.application.exception.validation.InvalidPriceException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.MaterialDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.RequirementDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationItemDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyCharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.calculation.CalculationItem;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.eveapi.dto.AccountBalanceDto;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.IndustryJobDto;
import lv.odylab.evemanage.integration.evecentralapi.dto.MarketStatDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import lv.odylab.evemanage.integration.evemetricsapi.dto.ItemPriceDto;
import lv.odylab.evemanage.service.priceset.PriceSetItemDtoComparator;
import lv.odylab.evemetricsapi.parser.method.itemprice.ItemPriceType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EveManageDtoMapperImpl implements EveManageDtoMapper {

    @Override
    public UserDto map(User user, Class<UserDto> userDtoClass) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo != null) {
            CharacterDto mainCharacter = map(mainCharacterInfo, CharacterDto.class);
            userDto.setMainCharacter(mainCharacter);
        }
        return userDto;
    }

    @Override
    public BlueprintDto map(Blueprint blueprint, Class<BlueprintDto> blueprintDtoClass) {
        BlueprintDto blueprintDto = new BlueprintDto();
        blueprintDto.setId(blueprint.getId());
        blueprintDto.setItemID(blueprint.getItemID());
        blueprintDto.setItemTypeID(blueprint.getItemTypeID());
        blueprintDto.setItemTypeName(blueprint.getItemTypeName());
        blueprintDto.setProductTypeID(blueprint.getProductTypeID());
        blueprintDto.setProductTypeName(blueprint.getProductTypeName());
        blueprintDto.setProductCategoryID(blueprint.getProductTypeCategoryID());
        blueprintDto.setProductGraphicIcon(blueprint.getProductGraphicIcon());
        blueprintDto.setProductivityLevel(blueprint.getProductivityLevel());
        blueprintDto.setMaterialLevel(blueprint.getMaterialLevel());
        if (blueprint.getAttachedCharacterInfo() != null) {
            blueprintDto.setAttachedCharacterInfo(mapForCharacterInfo(blueprint.getAttachedCharacterInfo(), CharacterInfoDto.class));
        }
        blueprintDto.setSharingLevel(blueprint.getSharingLevel());
        return blueprintDto;
    }

    @Override
    public MaterialDto map(TypeMaterialDto typeMaterial, Class<MaterialDto> blueprintMaterialDtoClass) {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setMaterialTypeID(typeMaterial.getMaterialTypeID());
        materialDto.setMaterialTypeName(typeMaterial.getMaterialTypeName());
        materialDto.setMaterialTypeCategoryID(typeMaterial.getMaterialTypeCategoryID());
        materialDto.setQuantity(typeMaterial.getQuantity());
        materialDto.setMaterialTypeGraphicIcon(typeMaterial.getMaterialTypeGraphicIcon());
        return materialDto;
    }

    @Override
    public TypeMaterialDto map(InvTypeMaterialDto invTypeMaterialDto, Class<TypeMaterialDto> typeMaterialDtoClass) {
        TypeMaterialDto typeMaterialDto = new TypeMaterialDto();
        typeMaterialDto.setMaterialTypeID(invTypeMaterialDto.getMaterialTypeID());
        typeMaterialDto.setMaterialTypeName(invTypeMaterialDto.getMaterialTypeName());
        typeMaterialDto.setMaterialTypeCategoryID(invTypeMaterialDto.getMaterialTypeCategoryID());
        typeMaterialDto.setQuantity(invTypeMaterialDto.getQuantity());
        typeMaterialDto.setMaterialTypeGraphicIcon(invTypeMaterialDto.getMaterialTypeIcon());
        return typeMaterialDto;
    }

    @Override
    public MaterialDto map(InvTypeMaterialDto invTypeMaterialDto, Class<MaterialDto> materialDtoClass) {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setMaterialTypeID(invTypeMaterialDto.getMaterialTypeID());
        materialDto.setMaterialTypeName(invTypeMaterialDto.getMaterialTypeName());
        materialDto.setMaterialTypeCategoryID(invTypeMaterialDto.getMaterialTypeCategoryID());
        materialDto.setQuantity(invTypeMaterialDto.getQuantity());
        materialDto.setMaterialTypeGraphicIcon(invTypeMaterialDto.getMaterialTypeIcon());
        return materialDto;
    }

    @Override
    public RequirementDto map(RamTypeRequirementDto ramTypeRequirementDto, Class<RequirementDto> requirementDtoClass) {
        RequirementDto requirementDto = new RequirementDto();
        requirementDto.setActivityID(ramTypeRequirementDto.getActivityID());
        requirementDto.setActivityName(ramTypeRequirementDto.getActivityName());
        requirementDto.setRequiredTypeID(ramTypeRequirementDto.getRequiredTypeID());
        requirementDto.setRequiredTypeName(ramTypeRequirementDto.getRequiredTypeName());
        requirementDto.setRequiredTypeCategoryID(ramTypeRequirementDto.getRequiredTypeCategoryID());
        requirementDto.setRequiredTypeGroupID(ramTypeRequirementDto.getRequiredTypeGroupID());
        requirementDto.setRequiredTypeGroupName(ramTypeRequirementDto.getRequiredTypeGroupName());
        requirementDto.setQuantity(ramTypeRequirementDto.getQuantity());
        requirementDto.setDamagePerJob(ramTypeRequirementDto.getDamagePerJob());
        requirementDto.setRequiredTypeNameGraphicIcon(ramTypeRequirementDto.getRequiredTypeIcon());
        return requirementDto;
    }

    @Override
    public BlueprintDetailsDto map(lv.odylab.evedb.rpc.dto.BlueprintDetailsDto blueprintDetailsDto, Class<BlueprintDetailsDto> blueprintDetailsDtoClass) {
        BlueprintDetailsDto blueprintDetails = new BlueprintDetailsDto();
        blueprintDetails.setBlueprintTypeDto(map(blueprintDetailsDto.getInvBlueprintTypeDto(), BlueprintTypeDto.class));

        List<TypeMaterialDto> materialDtos = new ArrayList<TypeMaterialDto>();
        for (InvTypeMaterialDto invTypeMaterialDto : blueprintDetailsDto.getMaterialDtos()) {
            materialDtos.add(map(invTypeMaterialDto, TypeMaterialDto.class));
        }
        List<TypeRequirementDto> manufacturingRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getManufacturingRequirementDtos()) {
            manufacturingRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        List<TypeRequirementDto> timeProductivityRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getTimeProductivityRequirementDtos()) {
            timeProductivityRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        List<TypeRequirementDto> materialProductivityRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getMaterialProductivityRequirementDtos()) {
            materialProductivityRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        List<TypeRequirementDto> copyingRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getCopyingRequirementDtos()) {
            copyingRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        List<TypeRequirementDto> reverseEngineeringRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getReverseEngineeringRequirementDtos()) {
            reverseEngineeringRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        List<TypeRequirementDto> inventionRequirementDtos = new ArrayList<TypeRequirementDto>();
        for (RamTypeRequirementDto ramTypeRequirementDto : blueprintDetailsDto.getInventionRequirementDtos()) {
            inventionRequirementDtos.add(map(ramTypeRequirementDto, TypeRequirementDto.class));
        }
        blueprintDetails.setMaterialDtos(materialDtos);
        blueprintDetails.setManufacturingRequirementDtos(manufacturingRequirementDtos);
        blueprintDetails.setTimeProductivityRequirementDtos(timeProductivityRequirementDtos);
        blueprintDetails.setMaterialProductivityRequirementDtos(materialProductivityRequirementDtos);
        blueprintDetails.setCopyingRequirementDtos(copyingRequirementDtos);
        blueprintDetails.setReverseEngineeringRequirementDtos(reverseEngineeringRequirementDtos);
        blueprintDetails.setInventionRequirementDtos(inventionRequirementDtos);
        return blueprintDetails;
    }

    @Override
    public lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto map(BlueprintDetailsDto blueprintDetailsDto, Class<lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto> blueprintDetailsDtoClass) {
        lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto blueprintDetails = new lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto();
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        blueprintDetails.setBlueprintTypeID(blueprintTypeDto.getBlueprintTypeID());
        blueprintDetails.setBlueprintTypeName(blueprintTypeDto.getBlueprintTypeName());
        blueprintDetails.setTechLevel(blueprintTypeDto.getTechLevel());
        blueprintDetails.setProductionTime(blueprintTypeDto.getProductionTime());
        blueprintDetails.setResearchProductivityTime(blueprintTypeDto.getResearchProductivityTime());
        blueprintDetails.setResearchMaterialTime(blueprintTypeDto.getResearchMaterialTime());
        blueprintDetails.setResearchCopyTime(blueprintTypeDto.getResearchCopyTime());
        blueprintDetails.setResearchTechTime(blueprintTypeDto.getResearchTechTime());
        blueprintDetails.setProductivityModifier(blueprintTypeDto.getProductivityModifier());
        blueprintDetails.setWasteFactor(blueprintTypeDto.getWasteFactor());

        List<MaterialDto> materials = new ArrayList<MaterialDto>();
        for (TypeMaterialDto typeMaterialDto : blueprintDetailsDto.getMaterialDtos()) {
            materials.add(map(typeMaterialDto, MaterialDto.class));
        }
        List<RequirementDto> manufacturingRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getManufacturingRequirementDtos()) {
            manufacturingRequirements.add(map(requirementDto, RequirementDto.class));
        }
        List<RequirementDto> timeProductivityRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getTimeProductivityRequirementDtos()) {
            timeProductivityRequirements.add(map(requirementDto, RequirementDto.class));
        }
        List<RequirementDto> materialProductivityRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getMaterialProductivityRequirementDtos()) {
            materialProductivityRequirements.add(map(requirementDto, RequirementDto.class));
        }
        List<RequirementDto> copyingRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getCopyingRequirementDtos()) {
            copyingRequirements.add(map(requirementDto, RequirementDto.class));
        }
        List<RequirementDto> reverseEngineeringRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getReverseEngineeringRequirementDtos()) {
            reverseEngineeringRequirements.add(map(requirementDto, RequirementDto.class));
        }
        List<RequirementDto> inventionRequirements = new ArrayList<RequirementDto>();
        for (TypeRequirementDto requirementDto : blueprintDetailsDto.getInventionRequirementDtos()) {
            inventionRequirements.add(map(requirementDto, RequirementDto.class));
        }
        blueprintDetails.setMaterials(materials);
        blueprintDetails.setManufacturingRequirements(manufacturingRequirements);
        blueprintDetails.setTimeProductivityRequirements(timeProductivityRequirements);
        blueprintDetails.setMaterialProductivityRequirements(materialProductivityRequirements);
        blueprintDetails.setCopyingRequirements(copyingRequirements);
        blueprintDetails.setReverseEngineeringRequirements(reverseEngineeringRequirements);
        blueprintDetails.setInventionRequirements(inventionRequirements);
        return blueprintDetails;
    }

    @Override
    public TypeRequirementDto map(RamTypeRequirementDto ramTypeRequirementDto, Class<TypeRequirementDto> typeRequirementDtoClass) {
        TypeRequirementDto typeRequirementDto = new TypeRequirementDto();
        typeRequirementDto.setActivityID(ramTypeRequirementDto.getActivityID());
        typeRequirementDto.setActivityName(ramTypeRequirementDto.getActivityName());
        typeRequirementDto.setRequiredTypeID(ramTypeRequirementDto.getRequiredTypeID());
        typeRequirementDto.setRequiredTypeName(ramTypeRequirementDto.getRequiredTypeName());
        typeRequirementDto.setRequiredTypeCategoryID(ramTypeRequirementDto.getRequiredTypeCategoryID());
        typeRequirementDto.setRequiredTypeGroupID(ramTypeRequirementDto.getRequiredTypeGroupID());
        typeRequirementDto.setRequiredTypeGroupName(ramTypeRequirementDto.getRequiredTypeGroupName());
        typeRequirementDto.setQuantity(ramTypeRequirementDto.getQuantity());
        typeRequirementDto.setDamagePerJob(ramTypeRequirementDto.getDamagePerJob());
        typeRequirementDto.setRequiredTypeNameGraphicIcon(ramTypeRequirementDto.getRequiredTypeIcon());
        return typeRequirementDto;
    }

    @Override
    public RequirementDto map(TypeRequirementDto typeRequirementDto, Class<RequirementDto> requirementDtoClass) {
        RequirementDto requirementDto = new RequirementDto();
        requirementDto.setActivityID(typeRequirementDto.getActivityID());
        requirementDto.setActivityName(typeRequirementDto.getActivityName());
        requirementDto.setRequiredTypeID(typeRequirementDto.getRequiredTypeID());
        requirementDto.setRequiredTypeName(typeRequirementDto.getRequiredTypeName());
        requirementDto.setRequiredTypeCategoryID(typeRequirementDto.getRequiredTypeCategoryID());
        requirementDto.setRequiredTypeGroupID(typeRequirementDto.getRequiredTypeGroupID());
        requirementDto.setRequiredTypeGroupName(typeRequirementDto.getRequiredTypeGroupName());
        requirementDto.setQuantity(typeRequirementDto.getQuantity());
        requirementDto.setDamagePerJob(typeRequirementDto.getDamagePerJob());
        requirementDto.setRequiredTypeNameGraphicIcon(typeRequirementDto.getRequiredTypeNameGraphicIcon());
        return requirementDto;
    }

    @Override
    public PriceSetItem map(PriceSetItemDto priceSetItemDto, Class<PriceSetItem> priceSetItemClass) throws InvalidPriceException {
        PriceSetItem priceSetItem = new PriceSetItem();
        priceSetItem.setItemTypeID(priceSetItemDto.getItemTypeID());
        priceSetItem.setItemCategoryID(priceSetItemDto.getItemCategoryID());
        priceSetItem.setItemTypeName(priceSetItemDto.getItemTypeName());
        priceSetItem.setItemTypeIcon(priceSetItemDto.getItemTypeIcon());
        priceSetItem.setPrice(formatPrice(priceSetItemDto.getPrice()));
        return priceSetItem;
    }

    private String formatPrice(String price) throws InvalidPriceException {
        try {
            return new BigDecimal(price).setScale(2).toPlainString();
        } catch (ArithmeticException ae) {
            throw new InvalidPriceException(price, ErrorCode.INVALID_PRICE);
        } catch (NumberFormatException nfe) {
            throw new InvalidPriceException(price, ErrorCode.INVALID_PRICE);
        }
    }

    @Override
    public PriceSetItemDto map(PriceSetItem priceSetItem, Class<PriceSetItemDto> priceSetItemDtoClass) {
        PriceSetItemDto priceSetItemDto = new PriceSetItemDto();
        priceSetItemDto.setItemTypeID(priceSetItem.getItemTypeID());
        priceSetItemDto.setItemCategoryID(priceSetItem.getItemCategoryID());
        priceSetItemDto.setItemTypeName(priceSetItem.getItemTypeName());
        priceSetItemDto.setItemTypeIcon(priceSetItem.getItemTypeIcon());
        priceSetItemDto.setPrice(new BigDecimal(priceSetItem.getPrice()).setScale(2).toPlainString());
        return priceSetItemDto;
    }

    @Override
    public PriceSetNameDto map(PriceSet priceSet, Class<PriceSetNameDto> priceSetItemClass) {
        PriceSetNameDto priceSetNameDto = new PriceSetNameDto();
        priceSetNameDto.setPriceSetID(priceSet.getId());
        priceSetNameDto.setPriceSetName(priceSet.getName());
        return priceSetNameDto;
    }

    @Override
    public PriceSetDto map(PriceSet priceSet, Class<PriceSetDto> priceSetDtoClass) {
        PriceSetDto priceSetDto = new PriceSetDto();
        priceSetDto.setId(priceSet.getId());
        priceSetDto.setName(priceSet.getName());
        if (priceSet.getAttachedCharacterInfo() != null) {
            priceSetDto.setAttachedCharacterName(map(priceSet.getAttachedCharacterInfo(), CharacterNameDto.class));
        }
        priceSetDto.setSharingLevel(priceSet.getSharingLevel());
        priceSetDto.setCreatedDate(priceSet.getCreatedDate().toString());
        priceSetDto.setUpdatedDate(priceSet.getUpdatedDate().toString());
        List<PriceSetItemDto> priceSetItemDtoList = new ArrayList<PriceSetItemDto>();
        for (PriceSetItem priceSetItem : priceSet.getItems()) {
            priceSetItemDtoList.add(map(priceSetItem, PriceSetItemDto.class));
        }
        Collections.sort(priceSetItemDtoList, new PriceSetItemDtoComparator());
        priceSetDto.setItems(priceSetItemDtoList);
        return priceSetDto;
    }

    @Override
    public CharacterDto map(Character character, Class<CharacterDto> characterDtoClass) {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(character.getId());
        characterDto.setCharacterID(character.getCharacterID());
        characterDto.setName(character.getName());
        characterDto.setCorporationID(character.getCorporationID());
        characterDto.setCorporationName(character.getCorporationName());
        characterDto.setCorporationTitles(character.getCorporationTitles());
        characterDto.setAllianceID(character.getAllianceID());
        characterDto.setAllianceName(character.getAllianceName());
        return characterDto;
    }

    @Override
    public CharacterDto map(CharacterInfo characterInfo, Class<CharacterDto> characterDtoClass) {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setCharacterID(characterInfo.getCharacterID());
        characterDto.setName(characterInfo.getName());
        characterDto.setCorporationID(characterInfo.getCorporationID());
        characterDto.setCorporationName(characterInfo.getCorporationName());
        characterDto.setAllianceID(characterInfo.getAllianceID());
        characterDto.setAllianceName(characterInfo.getAllianceName());
        return characterDto;
    }

    @Override
    public CharacterNameDto map(CharacterInfo characterInfo, Class<CharacterNameDto> characterNameDtoClass) {
        CharacterNameDto attachedCharacterNameDto = new CharacterNameDto();
        attachedCharacterNameDto.setId(characterInfo.getCharacterID());
        attachedCharacterNameDto.setName(characterInfo.getName());
        return attachedCharacterNameDto;
    }

    @Override
    public CharacterInfoDto mapForCharacterInfo(CharacterInfo characterInfo, Class<CharacterInfoDto> characterInfoDtoClass) {
        CharacterInfoDto attachedCharacterInfoDto = new CharacterInfoDto();
        attachedCharacterInfoDto.setId(characterInfo.getId());
        attachedCharacterInfoDto.setCharacterID(characterInfo.getCharacterID());
        attachedCharacterInfoDto.setName(characterInfo.getName());
        attachedCharacterInfoDto.setCorporationID(characterInfo.getCorporationID());
        attachedCharacterInfoDto.setCorporationName(characterInfo.getCorporationName());
        attachedCharacterInfoDto.setCorporationTicker(characterInfo.getCorporationTicker());
        attachedCharacterInfoDto.setAllianceID(characterInfo.getAllianceID());
        attachedCharacterInfoDto.setAllianceName(characterInfo.getAllianceName());
        return attachedCharacterInfoDto;
    }

    @Override
    public CharacterInfo map(Character character, Class<CharacterInfo> characterInfoClass) {
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setId(character.getId());
        characterInfo.setCharacterID(character.getCharacterID());
        characterInfo.setName(character.getName());
        characterInfo.setCorporationID(character.getCorporationID());
        characterInfo.setCorporationName(character.getCorporationName());
        characterInfo.setCorporationTicker(character.getCorporationTicker());
        characterInfo.setAllianceID(character.getAllianceID());
        characterInfo.setAllianceName(character.getAllianceName());
        return characterInfo;
    }

    @Override
    public ApiKeyDto map(ApiKey apiKey, Class<ApiKeyDto> apiKeyDtoClass) {
        ApiKeyDto apiKeyDto = new ApiKeyDto();
        apiKeyDto.setId(apiKey.getId());
        List<ApiKeyCharacterInfoDto> characterInfoDtos = new ArrayList<ApiKeyCharacterInfoDto>();
        for (ApiKeyCharacterInfo characterInfo : apiKey.getCharacterInfos()) {
            characterInfoDtos.add(map(characterInfo, ApiKeyCharacterInfoDto.class));
        }
        apiKeyDto.setCharacterInfos(characterInfoDtos);
        apiKeyDto.setLastCheckDate(apiKey.getLastCheckDate());
        apiKeyDto.setKeyType(apiKey.getKeyType());
        apiKeyDto.setValid(apiKey.isValid());
        return apiKeyDto;
    }

    @Override
    public ApiKeyCharacterInfoDto map(ApiKeyCharacterInfo characterInfo, Class<ApiKeyCharacterInfoDto> apiKeyCharacterInfoDtoClass) {
        ApiKeyCharacterInfoDto apiKeyCharacterInfoDto = new ApiKeyCharacterInfoDto();
        apiKeyCharacterInfoDto.setCharacterID(characterInfo.getCharacterID());
        apiKeyCharacterInfoDto.setName(characterInfo.getName());
        apiKeyCharacterInfoDto.setCorporationID(characterInfo.getCorporationID());
        apiKeyCharacterInfoDto.setCorporationName(characterInfo.getCorporationName());
        return apiKeyCharacterInfoDto;
    }

    @Override
    public ItemTypeDto map(InvTypeBasicInfoDto invTypeBasicInfoDto, Class<ItemTypeDto> itemTypeDtoClass) {
        ItemTypeDto itemTypeDto = new ItemTypeDto();
        itemTypeDto.setItemTypeID(invTypeBasicInfoDto.getItemTypeID());
        itemTypeDto.setItemCategoryID(invTypeBasicInfoDto.getItemCategoryID());
        itemTypeDto.setName(invTypeBasicInfoDto.getName());
        itemTypeDto.setGraphicIcon(invTypeBasicInfoDto.getIcon());
        return itemTypeDto;
    }

    @Override
    public ItemTypeDto map(lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto itemTypeDto, Class<ItemTypeDto> itemTypeDtoClass) {
        ItemTypeDto itemTypeDtoToMap = new ItemTypeDto();
        itemTypeDtoToMap.setItemTypeID(itemTypeDto.getItemTypeID());
        itemTypeDtoToMap.setItemCategoryID(itemTypeDto.getItemCategoryID());
        itemTypeDtoToMap.setName(itemTypeDto.getName());
        itemTypeDtoToMap.setGraphicIcon(itemTypeDto.getGraphicIcon());
        return itemTypeDtoToMap;
    }

    @Override
    public lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto map(InvBlueprintTypeDto invBlueprintTypeDto, Class<BlueprintTypeDto> blueprintTypeDtoClass) {
        lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto blueprintTypeDto = new lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto();
        blueprintTypeDto.setBlueprintTypeID(invBlueprintTypeDto.getBlueprintTypeID());
        blueprintTypeDto.setBlueprintTypeName(invBlueprintTypeDto.getBlueprintTypeName());
        blueprintTypeDto.setProductTypeID(invBlueprintTypeDto.getProductTypeID());
        blueprintTypeDto.setProductTypeName(invBlueprintTypeDto.getProductTypeName());
        blueprintTypeDto.setProductCategoryID(invBlueprintTypeDto.getProductCategoryID());
        blueprintTypeDto.setProductGraphicIcon(invBlueprintTypeDto.getProductIcon());
        blueprintTypeDto.setTechLevel(invBlueprintTypeDto.getTechLevel());
        blueprintTypeDto.setProductionTime(invBlueprintTypeDto.getProductionTime());
        blueprintTypeDto.setResearchProductivityTime(invBlueprintTypeDto.getResearchProductivityTime());
        blueprintTypeDto.setResearchMaterialTime(invBlueprintTypeDto.getResearchMaterialTime());
        blueprintTypeDto.setResearchCopyTime(invBlueprintTypeDto.getResearchCopyTime());
        blueprintTypeDto.setResearchTechTime(invBlueprintTypeDto.getResearchTechTime());
        blueprintTypeDto.setProductivityModifier(invBlueprintTypeDto.getProductivityModifier());
        blueprintTypeDto.setWasteFactor(invBlueprintTypeDto.getWasteFactor());
        blueprintTypeDto.setMaxProductionLimit(invBlueprintTypeDto.getMaxProductionLimit());
        return blueprintTypeDto;
    }

    @Override
    public lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto map(InvTypeBasicInfoDto invTypeBasicInfoDto, Class<lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto> itemTypeDtoClass) {
        lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto itemTypeDto = new lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto();
        itemTypeDto.setItemTypeID(invTypeBasicInfoDto.getItemTypeID());
        itemTypeDto.setItemCategoryID(invTypeBasicInfoDto.getItemCategoryID());
        itemTypeDto.setName(invTypeBasicInfoDto.getName());
        itemTypeDto.setGraphicIcon(invTypeBasicInfoDto.getIcon());
        return itemTypeDto;
    }

    @Override
    public CharacterSheetDto map(ApiCharacterSheetResult apiCharacterSheetResult, Class<CharacterSheetDto> characterSheetDtoClass) {
        CharacterSheetDto characterSheetDto = new CharacterSheetDto();
        characterSheetDto.setCharacterID(apiCharacterSheetResult.getCharacterID());
        characterSheetDto.setName(apiCharacterSheetResult.getName());
        characterSheetDto.setCorporationName(apiCharacterSheetResult.getCorporationName());
        characterSheetDto.setCorporationID(apiCharacterSheetResult.getCorporationID());

        List<ApiCharacterGenericRow> corporationTitleRows = apiCharacterSheetResult.getCorporationTitles();
        List<String> corporationTitles = new ArrayList<String>();
        if (corporationTitleRows != null) {
            for (ApiCharacterGenericRow corporationTitleRow : corporationTitleRows) {
                corporationTitles.add(corporationTitleRow.getTitleName().toUpperCase());
            }
        }
        characterSheetDto.setCorporationTitles(corporationTitles);
        return characterSheetDto;
    }

    @Override
    public IndustryJobDto map(ApiIndustryJobRow apiIndustryJobRow, Class<IndustryJobDto> industryJobDtoClass) {
        IndustryJobDto apiIndustryJobDto = new IndustryJobDto();
        apiIndustryJobDto.setInstalledItemID(apiIndustryJobRow.getInstalledItemID());
        apiIndustryJobDto.setInstalledItemProductivityLevel(apiIndustryJobRow.getInstalledItemProductivityLevel());
        apiIndustryJobDto.setInstalledItemMaterialLevel(apiIndustryJobRow.getInstalledItemMaterialLevel());
        apiIndustryJobDto.setInstalledItemLicensedProductionRunsRemaining(apiIndustryJobRow.getInstalledItemLicensedProductionRunsRemaining());
        apiIndustryJobDto.setInstalledItemTypeID(apiIndustryJobRow.getInstalledItemTypeID());
        apiIndustryJobDto.setOutputTypeID(apiIndustryJobRow.getOutputTypeID());
        apiIndustryJobDto.setActivityID(apiIndustryJobRow.getActivityID());
        return apiIndustryJobDto;
    }

    @Override
    public AccountCharacterDto map(ApiAccountCharacterRow apiAccountCharacterRow, Class<AccountCharacterDto> accountCharacterDtoClass) {
        AccountCharacterDto accountCharacterDto = new AccountCharacterDto();
        accountCharacterDto.setName(apiAccountCharacterRow.getName());
        accountCharacterDto.setCharacterID(apiAccountCharacterRow.getCharacterID());
        accountCharacterDto.setCorporationName(apiAccountCharacterRow.getCorporationName());
        accountCharacterDto.setCorporationID(apiAccountCharacterRow.getCorporationID());
        return accountCharacterDto;
    }

    @Override
    public AccountBalanceDto map(ApiAccountBalanceRow apiAccountBalanceRow, Class<AccountBalanceDto> accountBalanceDtoClass) {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto();
        apiAccountBalanceRow.setAccountID(apiAccountBalanceRow.getAccountID());
        apiAccountBalanceRow.setAccountKey(apiAccountBalanceRow.getAccountKey());
        apiAccountBalanceRow.setBalance(apiAccountBalanceRow.getBalance());
        return accountBalanceDto;
    }

    @Override
    public CorporationSheetDto map(ApiCorporationSheetResult apiCorporationSheetResult, Class<CorporationSheetDto> corporationSheetDtoClass) {
        CorporationSheetDto corporationSheetDto = new CorporationSheetDto();
        corporationSheetDto.setTicker(apiCorporationSheetResult.getTicker());
        corporationSheetDto.setAllianceID(apiCorporationSheetResult.getAllianceID());
        corporationSheetDto.setAllianceName(apiCorporationSheetResult.getAllianceName());
        return corporationSheetDto;
    }

    @Override
    public MarketStatDto map(MarketStatType marketStatType, Class<MarketStatDto> marketStatDtoClass) {
        MarketStatDto marketStatDto = new MarketStatDto();
        marketStatDto.setTypeID(marketStatType.getTypeID());
        marketStatDto.setMedian(marketStatType.getAll().getMedian());
        marketStatDto.setBuyMedian(marketStatType.getBuy().getMedian());
        marketStatDto.setSellMedian(marketStatType.getSell().getMedian());
        return marketStatDto;
    }

    @Override
    public ItemPriceDto map(ItemPriceType itemPriceType, Class<ItemPriceDto> itemPriceDtoClass) {
        ItemPriceDto itemPriceDto = new ItemPriceDto();
        itemPriceDto.setTypeID(itemPriceType.getId());
        BigDecimal medianBuyPrice = itemPriceType.getTypeGlobalData().getBuyPrices().getMedian();
        BigDecimal medianSellPrice = itemPriceType.getTypeGlobalData().getSellPrices().getMedian();
        BigDecimal medianPrice = medianBuyPrice.add(medianSellPrice).divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);
        itemPriceDto.setMedian(medianPrice);
        return itemPriceDto;
    }

    @Override
    public CalculationDto map(Calculation calculation, Class<CalculationDto> calculationDtoClass) {
        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setId(calculation.getId());
        calculationDto.setName(calculation.getName());
        calculationDto.setPrice(calculation.getPrice());
        calculationDto.setBlueprintTypeID(calculation.getBlueprintTypeID());
        calculationDto.setBlueprintTypeName(calculation.getBlueprintTypeName());
        calculationDto.setProductTypeName(calculation.getProductTypeName());
        calculationDto.setProductTypeID(calculation.getProductTypeID());
        calculationDto.setProductTypeCategoryID(calculation.getProductTypeCategoryID());
        calculationDto.setProductGraphicIcon(calculation.getProductGraphicIcon());
        calculationDto.setProductivityLevel(calculation.getProductivityLevel());
        calculationDto.setMaterialLevel(calculation.getMaterialLevel());
        calculationDto.setWasteFactor(calculation.getWasteFactor());
        List<CalculationItemDto> calculationItemDtos = new ArrayList<CalculationItemDto>();
        for (CalculationItem calculationItem : calculation.getItems()) {
            calculationItemDtos.add(map(calculationItem, CalculationItemDto.class));
        }
        calculationDto.setItems(calculationItemDtos);
        return calculationDto;
    }

    private CalculationItemDto map(CalculationItem calculationItem, Class<CalculationItemDto> calculationItemDtoClass) {
        CalculationItemDto calculationItemDto = new CalculationItemDto();
        calculationItemDto.setPathExpression(PathExpression.parsePath(calculationItem.getPath()));
        calculationItemDto.setItemTypeID(calculationItem.getItemTypeID());
        calculationItemDto.setItemCategoryID(calculationItem.getItemCategoryID());
        calculationItemDto.setItemTypeName(calculationItem.getItemTypeName());
        calculationItemDto.setItemTypeIcon(calculationItem.getItemTypeIcon());
        calculationItemDto.setQuantity(calculationItem.getQuantity());
        calculationItemDto.setParentQuantity(calculationItem.getParentQuantity());
        calculationItemDto.setPerfectQuantity(calculationItem.getPerfectQuantity());
        calculationItemDto.setWasteFactor(calculationItem.getWasteFactor());
        calculationItemDto.setPrice(calculationItem.getPrice());
        calculationItemDto.setPriceOverride(calculationItem.getPriceOverride());
        calculationItemDto.setTotalPrice(calculationItem.getTotalPrice());
        calculationItemDto.setTotalPriceOverride(calculationItem.getTotalPriceOverride());
        calculationItemDto.setTotalPriceForParent(calculationItem.getTotalPriceForParent());
        return calculationItemDto;
    }
}