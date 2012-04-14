package lv.odylab.evemanage.application.exception;

import lv.odylab.evecentralapi.sender.ApiIoException;
import lv.odylab.evecentralapi.sender.ApiParserException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

public class EveCentralApiException extends Exception {
    public EveCentralApiException(ApiParserException e) {
        super(prepareErrorCode(e));
    }

    public EveCentralApiException(ApiIoException e) {
        super(prepareErrorCode(e));
    }

    private static String prepareErrorCode(ApiParserException e) {
        return ErrorCode.EVECENTRAL_UNABLE_TO_PARSE_RESULT;
    }

    private static String prepareErrorCode(ApiIoException e) {
        return ErrorCode.EVECENTRAL_UNABLE_TO_GET_RESULT_FROM_SERVER;
    }
}
