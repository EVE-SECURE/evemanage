package lv.odylab.evemanage.service.calculation;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.calculation.BlueprintItem;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.calculation.CalculationItem;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.ItemTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.SchematicItemDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import lv.odylab.evemanage.shared.PathExpression;
import lv.odylab.evemanage.shared.RationalNumber;
import lv.odylab.evemanage.shared.eve.BlueprintUse;
import lv.odylab.evemanage.shared.eve.DataInterface;
import lv.odylab.evemanage.shared.eve.Datacore;
import lv.odylab.evemanage.shared.eve.SkillForCalculation;

import java.util.ArrayList;
import java.util.Iterator;
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
        return getNewCalculation(pathNodes, blueprintDetailsDto);
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
    public InventedBlueprint inventBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintName);
        return createInventedBlueprint(pathNodes, blueprintDetailsDto);
    }

    @Override
    public UsedSchematic useSchematic(Long[] pathNodes, String schematicName) throws InvalidItemTypeException, EveDbException {
        List<SchematicItemDto> schematicItemDtos = eveDbGateway.getPlanetSchematicForTypeName(schematicName);
        return createUsedSchematic(pathNodes, schematicItemDtos);
    }

    private Calculation getNewCalculation(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
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
        calculation.setPricePerUnit("0.00");
        calculation.setQuantity(1L);
        List<CalculationItem> calculationItems = createCalculationItems(pathNodes, blueprintDetailsDto);
        calculation.setCalculationItems(calculationItems);
        List<BlueprintItem> blueprintItems = new ArrayList<BlueprintItem>();
        PathExpression pathExpression = new PathExpression(pathNodes, blueprintTypeDto.getProductTypeID());
        blueprintItems.add(createBlueprintItem(pathExpression, blueprintDetailsDto.getBlueprintTypeDto()));
        calculation.setBlueprintItems(blueprintItems);
        List<SkillLevel> skillLevels = new ArrayList<SkillLevel>();
        skillLevels.add(new SkillLevel(SkillForCalculation.PRODUCTION_EFFICIENCY.getTypeID(), 5));
        calculation.setSkillLevels(skillLevels);
        return calculation;
    }

    private UsedBlueprint createUsedBlueprint(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
        UsedBlueprint usedBlueprint = new UsedBlueprint();
        usedBlueprint.setMaterialLevel(0);
        usedBlueprint.setProductivityLevel(0);
        List<CalculationItem> calculationItems = createCalculationItems(pathNodes, blueprintDetailsDto);
        usedBlueprint.setCalculationItems(calculationItems);
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        PathExpression pathExpression = new PathExpression(new Long[]{pathNodes[0], blueprintTypeDto.getProductTypeID()});
        usedBlueprint.setBlueprintItem(createBlueprintItem(pathExpression, blueprintTypeDto));
        return usedBlueprint;
    }

    private InventedBlueprint createInventedBlueprint(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) throws EveDbException {
        InventedBlueprint inventedBlueprint = new InventedBlueprint();
        List<SkillLevel> skillLevels = new ArrayList<SkillLevel>();
        List<BlueprintItem> blueprintItems = new ArrayList<BlueprintItem>();
        for (TypeRequirementDto typeRequirement : blueprintDetailsDto.getInventionRequirementDtos()) {
            Long requiredTypeGroupID = typeRequirement.getRequiredTypeGroupID();
            Long requiredTypeID = typeRequirement.getRequiredTypeID();
            if (requiredTypeGroupID == 333) { // Datacores
                Datacore datacore = Datacore.getByTypeID(requiredTypeID);
                skillLevels.add(new SkillLevel(datacore.getSkillForCalculation().getTypeID(), 5));
                blueprintItems.add(createBlueprintItem(pathNodes, typeRequirement));
            } else if (requiredTypeGroupID == 716) { // Data Interfaces
                DataInterface dataInterface = DataInterface.getByTypeID(requiredTypeID);
                inventedBlueprint.setDecryptors(dataInterface.getDecryptors());
                skillLevels.add(new SkillLevel(dataInterface.getEncryptionSkill().getTypeID(), 5));
            }
        }
        skillLevels.add(new SkillLevel(SkillForCalculation.HACKING.getTypeID(), 5));
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        PathExpression pathExpression = new PathExpression(pathNodes, blueprintTypeDto.getBlueprintTypeID());
        BlueprintItem blueprintItem = createBlueprintItem(pathExpression, blueprintTypeDto);
        blueprintItem.setBlueprintUse(BlueprintUse.COPY.toString());
        blueprintItems.add(blueprintItem);
        inventedBlueprint.setBlueprintItems(blueprintItems);
        inventedBlueprint.setSkillLevels(skillLevels);
        List<ItemTypeDto> baseItems = eveDbGateway.getBaseItemsForTypeID(blueprintTypeDto.getProductTypeID());
        removeBaseItemsNotApplicableForInvention(baseItems);
        inventedBlueprint.setBaseItems(baseItems);
        return inventedBlueprint;
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

    private List<CalculationItem> createCalculationItems(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto) {
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
        calculationItem.setQuantityMultiplier("1");
        calculationItem.setParentQuantity(1L);
        calculationItem.setParentQuantityMultiplier("1");
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
        calculationItem.setQuantityMultiplier("1");
        calculationItem.setParentQuantity(1L);
        calculationItem.setParentQuantityMultiplier("1");
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
        RationalNumber rationalNumber = new RationalNumber(schematicItemDto.getRequiredQuantity(), schematicItemDto.getSchematicQuantity());
        calculationItem.setQuantityMultiplier(rationalNumber.toString());
        calculationItem.setParentQuantity(1L);
        calculationItem.setParentQuantityMultiplier("1");
        calculationItem.setPerfectQuantity(1L);
        calculationItem.setDamagePerJob("1.00");
        calculationItem.setPrice("0.00");
        calculationItem.setTotalPrice("0.00");
        calculationItem.setTotalPriceForParent("0.00");
        return calculationItem;
    }

    private BlueprintItem createBlueprintItem(PathExpression pathExpression, BlueprintTypeDto blueprintType) {
        BlueprintItem blueprintItem = new BlueprintItem();
        blueprintItem.setPath(pathExpression.getExpression());
        blueprintItem.setBlueprintUse(BlueprintUse.ORIGINAL.toString());
        blueprintItem.setItemTypeID(blueprintType.getBlueprintTypeID());
        blueprintItem.setItemCategoryID(9L); // Blueprint
        blueprintItem.setItemTypeName(blueprintType.getBlueprintTypeName());
        blueprintItem.setParentBlueprintTypeID(blueprintType.getParentBlueprintTypeID());
        blueprintItem.setParentBlueprintTypeName(blueprintType.getParentBlueprintTypeName());
        blueprintItem.setParentProductTypeID(blueprintType.getParentProductTypeID());
        blueprintItem.setParentProductTypeName(blueprintType.getParentProductTypeName());
        blueprintItem.setQuantity(1L);
        blueprintItem.setParentQuantity(1L);
        blueprintItem.setPrice("0.00");
        blueprintItem.setTotalPrice("0.00");
        blueprintItem.setTotalPriceForParent("0.00");
        blueprintItem.setMaxProductionLimit(blueprintType.getMaxProductionLimit());
        blueprintItem.setRuns(1L);
        blueprintItem.setChance("1.00");
        return blueprintItem;
    }

    private BlueprintItem createBlueprintItem(Long[] pathNodes, TypeRequirementDto typeRequirement) {
        BlueprintItem blueprintItem = new BlueprintItem();
        PathExpression pathExpression = new PathExpression(pathNodes, typeRequirement.getRequiredTypeID());
        blueprintItem.setPath(pathExpression.getExpression());
        blueprintItem.setItemTypeID(typeRequirement.getRequiredTypeID());
        blueprintItem.setItemCategoryID(typeRequirement.getRequiredTypeCategoryID());
        blueprintItem.setItemTypeName(typeRequirement.getRequiredTypeName());
        blueprintItem.setItemTypeIcon(typeRequirement.getRequiredTypeNameGraphicIcon());
        blueprintItem.setQuantity(typeRequirement.getQuantity());
        blueprintItem.setParentQuantity(1L);
        blueprintItem.setPrice("0.00");
        blueprintItem.setTotalPrice("0.00");
        blueprintItem.setTotalPriceForParent("0.00");
        blueprintItem.setChance("1.00");
        return blueprintItem;
    }

    private void removeBaseItemsNotApplicableForInvention(List<ItemTypeDto> baseItems) {
        Iterator<ItemTypeDto> iterator = baseItems.iterator();
        while (iterator.hasNext()) {
            ItemTypeDto itemType = iterator.next();
            Integer metaLevel = itemType.getMetaLevel();
            if (metaLevel == null || metaLevel < 1 || metaLevel > 4) {
                iterator.remove();
            }
        }
    }
}
