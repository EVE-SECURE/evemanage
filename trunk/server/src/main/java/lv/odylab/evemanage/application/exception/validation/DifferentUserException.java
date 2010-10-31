package lv.odylab.evemanage.application.exception.validation;

import lv.odylab.evemanage.application.exception.DomainConstraintException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

public class DifferentUserException extends DomainConstraintException {
    public DifferentUserException() {
        super(ErrorCode.DOMAIN_DIFFERENT_USER);
    }
}
