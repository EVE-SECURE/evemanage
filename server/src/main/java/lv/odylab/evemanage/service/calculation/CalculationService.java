package lv.odylab.evemanage.service.calculation;

import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.calculation.Calculation;

public interface CalculationService {

    Calculation getCalculation(String blueprintName) throws EveDbException, InvalidNameException;

    Calculation getCalculation(Long[] pathNodes, String blueprintName) throws EveDbException, InvalidNameException;

}
