package lv.odylab.evemanage.client.rpc;

// TODO this approach needs revamp
public interface ErrorCode {

    String NAME_CANNOT_BE_EMPTY = "nameCannotBeEmpty";

    String INVALID_NAME = "nameIsNotValid";

    String NAME_MUST_BE_UNIQUE = "nameMustBeUnique";

    String INVALID_PRICE = "invalidPrice";

    String NO_ITEMS = "noItems";

    String INVALID_ITEM_TYPE = "invalidItemType";

    String ITEM_MUST_BE_UNIQUE = "itemMustBeUnique";

    String INVALID_API_KEY_ID = "invalidApiKeyID";

    /*static final String API_PARSING_ERROR = "apiParsingError";

    static final String API_RESPONSE_HAS_ERRORS = "apiResponseHasErrors";

    static final String EVEDB_COMMUNICATION_ERROR = "eveDbCommunicationError";*/

    String DOMAIN_DIFFERENT_USER = "domainDifferentUser";
    String DOMAIN_API_KEY_ALREADY_EXISTS = "domainApiKeyAlreadyExists";
    String DOMAIN_CHARACTER_ALREADY_EXISTS = "domainCharacterAlreadyExists";

    String API_KEY_AUTHENTICATION_FAILED = "apiKeyAuthenticationFailed";
    String API_SECURITY_LEVEL_NOT_HIGH_ENOUGH = "apiSecurityLevelNotHighEnough";
    String API_LOGIN_DENIED_BY_ACCOUNT_STATUS = "apiLoginDeniedByAccountStatus";
    String API_RESPONSE_HAS_ERRORS = "apiResponseHasErrors";
    String API_UNABLE_TO_PARSE_RESULT = "apiUnableToParseResult";
    String API_UNABLE_TO_GET_RESULT_FROM_SERVER = "apiUnableToGetResultFromServer";

    String EVECENTRAL_UNABLE_TO_PARSE_RESULT = "eveCentralUnableToParseResult";
    String EVECENTRAL_UNABLE_TO_GET_RESULT_FROM_SERVER = "eveCentralUnableToGetResultFromServer";

    String EVEMETRICS_UNABLE_TO_PARSE_RESULT = "eveMetricsUnableToParseResult";
    String EVEMETRICS_UNABLE_TO_GET_RESULT_FROM_SERVER = "eveMetricsUnableToGetResultFromServer";

    String UNABLE_TO_IMPORT_JOBS_FROM_XML = "unableToImportJobsFromXml";
}