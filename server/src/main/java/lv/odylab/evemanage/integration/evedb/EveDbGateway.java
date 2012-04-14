package lv.odylab.evemanage.integration.evedb;

import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.integration.evedb.dto.*;

import java.util.List;

public interface EveDbGateway {

    BlueprintTypeDto getBlueprintTypeByTypeID(Long typeID) throws EveDbException;

    BlueprintDetailsDto getBlueprintDetailsForTypeID(Long typeID) throws EveDbException;

    BlueprintDetailsDto getBlueprintDetailsForTypeName(String typeName) throws EveDbException, InvalidNameException;

    List<TypeMaterialDto> getBaseMaterialsForTypeID(Long typeID) throws EveDbException;

    List<TypeRequirementDto> getExtraMaterialsForTypeID(Long typeID) throws EveDbException;

    ItemTypeDto getItemTypeDtoByName(String itemTypeName) throws EveDbException, InvalidItemTypeException;

    List<ItemTypeDto> lookupType(String query) throws EveDbException;

    List<ItemTypeDto> lookupBlueprintType(String query) throws EveDbException;

    Long getTypeID(String typeName) throws EveDbException, InvalidNameException;

    String getTypeName(Long typeID) throws EveDbException, InvalidItemTypeException;

    String getEveDbVersion() throws EveDbException;

}
