package lv.odylab.evemanage.service.blueprint;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.repackaged.Base64;
import lv.odylab.evemanage.application.EveManageDtoMapper;
import lv.odylab.evemanage.application.background.blueprint.AddBlueprintTaskLauncher;
import lv.odylab.evemanage.application.exception.CsvImportException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.EveManageSecurityException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.SharingLevel;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.eve.ApiKey;
import lv.odylab.evemanage.domain.eve.ApiKeyDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import lv.odylab.evemanage.integration.eveapi.EveApiGateway;
import lv.odylab.evemanage.integration.eveapi.dto.IndustryJobDto;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import lv.odylab.evemanage.security.EveManageSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class BlueprintManagementServiceImpl implements BlueprintManagementService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageSecurityManager securityManager;
    private final EveManageDtoMapper mapper;
    private final EveApiGateway eveApiGateway;
    private final EveDbGateway eveDbGateway;
    private final UserDao userDao;
    private final ApiKeyDao apiKeyDao;
    private final CharacterDao characterDao;
    private final BlueprintDao blueprintDao;
    private final AddBlueprintTaskLauncher addBlueprintTaskLauncher;

    @Inject
    public BlueprintManagementServiceImpl(EveManageSecurityManager securityManager, EveManageDtoMapper mapper, EveApiGateway eveApiGateway, EveDbGateway eveDbGateway, UserDao userDao, ApiKeyDao apiKeyDao, CharacterDao characterDao, BlueprintDao blueprintDao, AddBlueprintTaskLauncher addBlueprintTaskLauncher) {
        this.securityManager = securityManager;
        this.mapper = mapper;
        this.eveApiGateway = eveApiGateway;
        this.eveDbGateway = eveDbGateway;
        this.userDao = userDao;
        this.apiKeyDao = apiKeyDao;
        this.characterDao = characterDao;
        this.blueprintDao = blueprintDao;
        this.addBlueprintTaskLauncher = addBlueprintTaskLauncher;
    }

    @Override
    public Blueprint createBlueprint(String blueprintTypeName, Integer meLevel, Integer peLevel, Key<User> userKey) throws EveDbException, InvalidNameException {
        Long blueprintTypeID = eveDbGateway.getTypeID(blueprintTypeName);
        return createBlueprint(blueprintTypeID, null, meLevel, peLevel, null, SharingLevel.PERSONAL.toString(), userKey);
    }

    @Override
    public Blueprint createBlueprint(Long blueprintTypeID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel, Key<User> userKey) throws EveDbException {
        BlueprintTypeDto blueprintTypeDto = eveDbGateway.getBlueprintTypeByTypeID(blueprintTypeID);
        Blueprint blueprint = new Blueprint();
        blueprint.setUser(userKey);
        blueprint.setItemID(itemID);
        blueprint.setItemTypeID(blueprintTypeID);
        blueprint.setItemTypeName(blueprintTypeDto.getBlueprintTypeName());
        blueprint.setProductTypeID(blueprintTypeDto.getProductTypeID());
        blueprint.setProductTypeName(blueprintTypeDto.getProductTypeName());
        blueprint.setProductGraphicIcon(blueprintTypeDto.getProductGraphicIcon());
        blueprint.setProductTypeCategoryID(blueprintTypeDto.getProductCategoryID());
        blueprint.setProductivityLevel(peLevel);
        blueprint.setMaterialLevel(meLevel);
        if (attachedCharacterID != null) {
            Character character = characterDao.getByCharacterID(attachedCharacterID, userKey);
            blueprint.setAttachedCharacterInfo(mapper.map(character, CharacterInfo.class));
        } else {
            blueprint.setAttachedCharacterInfo(null);
        }
        blueprint.setSharingLevel(sharingLevel);
        blueprint.setCreatedDate(new Date());
        blueprint.setUpdatedDate(new Date());
        blueprintDao.put(blueprint, userKey);
        return blueprint;
    }

    @Override
    public BlueprintDetailsDto getBlueprintDetails(Long blueprintTypeID) throws EveDbException {
        return eveDbGateway.getBlueprintDetailsForTypeID(blueprintTypeID);
    }

    @Override
    public BlueprintDetailsDto getBlueprintDetails(String blueprintTypeName) throws EveDbException, InvalidNameException {
        return eveDbGateway.getBlueprintDetailsForTypeName(blueprintTypeName);
    }

    @Override
    public List<TypeMaterialDto> getTypeMaterials(Long typeID) throws EveDbException {
        return eveDbGateway.getBaseMaterialsForTypeID(typeID);
    }

    @Override
    public List<TypeRequirementDto> getTypeRequirements(Long typeID) throws EveDbException {
        return eveDbGateway.getExtraMaterialsForTypeID(typeID);
    }

    @Override
    public void put(Blueprint blueprint, Key<User> userKey) {
        blueprintDao.put(blueprint, userKey);
    }

    @Override
    public List<Blueprint> getBlueprints(Key<User> userKey) {
        return blueprintDao.getAll(userKey);
    }

    @Override
    public List<Blueprint> getCorporationBlueprints(Key<User> userKey) {
        User user = userDao.get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo == null || mainCharacterInfo.getCorporationID() == null) {
            return Collections.emptyList();
        }
        return blueprintDao.getAllForCorporationID(mainCharacterInfo.getCorporationID());
    }

    @Override
    public List<Blueprint> getAllianceBlueprints(Key<User> userKey) {
        User user = userDao.get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo == null || mainCharacterInfo.getAllianceID() == null) {
            return Collections.emptyList();
        }
        return blueprintDao.getAllForAllianceID(mainCharacterInfo.getAllianceID());
    }

    @Override
    public Blueprint saveBlueprint(Long blueprintID, Long itemID, Integer meLevel, Integer peLevel, Long attachedCharacterID, String sharingLevel, Key<User> userKey) {
        Blueprint blueprint = blueprintDao.get(blueprintID, userKey);
        blueprint.setItemID(itemID);
        blueprint.setMaterialLevel(meLevel);
        blueprint.setProductivityLevel(peLevel);
        if (attachedCharacterID != null) {
            Character character = characterDao.getByCharacterID(attachedCharacterID, userKey);
            blueprint.setAttachedCharacterInfo(mapper.map(character, CharacterInfo.class));
        } else {
            blueprint.setAttachedCharacterInfo(null);
        }
        blueprint.setSharingLevel(sharingLevel);
        blueprint.setUpdatedDate(new Date());
        blueprintDao.put(blueprint, userKey);
        return blueprint;
    }

    @Override
    public void deleteBlueprint(Long blueprintID, Key<User> userKey) {
        Blueprint blueprint = blueprintDao.get(blueprintID, userKey);
        blueprintDao.delete(blueprint);
    }

    @Override
    public void importBlueprintsFromXml(String importXml, Long attachedCharacterID, String sharingLevel, Key<User> userKey) throws EveApiException {
        proceedIndustryJobImport(eveApiGateway.importFromIndustryJobXml(importXml), attachedCharacterID, sharingLevel, userKey);
    }

    @Override
    public void importBlueprintsFromCsv(String importCsv, Long attachedCharacterID, String sharingLevel, Key<User> userKey) {
        String[] importCsvStrings = importCsv.trim().split("\n");
        String[] columns = importCsvStrings[0].split(",");
        List<String> possibleColumns = Arrays.asList("name", "typeid", "me", "pe", "itemid");
        Map<String, Integer> columnMap = new HashMap<String, Integer>();
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            String columnName = column.trim().toLowerCase();
            if (possibleColumns.contains(columnName)) {
                columnMap.put(columnName, i);
            }
        }

        for (int i = 1; i < importCsvStrings.length; i++) {
            String[] tokens = importCsvStrings[i].split(",");
            String name = null;
            Long typeID = null;
            Integer me = 0;
            Integer pe = 0;
            Long itemID = null;
            if (columnMap.containsKey("name")) {
                name = tokens[columnMap.get("name")];
            }
            if (columnMap.containsKey("typeid")) {
                typeID = Long.valueOf(tokens[columnMap.get("typeid")].trim());
            }
            if (columnMap.containsKey("me")) {
                me = Integer.valueOf(tokens[columnMap.get("me")].trim());
            }
            if (columnMap.containsKey("pe")) {
                pe = Integer.valueOf(tokens[columnMap.get("pe")].trim());
            }
            if (columnMap.containsKey("itemid")) {
                itemID = Long.valueOf(tokens[columnMap.get("itemid")].trim());
            }

            if (name != null) {
                addBlueprintTaskLauncher.launch(userKey.getId(), name, itemID, me, pe, attachedCharacterID, sharingLevel);
            } else if (typeID != null) {
                addBlueprintTaskLauncher.launch(userKey.getId(), typeID, itemID, me, pe, attachedCharacterID, sharingLevel);
            } else {
                throw new CsvImportException("noNameAndTypeID");
            }
        }
    }

    @Override
    public void importBlueprintsUsingOneTimeFullApiKey(String fullApiKey, Long userID, Long characterID, String level, Long attachedCharacterID, String sharingLevel, Key<User> userKey) throws EveApiException {
        if ("CHARACTER".equals(level)) {
            proceedIndustryJobImport(eveApiGateway.getCharacterIndustryJobs(fullApiKey, userID, characterID), attachedCharacterID, sharingLevel, userKey);
        } else {
            proceedIndustryJobImport(eveApiGateway.getCorporationIndustryJobs(fullApiKey, userID, characterID), attachedCharacterID, sharingLevel, userKey);
        }
    }

    @Override
    public void importBlueprintsUsingFullApiKey(Long characterID, String level, Long attachedCharacterID, String sharingLevel, Key<User> userKey) throws EveApiException {
        ApiKey fullApiKey = apiKeyDao.getFullForCharacterID(characterID, userKey);
        String fullApiKeyString;
        try {
            byte[] decodedApiKeyBytes = Base64.decode(fullApiKey.getEncodedApiKeyString());
            byte[] decryptedApiKeyBytes = securityManager.decrypt(decodedApiKeyBytes);
            fullApiKeyString = new String(decryptedApiKeyBytes);
        } catch (IOException e) {
            throw new EveManageSecurityException(e);
        }

        if ("CHARACTER".equals(level)) {
            proceedIndustryJobImport(eveApiGateway.getCharacterIndustryJobs(fullApiKeyString, fullApiKey.getApiKeyUserID(), characterID), attachedCharacterID, sharingLevel, userKey);
        } else {
            proceedIndustryJobImport(eveApiGateway.getCorporationIndustryJobs(fullApiKey.getEncodedApiKeyString(), fullApiKey.getApiKeyUserID(), characterID), attachedCharacterID, sharingLevel, userKey);
        }
    }

    private void proceedIndustryJobImport(List<IndustryJobDto> industryJobDtos, Long attachedCharacterID, String sharingLevel, Key<User> userKey) {
        List<Blueprint> existingBlueprints = blueprintDao.getAll(userKey);
        Set<Long> existingItemIDs = new HashSet<Long>();
        for (Blueprint blueprint : existingBlueprints) {
            Long itemID = blueprint.getItemID();
            if (itemID != null) {
                existingItemIDs.add(itemID);
            }
        }

        Set<Long> newItemIDs = new HashSet<Long>();
        for (IndustryJobDto industryJobDto : industryJobDtos) {
            Long itemID = industryJobDto.getInstalledItemID();
            if (industryJobDto.getActivityID() == 1 &&
                    industryJobDto.getInstalledItemLicensedProductionRunsRemaining() == -1 &&
                    !newItemIDs.contains(itemID) &&
                    !existingItemIDs.contains(itemID)) {
                addBlueprintTaskLauncher.launch(userKey.getId(), industryJobDto.getInstalledItemTypeID(), industryJobDto.getInstalledItemID(), industryJobDto.getInstalledItemMaterialLevel(), industryJobDto.getInstalledItemProductivityLevel(), attachedCharacterID, sharingLevel);
                newItemIDs.add(itemID);
            }
        }
    }
}