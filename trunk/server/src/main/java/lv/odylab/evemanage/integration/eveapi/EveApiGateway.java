package lv.odylab.evemanage.integration.eveapi;

import lv.odylab.evemanage.application.exception.ApiKeyNotValidException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.UnableToImportJobsFromXmlException;
import lv.odylab.evemanage.integration.eveapi.dto.AccountBalanceDto;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.IndustryJobDto;

import java.util.List;

public interface EveApiGateway {

    List<IndustryJobDto> importFromIndustryJobXml(String importXml) throws UnableToImportJobsFromXmlException;

    List<AccountCharacterDto> getApiKeyCharacters(String apiKeyString, Long apiKeyUserID) throws EveApiException, ApiKeyNotValidException;

    List<AccountBalanceDto> getAccountBalances(String apiKeyString, Long apiKeyUserID, Long characterID) throws EveApiException;

    CharacterSheetDto getCharacterSheet(String apiKeyString, Long apiKeyUserID, Long characterID) throws EveApiException;

    CorporationSheetDto getCorporationSheet(Long corporationID) throws EveApiException;

}
