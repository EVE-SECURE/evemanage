package lv.odylab.evemanage.application.exception;

import lv.odylab.eveapi.sender.ApiErrorException;
import lv.odylab.eveapi.sender.ApiIoException;
import lv.odylab.eveapi.sender.ApiParserException;
import lv.odylab.evemanage.client.rpc.ErrorCode;

public class EveApiException extends Exception {
    public EveApiException(String message) {
        super(message);
    }

    public EveApiException(ApiErrorException e) {
        super(prepareErrorCode(e));
    }

    public EveApiException(ApiParserException e) {
        super(prepareErrorCode(e));
    }

    public EveApiException(ApiIoException e) {
        super(prepareErrorCode(e));
    }

    private static String prepareErrorCode(ApiErrorException e) {
        if (e.isApiKeyAuthenticationFailure()) {
            return ErrorCode.API_KEY_AUTHENTICATION_FAILED;
        } else if (e.isSecurityLevelNotHighEnough()) {
            return ErrorCode.API_SECURITY_LEVEL_NOT_HIGH_ENOUGH;
        } else if (e.isLoginDeniedByAccountStatus()) {
            return ErrorCode.API_LOGIN_DENIED_BY_ACCOUNT_STATUS;
        } else {
            return ErrorCode.API_RESPONSE_HAS_ERRORS;
        }
    }

    private static String prepareErrorCode(ApiParserException e) {
        return ErrorCode.API_UNABLE_TO_PARSE_RESULT;
    }

    private static String prepareErrorCode(ApiIoException e) {
        return ErrorCode.API_UNABLE_TO_GET_RESULT_FROM_SERVER;
    }
}