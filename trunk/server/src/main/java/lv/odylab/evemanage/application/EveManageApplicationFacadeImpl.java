package lv.odylab.evemanage.application;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.SharingLevel;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;
import lv.odylab.evemanage.security.EveManageSecurityManager;
import lv.odylab.evemanage.service.blueprint.BlueprintManagementService;
import lv.odylab.evemanage.service.calculation.CalculationService;
import lv.odylab.evemanage.service.eve.EveManagementService;
import lv.odylab.evemanage.service.priceset.PriceSetManagementService;
import lv.odylab.evemanage.service.user.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EveManageApplicationFacadeImpl implements EveManageApplicationFacade {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserManagementService userManagementService;
    private final BlueprintManagementService blueprintManagementService;
    private final PriceSetManagementService priceSetManagementService;
    private final EveManagementService eveManagementService;
    private final CalculationService calculationService;
    private final EveDbGateway eveDbGateway;
    private final String eveManageVersion;

    @Inject
    public EveManageApplicationFacadeImpl(EveManageSecurityManager eveManageSecurityManager,
                                          UserManagementService userManagementService,
                                          BlueprintManagementService blueprintManagementService,
                                          PriceSetManagementService priceSetManagementService,
                                          EveManagementService eveManagementService,
                                          CalculationService calculationService,
                                          EveDbGateway eveDbGateway,
                                          @Named("eveManage.version") String eveManageVersion) {
        this.userManagementService = userManagementService;
        this.blueprintManagementService = blueprintManagementService;
        this.priceSetManagementService = priceSetManagementService;
        this.eveManagementService = eveManagementService;
        this.calculationService = calculationService;
        this.eveDbGateway = eveDbGateway;
        this.eveManageVersion = eveManageVersion;

        eveManageSecurityManager.initializeSecuritySystem();
    }

    @Override
    public LoginDto login(String requestUri, String locale) {
        return userManagementService.login(requestUri, locale);
    }

    @Override
    public User getCurrentUser() {
        return userManagementService.getUser(getCurrentUserKey());
    }

    @Override
    public List<String> getAvailableSharingLevels() {
        return Arrays.asList(SharingLevel.PERSONAL.toString(), SharingLevel.CORPORATION.toString(), SharingLevel.ALLIANCE.toString());
    }

    @Override
    public Blueprint createBlueprint(String blueprintTypeName, Integer meLevel, Integer peLevel) throws EveDbException, InvalidNameException {
        return blueprintManagementService.createBlueprint(blueprintTypeName, meLevel, peLevel, getCurrentUserKey());
    }

    @Override
    public List<Blueprint> getBlueprints() {
        return blueprintManagementService.getBlueprints(getCurrentUserKey());
    }

    @Override
    public List<Blueprint> getCorporationBlueprints() {
        return blueprintManagementService.getCorporationBlueprints(getCurrentUserKey());
    }

    @Override
    public List<Blueprint> getAllianceBlueprints() {
        return blueprintManagementService.getAllianceBlueprints(getCurrentUserKey());
    }

    @Override
    public Blueprint saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel) {
        return blueprintManagementService.saveBlueprint(blueprintID, itemID, meLevel, peLevel, attachedCharacterID, sharingLevel, getCurrentUserKey());
    }

    @Override
    public void deleteBlueprint(Long blueprintID) {
        blueprintManagementService.deleteBlueprint(blueprintID, getCurrentUserKey());
    }

    @Override
    public BlueprintDetailsDto getBlueprintDetails(Long blueprintTypeID) throws EveDbException {
        return blueprintManagementService.getBlueprintDetails(blueprintTypeID);
    }

    @Override
    public void importBlueprints(String importXml, Long attachedCharacterID, String sharingLevel) throws EveApiException {
        blueprintManagementService.importBlueprints(importXml, attachedCharacterID, sharingLevel, getCurrentUserKey());
    }

    @Override
    public List<PriceSet> getPriceSets() {
        return priceSetManagementService.getPriceSets(getCurrentUserKey());
    }

    @Override
    public List<PriceSet> getCorporationPriceSets() {
        return priceSetManagementService.getCorporationPriceSets(getCurrentUserKey());
    }

    @Override
    public List<PriceSet> getAlliancePriceSets() {
        return priceSetManagementService.getAlliancePriceSets(getCurrentUserKey());
    }

    @Override
    public PriceSet getPriceSet(Long priceSetID) {
        return priceSetManagementService.getPriceSet(priceSetID, getCurrentUserKey());
    }

    @Override
    public PriceSet getCorporationPriceSet(Long priceSetID) {
        return priceSetManagementService.getCorporationPriceSet(priceSetID, getCurrentUserKey());
    }

    @Override
    public PriceSet getAlliancePriceSet(Long priceSetID) {
        return priceSetManagementService.getAlliancePriceSet(priceSetID, getCurrentUserKey());
    }

    @Override
    public PriceSet createPriceSet(String priceSetName) throws InvalidNameException {
        return priceSetManagementService.createPriceSet(priceSetName, getCurrentUserKey());
    }

    @Override
    public void renamePriceSet(Long priceSetID, String priceSetName) throws InvalidNameException {
        priceSetManagementService.renamePriceSet(priceSetID, priceSetName, getCurrentUserKey());
    }

    @Override
    public void savePriceSet(Long priceSetID, Set<PriceSetItem> priceSetItems, String sharingLevel, Long attachedCharacterID) {
        priceSetManagementService.savePriceSet(priceSetID, priceSetItems, sharingLevel, attachedCharacterID, getCurrentUserKey());
    }

    @Override
    public List<PriceSetItem> fetchPricesFromEveCentral(List<PriceSetItem> priceSetItems) throws EveCentralApiException {
        return priceSetManagementService.fetchPricesFromEveCentral(priceSetItems);
    }

    @Override
    public Map<Long, String> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs) throws EveCentralApiException {
        return priceSetManagementService.fetchPricesFromEveCentralForTypeIDs(typeIDs);
    }

    @Override
    public List<PriceSetItem> fetchPricesFromEveMetrics(List<PriceSetItem> priceSetItems) throws EveMetricsApiException {
        return priceSetManagementService.fetchPricesFromEveMetrics(priceSetItems);
    }

    @Override
    public Map<Long, String> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs) throws EveMetricsApiException {
        return priceSetManagementService.fetchPricesFromEveMetricsForTypeIDs(typeIDs);
    }

    @Override
    public void deletePriceSet(Long priceSetID) {
        priceSetManagementService.deletePriceSet(priceSetID, getCurrentUserKey());
    }

    @Override
    public List<lv.odylab.evemanage.domain.eve.Character> getCharacters() {
        return eveManagementService.getCharacters(getCurrentUserKey());
    }

    @Override
    public List<CharacterNameDto> getAvailableNewCharacterNames() {
        return eveManagementService.getAvailableNewCharacterNames(getCurrentUserKey());
    }

    @Override
    public List<CharacterNameDto> getCharacterNames() {
        return eveManagementService.getCharacterNames(getCurrentUserKey());
    }

    @Override
    public void addCharacter(Long characterID) throws EveApiException {
        eveManagementService.createCharacter(characterID, getCurrentUserKey());
    }

    @Override
    public void deleteCharacter(Long characterID) {
        eveManagementService.deleteCharacter(characterID, getCurrentUserKey());
    }

    @Override
    public void setMainCharacter(String characterName) {
        userManagementService.setMainCharacter(characterName, getCurrentUserKey());
    }

    @Override
    public List<ApiKey> getApiKeys() {
        return eveManagementService.getApiKeys(getCurrentUserKey());
    }

    @Override
    public void createApiKey(String apiKeyString, Long apiKeyUserID) throws EveApiException, ApiKeyShouldBeRemovedException {
        eveManagementService.createApiKey(apiKeyString, apiKeyUserID, getCurrentUserKey());
    }

    @Override
    public void deleteApiKey(Long apiKeyID) {
        eveManagementService.deleteApiKey(apiKeyID, getCurrentUserKey());
    }

    @Override
    public ItemTypeDto getItemTypeByName(String itemTypeName) throws EveDbException, InvalidItemTypeException {
        return eveDbGateway.getItemTypeDtoByName(itemTypeName);
    }

    @Override
    public List<ItemTypeDto> lookupType(String query) throws EveDbException {
        return eveDbGateway.lookupType(query);
    }

    @Override
    public List<ItemTypeDto> lookupBlueprintType(String query) throws EveDbException {
        return eveDbGateway.lookupBlueprintType(query);
    }

    @Override
    public Calculation getCalculation(String blueprintName) throws EveDbException, InvalidNameException {
        return calculationService.getCalculation(blueprintName);
    }

    @Override
    public Calculation getCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        return calculationService.getCalculation(pathNodes, blueprintName);
    }

    @Override
    public String getEveManageVersion() {
        return eveManageVersion;
    }

    @Override
    public String getEveDbVersion() throws EveDbException {
        return eveDbGateway.getEveDbVersion();
    }

    private Key<User> getCurrentUserKey() {
        return userManagementService.getCurrentUserKey();
    }
}