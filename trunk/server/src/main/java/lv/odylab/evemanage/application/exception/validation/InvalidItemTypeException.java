package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.EveManageException;

public class InvalidItemTypeException extends EveManageException {
    public InvalidItemTypeException(String argument, String errorCode) {
        super(argument, errorCode);
    }
}