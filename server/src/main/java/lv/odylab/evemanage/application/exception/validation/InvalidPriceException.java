package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.EveManageException;

public class InvalidPriceException extends EveManageException {
    public InvalidPriceException(String argument, String errorCode) {
        super(argument, errorCode);
    }
}
