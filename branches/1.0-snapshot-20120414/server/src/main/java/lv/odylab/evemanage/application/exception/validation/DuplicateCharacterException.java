package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.DomainConstraintException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

public class DuplicateCharacterException extends DomainConstraintException {
    public DuplicateCharacterException() {
        super(ErrorCode.DOMAIN_CHARACTER_ALREADY_EXISTS);
    }
}
