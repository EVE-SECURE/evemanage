package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.DomainConstraintException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

public class DuplicateApiKeyException extends DomainConstraintException {
    public DuplicateApiKeyException() {
        super(ErrorCode.DOMAIN_API_KEY_ALREADY_EXISTS);
    }
}
