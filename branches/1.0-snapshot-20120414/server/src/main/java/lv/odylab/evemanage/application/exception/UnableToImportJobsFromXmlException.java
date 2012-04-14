package lv.odylab.evemanage.application.exception;

import lv.odylab.evemanage.client.rpc.ErrorCode;

public class UnableToImportJobsFromXmlException extends EveApiException {
    public UnableToImportJobsFromXmlException(Throwable cause) {
        super(ErrorCode.UNABLE_TO_IMPORT_JOBS_FROM_XML);
    }
}
