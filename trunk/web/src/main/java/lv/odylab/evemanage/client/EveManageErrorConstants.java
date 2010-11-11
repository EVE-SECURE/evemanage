package lv.odylab.evemanage.client;

import com.google.gwt.i18n.client.ConstantsWithLookup;

public interface EveManageErrorConstants extends ConstantsWithLookup {

    @DefaultStringValue("Error on server")
    String errorOnServer();

    @DefaultStringValue("Something wrong with server or your connection, please reload")
    String loginFailed();

    @DefaultStringValue("Failed to load tab, something wrong with server or your connection, please reload")
    String failedLoading();

    @DefaultStringValue("Name cannot be empty")
    String nameCannotBeEmpty();

    @DefaultStringValue("Name is not valid")
    String nameIsNotValid();

    @DefaultStringValue("Name must be unique")
    String nameMustBeUnique();

    @DefaultStringValue("Invalid price")
    String invalidPrice();

    @DefaultStringValue("No items")
    String noItems();

    @DefaultStringValue("Invalid item type")
    String invalidItemType();

    @DefaultStringValue("Item must be unique")
    String itemMustBeUnique();

    @DefaultStringValue("Invalid api key id")
    String invalidApiKeyID();

    @DefaultStringValue("Api key authentication failed")
    String apiKeyAuthenticationFailed();

    @DefaultStringValue("Api key security level not high enough")
    String apiSecurityLevelNotHighEnough();

    @DefaultStringValue("Login denied by account status")
    String apiLoginDeniedByAccountStatus();

    @DefaultStringValue("Api response has errors")
    String apiResponseHasErrors();

    @DefaultStringValue("Unable to parse response from api server")
    String apiUnableToParseResult();

    @DefaultStringValue("Unable to get result from api server")
    String apiUnableToGetResultFromServer();

    @DefaultStringValue("Unable to parse response from EVE-Central server")
    String eveCentralUnableToParseResult();

    @DefaultStringValue("Unable to get result from EVE-Central server")
    String eveCentralUnableToGetResultFromServer();

    @DefaultStringValue("Unable to parse response from EVE Metrics server")
    String eveMetricsUnableToParseResult();

    @DefaultStringValue("Unable to get result from EVE Metrics server")
    String eveMetricsUnableToGetResultFromServer();

    @DefaultStringValue("Unable to import jobs from xml")
    String unableToImportJobsFromXml();

    @DefaultStringValue("Api key is already in use")
    String domainApiKeyAlreadyExists();

    @DefaultStringValue("Character is already in use")
    String domainCharacterAlreadyExists();

}