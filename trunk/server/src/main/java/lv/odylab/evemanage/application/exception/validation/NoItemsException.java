package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.EveManageException;

public class NoItemsException extends EveManageException {
    public NoItemsException(String errorCode) {
        super("no items", errorCode);
    }
}