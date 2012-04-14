package lv.odylab.evemanage.application.exception;

public class DomainConstraintException extends RuntimeException {
    public DomainConstraintException(String message) {
        super(message);
    }
}
