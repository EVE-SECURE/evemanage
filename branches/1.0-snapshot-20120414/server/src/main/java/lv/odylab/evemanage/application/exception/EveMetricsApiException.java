package lv.odylab.evemanage.application.exception;

import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemetricsapi.sender.ApiIoException;
import lv.odylab.evemetricsapi.sender.ApiParserException;

public class EveMetricsApiException extends Exception {
    public EveMetricsApiException(ApiParserException e) {
        super(prepareErrorCode(e));
    }

    public EveMetricsApiException(ApiIoException e) {
        super(prepareErrorCode(e));
    }

    private static String prepareErrorCode(ApiParserException e) {
        return ErrorCode.EVEMETRICS_UNABLE_TO_PARSE_RESULT;
    }

    private static String prepareErrorCode(ApiIoException e) {
        return ErrorCode.EVEMETRICS_UNABLE_TO_GET_RESULT_FROM_SERVER;
    }
}
