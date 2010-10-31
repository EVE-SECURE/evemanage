package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.EveManageException;

public class InvalidApiKeyException extends EveManageException {
    public InvalidApiKeyException(String argument, String errorCode) {
        super(argument, errorCode);
    }
}
