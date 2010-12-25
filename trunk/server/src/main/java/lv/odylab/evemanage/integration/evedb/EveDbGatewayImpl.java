package lv.odylab.evemanage.integration.evedb;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lv.odylab.appengine.aspect.Caching;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.evedb.client.BadRequestException;
import lv.odylab.evedb.client.EveDbWsClient;
import lv.odylab.evedb.client.EveDbWsClientImpl;
import lv.odylab.evedb.client.HttpRequestSenderImpl;
import lv.odylab.evedb.client.HttpRequestSenderWithRetryImpl;
import lv.odylab.evedb.rpc.dto.InvBlueprintTypeDto;
import lv.odylab.evedb.rpc.dto.InvTypeBasicInfoDto;
import lv.odylab.evedb.rpc.dto.InvTypeMaterialDto;
import lv.odylab.evedb.rpc.dto.PlanetSchematicDto;
import lv.odylab.evedb.rpc.dto.RamTypeRequirementDto;
import lv.odylab.evemanage.application.EveManageDtoMapper;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.SchematicItemDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Logging
public class EveDbGatewayImpl implements EveDbGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageDtoMapper mapper;
    private final EveDbWsClient client;

    @Inject
    public EveDbGatewayImpl(EveManageDtoMapper mapper,
                            @Named("eveDb.url") String eveDbUrl) {
        this.mapper = mapper;
        this.client = new EveDbWsClientImpl(eveDbUrl, new HttpRequestSenderWithRetryImpl(5, new HttpRequestSenderImpl()));
    }

    @Override
    @Caching
    public BlueprintTypeDto getBlueprintTypeByTypeID(Long typeID) throws EveDbException {
        try {
            InvBlueprintTypeDto invBlueprintTypeDto = client.getBlueprintTypeByTypeID(typeID);
            return mapper.map(invBlueprintTypeDto, BlueprintTypeDto.class);
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public BlueprintDetailsDto getBlueprintDetailsForTypeID(Long typeID) throws EveDbException {
        try {
            lv.odylab.evedb.rpc.dto.BlueprintDetailsDto blueprintDetailsDto = client.getBlueprintDetailsForTypeID(typeID);
            return mapper.map(blueprintDetailsDto, BlueprintDetailsDto.class);
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public BlueprintDetailsDto getBlueprintDetailsForTypeName(String typeName) throws EveDbException, InvalidNameException {
        try {
            if (typeName.length() == 0) {
                throw new InvalidNameException(typeName, ErrorCode.NAME_CANNOT_BE_EMPTY);
            }
            lv.odylab.evedb.rpc.dto.BlueprintDetailsDto blueprintDetailsDto = client.getBlueprintDetailsForTypeName(typeName);
            return mapper.map(blueprintDetailsDto, BlueprintDetailsDto.class);
        } catch (BadRequestException e) {
            logger.error("Caught BadRequestException", e);
            throw new InvalidNameException(typeName, ErrorCode.INVALID_NAME);
        } catch (RuntimeException e) {
            logger.error("Caught RuntimeException", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public List<TypeMaterialDto> getBaseMaterialsForTypeID(Long typeID) throws EveDbException {
        try {
            List<InvTypeMaterialDto> invTypeMaterialDtos = client.getBaseMaterialsForTypeID(typeID);
            List<TypeMaterialDto> typeMaterialDtos = new ArrayList<TypeMaterialDto>();
            for (InvTypeMaterialDto invTypeMaterialDto : invTypeMaterialDtos) {
                typeMaterialDtos.add(mapper.map(invTypeMaterialDto, TypeMaterialDto.class));
            }
            return typeMaterialDtos;
        } catch (BadRequestException e) {
            logger.warn("Caught BadRequestException", e.getMessage());
            return new ArrayList<TypeMaterialDto>();
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public List<TypeRequirementDto> getExtraMaterialsForTypeID(Long typeID) throws EveDbException {
        try {
            List<RamTypeRequirementDto> ramTypeRequirementDtos = client.getExtraMaterialsForTypeID(typeID);
            List<TypeRequirementDto> typeRequirementDtos = new ArrayList<TypeRequirementDto>();
            for (RamTypeRequirementDto ramTypeRequirementDto : ramTypeRequirementDtos) {
                typeRequirementDtos.add(mapper.map(ramTypeRequirementDto, TypeRequirementDto.class));
            }
            return typeRequirementDtos;
        } catch (BadRequestException e) {
            logger.warn("Caught BadRequestException", e.getMessage());
            return new ArrayList<TypeRequirementDto>();
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public ItemTypeDto getItemTypeDtoByName(String itemTypeName) throws EveDbException, InvalidItemTypeException {
        try {
            if (itemTypeName.length() == 0) {
                throw new InvalidItemTypeException(itemTypeName, ErrorCode.NAME_CANNOT_BE_EMPTY);
            }
            InvTypeBasicInfoDto invTypeBasicInfoDto = client.getTypeBasicInfoByTypeName(itemTypeName);
            return invTypeBasicInfoDto == null ? null : mapper.map(invTypeBasicInfoDto, ItemTypeDto.class);
        } catch (BadRequestException e) {
            logger.error("Caught BadRequestException", e.getMessage());
            throw new InvalidItemTypeException(e.getMessage(), ErrorCode.INVALID_ITEM_TYPE);
        } catch (RuntimeException e) {
            logger.error("Caught RuntimeException", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public List<ItemTypeDto> lookupType(String query) throws EveDbException {
        try {
            List<InvTypeBasicInfoDto> invTypeBasicInfoDtoList = client.lookupType(query);
            List<ItemTypeDto> itemTypeDtos = new ArrayList<ItemTypeDto>();
            for (InvTypeBasicInfoDto invTypeBasicInfoDto : invTypeBasicInfoDtoList) {
                itemTypeDtos.add(mapper.map(invTypeBasicInfoDto, ItemTypeDto.class));
            }
            return itemTypeDtos;
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public List<ItemTypeDto> lookupBlueprintType(String query) throws EveDbException {
        try {
            List<InvTypeBasicInfoDto> invTypeBasicInfoDtoList = client.lookupBlueprintType(query);
            List<ItemTypeDto> itemTypeDtos = new ArrayList<ItemTypeDto>();
            for (InvTypeBasicInfoDto invTypeBasicInfoDto : invTypeBasicInfoDtoList) {
                itemTypeDtos.add(mapper.map(invTypeBasicInfoDto, ItemTypeDto.class));
            }
            return itemTypeDtos;
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public List<SchematicItemDto> getPlanetSchematicForTypeName(String typeName) throws EveDbException, InvalidItemTypeException {
        try {
            List<PlanetSchematicDto> planetSchematicDtos = client.getPlanetarySchematicForTypeName(typeName);
            List<SchematicItemDto> schematicItemDtos = new ArrayList<SchematicItemDto>();
            for (PlanetSchematicDto planetSchematicDto : planetSchematicDtos) {
                schematicItemDtos.add(mapper.map(planetSchematicDto, SchematicItemDto.class));
            }
            return schematicItemDtos;
        } catch (BadRequestException e) {
            logger.error("Caught BadRequestException", e.getMessage());
            throw new InvalidItemTypeException(e.getMessage(), ErrorCode.INVALID_ITEM_TYPE);
        } catch (RuntimeException e) {
            logger.error("Caught RuntimeException", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public Long getTypeID(String typeName) throws EveDbException, InvalidNameException {
        try {
            if (typeName.length() == 0) {
                throw new InvalidNameException(typeName, ErrorCode.NAME_CANNOT_BE_EMPTY);
            }
            return client.getTypeNameToTypeID(typeName);
        } catch (BadRequestException e) {
            logger.error("Caught BadRequestException", e);
            throw new InvalidNameException(typeName, ErrorCode.INVALID_NAME);
        } catch (RuntimeException e) {
            logger.error("Caught RuntimeException", e);
            throw new EveDbException(e);
        }
    }

    @Override
    @Caching
    public String getTypeName(Long typeID) throws EveDbException, InvalidItemTypeException {
        try {
            return client.getTypeIdToTypeName(typeID);
        } catch (BadRequestException e) {
            logger.error("Caught BadRequestException", e);
            throw new InvalidItemTypeException(String.valueOf(typeID), ErrorCode.INVALID_NAME);
        } catch (RuntimeException e) {
            logger.error("Caught RuntimeException", e);
            throw new EveDbException(e);
        }
    }

    @Override
    public String getEveDbVersion() throws EveDbException {
        try {
            return client.getVersion();
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new EveDbException(e);
        }
    }
}