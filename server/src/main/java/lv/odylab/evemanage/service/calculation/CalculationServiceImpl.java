package lv.odylab.evemanage.service.calculation;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.CalculationExpression;
import lv.odylab.evemanage.client.rpc.PathExpression;
import lv.odylab.evemanage.domain.calculation.Calculation;
import lv.odylab.evemanage.domain.calculation.CalculationItem;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintDetailsDto;
import lv.odylab.evemanage.integration.evedb.dto.BlueprintTypeDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeMaterialDto;
import lv.odylab.evemanage.integration.evedb.dto.TypeRequirementDto;
import lv.odylab.evemanage.security.EveManageSecurityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CalculationServiceImpl implements CalculationService {
    private final EveManageSecurityManager securityManager;
    private final EveDbGateway eveDbGateway;

    @Inject
    public CalculationServiceImpl(EveManageSecurityManager securityManager, EveDbGateway eveDbGateway) {
        this.securityManager = securityManager;
        this.eveDbGateway = eveDbGateway;
    }

    @Override
    public Calculation getCalculation(String blueprintName) throws EveDbException, InvalidNameException {
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintName);
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        Long[] pathNodes = new Long[]{blueprintTypeDto.getProductTypeID()};
        return getCalculation(pathNodes, blueprintDetailsDto, Collections.<Long, String>emptyMap());
    }

    @Override
    public Calculation getCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException {
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintName);
        return getCalculation(pathNodes, blueprintDetailsDto, Collections.<Long, String>emptyMap());
    }

    @Override
    public Calculation getCalculationForExpression(CalculationExpression calculationExpression) throws EveDbException, InvalidNameException {
        String blueprintTypeNameFromUrl = calculationExpression.getBlueprintTypeName();
        String blueprintTypeName = securityManager.decodeUrlString(blueprintTypeNameFromUrl);
        calculationExpression.setBlueprintTypeName(blueprintTypeName);
        BlueprintDetailsDto blueprintDetailsDto = eveDbGateway.getBlueprintDetailsForTypeName(blueprintTypeName);
        BlueprintTypeDto blueprintTypeDto = blueprintDetailsDto.getBlueprintTypeDto();
        Long[] pathNodes = new Long[]{blueprintTypeDto.getProductTypeID()};
        Calculation calculation = getCalculation(pathNodes, blueprintDetailsDto, calculationExpression.getPriceSetItemTypeIdToPriceMap());
        calculation.setMaterialLevel(calculationExpression.getMeLevel());
        calculation.setProductivityLevel(calculationExpression.getPeLevel());
        return calculation;
    }

    private Calculation getCalculation(Long[] pathNodes, BlueprintDetailsDto blueprintDetailsDto, Map<Long, String> priceSetItemTypeIdToPriceMap) {
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

        List<TypeMaterialDto> materialDtos = blueprintDetailsDto.getMaterialDtos();
        List<TypeRequirementDto> requirementDtos = blueprintDetailsDto.getManufacturingRequirementDtos();
        List<CalculationItem> calculationItems = new ArrayList<CalculationItem>();

        for (TypeMaterialDto materialDto : materialDtos) {
            CalculationItem calculationItem = new CalculationItem();
            PathExpression pathExpression = new PathExpression(pathNodes, calculation.getMaterialLevel(), calculation.getProductivityLevel(), materialDto.getMaterialTypeID());
            calculationItem.setPath(pathExpression.getPath());
            calculationItem.setItemTypeID(materialDto.getMaterialTypeID());
            calculationItem.setItemCategoryID(materialDto.getMaterialTypeCategoryID());
            calculationItem.setItemTypeName(materialDto.getMaterialTypeName());
            calculationItem.setItemTypeIcon(materialDto.getMaterialTypeGraphicIcon());
            calculationItem.setQuantity(materialDto.getQuantity());
            calculationItem.setParentQuantity(1L);
            calculationItem.setPerfectQuantity(materialDto.getQuantity());
            calculationItem.setWasteFactor(blueprintTypeDto.getWasteFactor());
            calculationItem.setDamagePerJob("1.00");
            String price = priceSetItemTypeIdToPriceMap.get(materialDto.getMaterialTypeID());
            calculationItem.setPrice(price == null ? "0.00" : price);
            calculationItem.setTotalPrice("0.00");
            calculationItem.setTotalPriceForParent("0.00");
            calculationItems.add(calculationItem);
        }

        for (TypeRequirementDto requirementDto : requirementDtos) {
            if (requirementDto.getRequiredTypeCategoryID() == 16L) {
                continue;
            }
            CalculationItem calculationItem = new CalculationItem();
            PathExpression pathExpression = new PathExpression(pathNodes, requirementDto.getRequiredTypeID());
            calculationItem.setPath(pathExpression.getPath());
            calculationItem.setItemTypeID(requirementDto.getRequiredTypeID());
            calculationItem.setItemCategoryID(requirementDto.getRequiredTypeCategoryID());
            calculationItem.setItemTypeName(requirementDto.getRequiredTypeName());
            calculationItem.setItemTypeIcon(requirementDto.getRequiredTypeNameGraphicIcon());
            calculationItem.setQuantity(requirementDto.getQuantity());
            calculationItem.setParentQuantity(1L);
            calculationItem.setPerfectQuantity(requirementDto.getQuantity());
            calculationItem.setWasteFactor(blueprintTypeDto.getWasteFactor());
            calculationItem.setDamagePerJob(requirementDto.getDamagePerJob());
            String price = priceSetItemTypeIdToPriceMap.get(requirementDto.getRequiredTypeID());
            calculationItem.setPrice(price == null ? "0.00" : price);
            calculationItem.setTotalPrice("0.00");
            calculationItem.setTotalPriceForParent("0.00");
            calculationItems.add(calculationItem);
        }
        calculation.setItems(calculationItems);
        return calculation;
    }
}
