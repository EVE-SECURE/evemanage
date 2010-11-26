package lv.odylab.evemanage.application;

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
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetItemDto;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EveManageClientFacade {

    LoginDto login(String requestUri, String locale);

    UserDto getCurrentUser();

    List<CharacterNameDto> getAvailableAttachedCharacterNames();

    List<String> getAvailableSharingLevels();

    List<ApiKeyDto> getFullApiKeys();

    BlueprintDto createBlueprint(String blueprintTypeName, Integer meLevel, Integer peLevel) throws EveDbException, InvalidNameException;

    List<BlueprintDto> getBlueprints();

    List<BlueprintDto> getCorporationBlueprints();

    List<BlueprintDto> getAllianceBlueprints();

    BlueprintDto saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel);

    void deleteBlueprint(Long blueprintID);

    BlueprintDetailsDto getBlueprintDetails(Long blueprintTypeID) throws EveDbException;

    void importBlueprintsFromXml(String importXml, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    void importBlueprintsFromCsv(String importCsv, Long attachedCharacterID, String sharingLevel);

    void importBlueprintsUsingOneTimeFullApiKey(String fullApiKey, Long userID, Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    void importBlueprintsUsingFullApiKey(Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    PriceSetDto getPriceSet(Long priceSetID);

    PriceSetDto getCorporationPriceSet(Long priceSetID);

    PriceSetDto getAlliancePriceSet(Long priceSetID);

    List<PriceSetNameDto> getPriceSetNames();

    List<PriceSetNameDto> getCorporationPriceSetNames();

    List<PriceSetNameDto> getAlliancePriceSetNames();

    PriceSetDto createPriceSet(String priceSetName) throws InvalidNameException;

    void renamePriceSet(Long priceSetID, String priceSetName) throws InvalidNameException;

    void savePriceSet(Long priceSetID, List<PriceSetItemDto> priceSetItemDtos, String sharingLevel, Long attachedCharacterID) throws InvalidPriceException;

    List<PriceSetItemDto> fetchPricesFromEveCentral(List<PriceSetItemDto> priceSetItemDtos) throws InvalidPriceException, EveCentralApiException;

    Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs) throws EveCentralApiException;

    List<PriceSetItemDto> fetchPricesFromEveMetrics(List<PriceSetItemDto> priceSetItemDtos) throws InvalidPriceException, EveMetricsApiException;

    Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs) throws EveMetricsApiException;

    void deletePriceSet(Long priceSetID);

    List<CharacterDto> getCharacters();

    List<CharacterNameDto> getAvailableNewCharacterNames();

    List<CharacterNameDto> getCharacterNames();

    void addCharacter(Long characterID) throws EveApiException;

    void deleteCharacter(Long characterID);

    void setMainCharacter(String characterName);

    List<ApiKeyDto> getApiKeys();

    void createApiKey(Long apiKeyUserID, String apiKeyString) throws EveApiException, ApiKeyShouldBeRemovedException;

    void deleteApiKey(Long apiKeyID);

    lv.odylab.evemanage.client.rpc.dto.ItemTypeDto getItemTypeByName(String itemTypeName) throws InvalidItemTypeException, EveDbException;

    List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> lookupType(String query) throws EveDbException;

    List<lv.odylab.evemanage.client.rpc.dto.ItemTypeDto> lookupBlueprintType(String query) throws EveDbException;

    CalculationDto getQuickCalculation(String blueprintName) throws EveDbException, InvalidNameException;

    CalculationDto getQuickCalculationForExpression(CalculationExpression calculationExpression) throws EveDbException, InvalidNameException;

    CalculationDto getQuickCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException;

    CalculationDto getQuickCalculation(Long[] pathNodes, Long blueprintProductTypeID) throws EveDbException, InvalidNameException, InvalidItemTypeException;

    String getEveManageVersion();

    String getEveDbVersion() throws EveDbException;

}
