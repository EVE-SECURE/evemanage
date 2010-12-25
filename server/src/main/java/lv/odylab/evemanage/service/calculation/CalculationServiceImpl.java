package lv.odylab.evemanage.service.calculation;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.client.rpc.RationalNumber;
import lv.odylab.evemanage.client.rpc.RationalNumberProductExpression;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.calculation.CalculationItem;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.SchematicItemDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;

import java.util.ArrayList;
import java.util.List;

public class CalculationServiceImpl implements CalculationService {
    private final EveDbGateway eveDbGateway;

    @Inject
    public CalculationServiceImpl(EveDbGateway eveDbGateway) {
        this.eveDbGateway = eveDbGateway;
    }

    @Override
    public Calculation getNewCalculation(String blueprintName) throws EveDbException, InvalidNameException {
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintName);
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        Long[] pathNodes = new Long[]{blueprintTypeDto.getProductTypeID()};
        return getCalculation(pathNodes, blueprintDetailsDto);
    }

    @Override
    public UsedBlueprint useBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintName);
        return createUsedBlueprint(pathNodes, blueprintDetailsDto);
    }

    @Override
    public UsedBlueprint useBlueprint(Long[] pathNodes, Long blueprintProductTypeID) throws InvalidItemTypeException, EveDbException, InvalidNameException {
        String typeName = eveDbGateway.getTypeName(blueprintProductTypeID);
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(typeName + " Blueprint");
        return createUsedBlueprint(pathNodes, blueprintDetailsDto);
    }

    @Override
    public UsedSchematic useSchematic(Long[] pathNodes, String schematicName) throws InvalidItemTypeException, EveDbException {
        List<SchematicItemDto> schematicItemDtos = eveDbGateway.getPlanetSchematicForTypeName(schematicName);
        return createUsedSchematic(pathNodes, schematicItemDtos);
    }

    private Calculation getCalculation(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
        Calculation calculation = new Calculation();
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        calculation.setBlueprintTypeID(blueprintTypeDto.getBlueprintTypeID());
        calculation.setBlueprintTypeName(blueprintTypeDto.getBlueprintTypeName());
        calculation.setProductTypeID(blueprintTypeDto.getProductTypeID());
        calculation.setProductTypeCategoryID(blueprintTypeDto.getProductCategoryID());
        calculation.setProductTypeName(blueprintTypeDto.getProductTypeName());
        calculation.setProductGraphicIcon(blueprintTypeDto.getProductGraphicIcon());
        calculation.setMaterialLevel(0);
        calculation.setProductivityLevel(0);
        calculation.setWasteFactor(blueprintTypeDto.getWasteFactor());
        calculation.setMaxProductionLimit(blueprintTypeDto.getMaxProductionLimit());
        calculation.setProductVolume(blueprintTypeDto.getProductVolume());
        calculation.setProductPortionSize(blueprintTypeDto.getProductPortionSize());
        calculation.setPrice("0.00");
        List<CalculationItem> calculationItems = createCalculationItemsFromBlueprintDetails(pathNodes, blueprintDetailsDto);
        calculation.setCalculationItems(calculationItems);
        return calculation;
    }

    private UsedBlueprint createUsedBlueprint(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
        UsedBlueprint usedBlueprint = new UsedBlueprint();
        usedBlueprint.setMaterialLevel(0);
        usedBlueprint.setProductivityLevel(0);
        List<CalculationItem> calculationItems = createCalculationItemsFromBlueprintDetails(pathNodes, blueprintDetailsDto);
        usedBlueprint.setCalculationItems(calculationItems);
        return usedBlueprint;
    }

    private UsedSchematic createUsedSchematic(Long[] pathNodes, List<SchematicItemDto> schematicItemDtos) {
        UsedSchematic usedSchematic = new UsedSchematic();
        List<CalculationItem> calculationItems = new ArrayList<CalculationItem>();
        for (SchematicItemDto schematicItemDto : schematicItemDtos) {
            PathExpression pathExpression = new PathExpression(pathNodes, schematicItemDto.getRequiredTypeID());
            calculationItems.add(createCalculationItem(pathExpression, schematicItemDto));
        }
        usedSchematic.setCalculationItems(calculationItems);
        return usedSchematic;
    }

    private List<CalculationItem> createCalculationItemsFromBlueprintDetails(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        List<TypeMaterialDto> materialDtos = blueprintDetailsDto.getMaterialDtos();
        List<TypeRequirementDto> requirementDtos = blueprintDetailsDto.getManufacturingRequirementDtos();
        List<CalculationItem> calculationItems = new ArrayList<CalculationItem>();
        for (TypeMaterialDto materialDto : materialDtos) {
            PathExpression pathExpression = new PathExpression(pathNodes, 0, 0, materialDto.getMaterialTypeID());
            calculationItems.add(createCalculationItem(pathExpression, blueprintTypeDto, materialDto));
        }
        for (TypeRequirementDto requirementDto : requirementDtos) {
            if (requirementDto.getRequiredTypeCategoryID() == 16L) { // Skill
                continue;
            }
            PathExpression pathExpression = new PathExpression(pathNodes, requirementDto.getRequiredTypeID());
            calculationItems.add(createCalculationItem(pathExpression, blueprintTypeDto, requirementDto));
        }
        return calculationItems;
    }

    private CalculationItem createCalculationItem(PathExpression pathExpression, BlueprintTypeDto blueprintTypeDto, TypeMaterialDto materialDto) {
        CalculationItem calculationItem = new CalculationItem();
        calculationItem.setPath(pathExpression.getExpression());
        calculationItem.setItemTypeID(materialDto.getMaterialTypeID());
        calculationItem.setItemCategoryID(materialDto.getMaterialTypeCategoryID());
        calculationItem.setItemTypeName(materialDto.getMaterialTypeName());
        calculationItem.setItemTypeIcon(materialDto.getMaterialTypeGraphicIcon());
        calculationItem.setQuantity(materialDto.getQuantity());
        calculationItem.setParentQuantity(1L);
        calculationItem.setPerfectQuantity(materialDto.getQuantity());
        calculationItem.setWasteFactor(blueprintTypeDto.getWasteFactor());
        calculationItem.setDamagePerJob("1.00");
        calculationItem.setPrice("0.00");
        calculationItem.setTotalPrice("0.00");
        calculationItem.setTotalPriceForParent("0.00");
        return calculationItem;
    }

    private CalculationItem createCalculationItem(PathExpression pathExpression, BlueprintTypeDto blueprintTypeDto, TypeRequirementDto requirementDto) {
        CalculationItem calculationItem = new CalculationItem();
        calculationItem.setPath(pathExpression.getExpression());
        calculationItem.setItemTypeID(requirementDto.getRequiredTypeID());
        calculationItem.setItemCategoryID(requirementDto.getRequiredTypeCategoryID());
        calculationItem.setItemTypeName(requirementDto.getRequiredTypeName());
        calculationItem.setItemTypeIcon(requirementDto.getRequiredTypeNameGraphicIcon());
        calculationItem.setQuantity(requirementDto.getQuantity());
        calculationItem.setParentQuantity(1L);
        calculationItem.setPerfectQuantity(requirementDto.getQuantity());
        calculationItem.setWasteFactor(blueprintTypeDto.getWasteFactor());
        calculationItem.setDamagePerJob(requirementDto.getDamagePerJob());
        calculationItem.setPrice("0.00");
        calculationItem.setTotalPrice("0.00");
        calculationItem.setTotalPriceForParent("0.00");
        return calculationItem;
    }

    private CalculationItem createCalculationItem(PathExpression pathExpression, SchematicItemDto schematicItemDto) {
        CalculationItem calculationItem = new CalculationItem();
        calculationItem.setPath(pathExpression.getExpression());
        calculationItem.setItemTypeID(schematicItemDto.getRequiredTypeID());
        calculationItem.setItemCategoryID(43L); // Planetary Commodities
        calculationItem.setItemTypeName(schematicItemDto.getRequiredTypeName());
        calculationItem.setItemTypeIcon(schematicItemDto.getRequiredIcon());
        calculationItem.setQuantity(1L);
        RationalNumberProductExpression quantityMultiplier = new RationalNumberProductExpression();
        RationalNumber rationalNumber = new RationalNumber(schematicItemDto.getRequiredQuantity(), schematicItemDto.getSchematicQuantity());
        quantityMultiplier.addRationalNumber(rationalNumber);
        calculationItem.setQuantityMultiplier(quantityMultiplier.getExpression());
        calculationItem.setParentQuantity(1L);
        calculationItem.setPerfectQuantity(1L);
        calculationItem.setDamagePerJob("1.00");
        calculationItem.setPrice("0.00");
        calculationItem.setTotalPrice("0.00");
        calculationItem.setTotalPriceForParent("0.00");
        return calculationItem;
    }
}
