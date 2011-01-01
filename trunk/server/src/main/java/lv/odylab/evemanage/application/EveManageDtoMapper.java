package lv.odylab.evemanage.application;

import lv.odylab.eveapi.parser.method.account.character.ApiAccountCharacterRow;
import lv.odylab.eveapi.parser.method.character.sheet.ApiCharacterSheetResult;
import lv.odylab.eveapi.parser.method.common.accbalance.ApiAccountBalanceRow;
import lv.odylab.eveapi.parser.method.common.industryjob.ApiIndustryJobRow;
import lv.odylab.eveapi.parser.method.corporation.sheet.ApiCorporationSheetResult;
import lv.odylab.evecentralapi.parser.method.marketstat.MarketStatType;
import lv.odylab.evedb.rpc.dto.BlueprintDetailsDto;
import lv.odylab.evedb.rpc.dto.InvBlueprintTypeDto;
import lv.odylab.evedb.rpc.dto.InvTypeBasicInfoDto;
import lv.odylab.evedb.rpc.dto.InvTypeMaterialDto;
import lv.odylab.evedb.rpc.dto.PlanetSchematicDto;
import lv.odylab.evedb.rpc.dto.RamTypeRequirementDto;
import lv.odylab.evemanage.application.exception.validation.InvalidPriceException;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.MaterialDto;
import lv.odylab.evemanage.client.rpc.dto.blueprint.RequirementDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.CalculationDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.InventedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedBlueprintDto;
import lv.odylab.evemanage.client.rpc.dto.calculation.UsedSchematicDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyCharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterInfoDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;
import lv.odylab.evemanage.client.rpc.dto.user.SkillLevelDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyCharacterInfo;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.Region;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.PriceFetchOption;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.eveapi.dto.AccountBalanceDto;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.IndustryJobDto;
import lv.odylab.evemanage.integration.evecentralapi.dto.MarketStatDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.SchematicItemDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import lv.odylab.evemanage.integration.evemetricsapi.dto.ItemPriceDto;
import lv.odylab.evemanage.service.calculation.InventedBlueprint;
import lv.odylab.evemanage.service.calculation.UsedBlueprint;
import lv.odylab.evemanage.service.calculation.UsedSchematic;
import lv.odylab.evemetricsapi.parser.method.itemprice.ItemPriceType;

public interface EveManageDtoMapper {

    UserDto map(User user, Class<UserDto> userDtoClass);

    SkillLevelDto map(SkillLevel skillLevel, Class<SkillLevelDto> skillLevelDtoClass);

    SkillLevel map(SkillLevelDto skillLevelDto, Class<SkillLevel> skillLevelClass);

    RegionDto map(Region region, Class<RegionDto> regionDtoClass);

    PriceFetchOptionDto map(PriceFetchOption priceFetchOption, Class<PriceFetchOptionDto> priceFetchOptionDtoClass);

    BlueprintDto map(Blueprint blueprint, Class<BlueprintDto> blueprintDtoClass);

    MaterialDto map(TypeMaterialDto typeMaterial, Class<MaterialDto> blueprintMaterialDtoClass);

    TypeMaterialDto map(InvTypeMaterialDto invTypeMaterialDto, Class<TypeMaterialDto> typeMaterialDtoClass);

    MaterialDto map(InvTypeMaterialDto invTypeMaterialDto, Class<MaterialDto> materialDtoClass);

    RequirementDto map(RamTypeRequirementDto ramTypeRequirementDto, Class<RequirementDto> requirementDtoClass);

    lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto map(BlueprintDetailsDto blueprintDetailsDto, Class<lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto> blueprintDetailsDtoClass);

    lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto map(lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto blueprintDetailsDto, Class<lv.odylab.evemanage.client.rpc.dto.blueprint.BlueprintDetailsDto> blueprintDetailsDtoClass);

    TypeRequirementDto map(RamTypeRequirementDto ramTypeRequirementDto, Class<TypeRequirementDto> typeRequirementDtoClass);

    RequirementDto map(TypeRequirementDto typeRequirementDto, Class<RequirementDto> requirementDtoClass);

    PriceSetItem map(PriceSetItemDto priceSetItemDto, Class<PriceSetItem> priceSetItemClass) throws InvalidPriceException;

    PriceSetItemDto map(PriceSetItem updatedPriceSetItem, Class<PriceSetItemDto> priceSetItemDtoClass);

    PriceSetNameDto map(PriceSet priceSet, Class<PriceSetNameDto> priceSetItemClass);

    PriceSetDto map(PriceSet priceSet, Class<PriceSetDto> priceSetDtoClass);

    CharacterDto map(lv.odylab.evemanage.domain.eve.Character character, Class<CharacterDto> characterDtoClass);

    CharacterDto map(CharacterInfo characterInfo, Class<CharacterDto> characterDtoClass);

    CharacterNameDto map(CharacterInfo characterInfo, Class<CharacterNameDto> characterNameDtoClass);

    CharacterInfoDto mapForCharacterInfo(CharacterInfo characterInfo, Class<CharacterInfoDto> characterInfoDtoClass);

    CharacterInfo map(Character character, Class<CharacterInfo> characterInfoClass);

    ApiKeyDto map(ApiKey apiKey, Class<ApiKeyDto> apiKeyDtoClass);

    ApiKeyCharacterInfoDto map(ApiKeyCharacterInfo characterInfo, Class<ApiKeyCharacterInfoDto> apiKeyCharacterInfoDtoClass);

    ItemTypeDto map(InvTypeBasicInfoDto invTypeBasicInfoDto, Class<ItemTypeDto> itemTypeDtoClass);

    ItemTypeDto map(lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto itemTypeDto, Class<ItemTypeDto> itemTypeDtoClass);

    lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto map(InvBlueprintTypeDto invBlueprintTypeDto, Class<BlueprintTypeDto> blueprintTypeDtoClass);

    lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto map(InvTypeBasicInfoDto invTypeBasicInfoDto, Class<lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto> itemTypeDtoClass);

    CharacterSheetDto map(ApiCharacterSheetResult apiCharacterSheetResult, Class<CharacterSheetDto> characterSheetDtoClass);

    IndustryJobDto map(ApiIndustryJobRow apiIndustryJobRow, Class<IndustryJobDto> industryJobDtoClass);

    AccountCharacterDto map(ApiAccountCharacterRow apiAccountCharacterRow, Class<AccountCharacterDto> accountCharacterDtoClass);

    AccountBalanceDto map(ApiAccountBalanceRow apiAccountBalanceRow, Class<AccountBalanceDto> accountBalanceDtoClass);

    CorporationSheetDto map(ApiCorporationSheetResult apiCorporationSheetResult, Class<CorporationSheetDto> corporationSheetDtoClass);

    MarketStatDto map(MarketStatType marketStatType, Class<MarketStatDto> marketStatDtoClass);

    ItemPriceDto map(ItemPriceType itemPriceType, Class<ItemPriceDto> itemPriceDtoClass);

    CalculationDto map(Calculation calculation, Class<CalculationDto> calculationDtoClass);

    SchematicItemDto map(PlanetSchematicDto planetSchematicDto, Class<SchematicItemDto> schematicItemDtoClass);

    UsedBlueprintDto map(UsedBlueprint usedBlueprint, Class<UsedBlueprintDto> usedBlueprintDtoClass);

    InventedBlueprintDto map(InventedBlueprint inventedBlueprint, Class<InventedBlueprintDto> inventedBlueprintDtoClass);

    UsedSchematicDto map(UsedSchematic usedSchematic, Class<UsedSchematicDto> usedSchematicDtoClass);

}
