package lv.odylab.evemanage.service.calculation;

import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidItemTypeException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.calculation.Calculation;

public interface CalculationService {

    Calculation getNewCalculation(String blueprintName) throws EveDbException, InvalidNameException;

    UsedBlueprint useBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException;

    UsedBlueprint useBlueprint(Long[] pathNodes, Long blueprintProductTypeID) throws InvalidItemTypeException, EveDbException, InvalidNameException;

    InventedBlueprint inventBlueprint(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException;

    UsedSchematic useSchematic(Long[] pathNodes, String schematicName) throws InvalidItemTypeException, EveDbException;

}
