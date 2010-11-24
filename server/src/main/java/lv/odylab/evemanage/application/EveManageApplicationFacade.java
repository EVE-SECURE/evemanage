package lv.odylab.evemanage.application;

import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EveManageApplicationFacade {

    LoginDto login(String requestUri, String locale);

    User getCurrentUser();

    List<String> getAvailableSharingLevels();

    List<ApiKey> getFullApiKeys();

    Blueprint createBlueprint(String blueprintTypeName, Integer meLevel, Integer peLevel) throws EveDbException, InvalidNameException;

    List<Blueprint> getBlueprints();

    List<Blueprint> getCorporationBlueprints();

    List<Blueprint> getAllianceBlueprints();

    Blueprint saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel);

    void deleteBlueprint(Long blueprintID);

    BlueprintDetailsDto getBlueprintDetails(Long blueprintTypeID) throws EveDbException;

    void importBlueprintsFromXml(String importXml, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    void importBlueprintsFromCsv(String importCsv, Long attachedCharacterID, String sharingLevel);

    void importBlueprintsUsingOneTimeFullApiKey(String fullApiKey, Long userID, Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    void importBlueprintsUsingFullApiKey(Long characterID, String level, Long attachedCharacterID, String sharingLevel) throws EveApiException;

    List<PriceSet> getPriceSets();

    List<PriceSet> getCorporationPriceSets();

    List<PriceSet> getAlliancePriceSets();

    PriceSet getPriceSet(Long priceSetID);

    PriceSet getCorporationPriceSet(Long priceSetID);

    PriceSet getAlliancePriceSet(Long priceSetID);

    PriceSet createPriceSet(String priceSetName) throws InvalidNameException;

    void renamePriceSet(Long priceSetID, String priceSetName) throws InvalidNameException;

    void savePriceSet(Long priceSetID, Set<PriceSetItem> priceSetItems, String sharingLevel, Long attachedCharacterID);

    List<PriceSetItem> fetchPricesFromEveCentral(List<PriceSetItem> priceSetItems) throws EveCentralApiException;

    Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs) throws EveCentralApiException;

    List<PriceSetItem> fetchPricesFromEveMetrics(List<PriceSetItem> priceSetItems) throws EveMetricsApiException;

    Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs) throws EveMetricsApiException;

    void deletePriceSet(Long priceSetID);

    List<Character> getCharacters();

    List<CharacterNameDto> getAvailableNewCharacterNames();

    List<CharacterNameDto> getCharacterNames();

    void addCharacter(Long characterID) throws EveApiException;

    void deleteCharacter(Long characterID);

    void setMainCharacter(String characterName);

    List<ApiKey> getApiKeys();

    void createApiKey(String apiKeyString, Long apiKeyUserID) throws EveApiException, ApiKeyShouldBeRemovedException;

    void deleteApiKey(Long apiKeyID);

    ItemTypeDto getItemTypeByName(String itemTypeName) throws EveDbException, InvalidItemTypeException;

    List<ItemTypeDto> lookupType(String query) throws EveDbException;

    List<ItemTypeDto> lookupBlueprintType(String query) throws EveDbException;

    Calculation getCalculation(String blueprintName) throws EveDbException, InvalidNameException;

    Calculation getCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException;

    String getEveManageVersion();

    String getEveDbVersion() throws EveDbException;

}
