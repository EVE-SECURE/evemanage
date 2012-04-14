package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.EveManageException;

public class InvalidNameException extends EveManageException {
    public InvalidNameException(String argument, String errorCode) {
        super(argument, errorCode);
    }
}
