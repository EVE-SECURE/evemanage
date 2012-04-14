package lv.odylab.evemanage.integration.eveapi;

import com.google.inject.Inject;
import lv.odylab.appengine.aspect.Caching;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.eveapi.EveApiFacade;
import lv.odylab.eveapi.parser.method.account.character.ApiAccountCharacterResponse;
import lv.odylab.eveapi.parser.method.account.character.ApiAccountCharacterRow;
import lv.odylab.eveapi.parser.method.character.sheet.ApiCharacterSheetResponse;
import lv.odylab.eveapi.parser.method.character.sheet.ApiCharacterSheetResult;
import lv.odylab.eveapi.parser.method.common.accbalance.ApiAccountBalanceResponse;
import lv.odylab.eveapi.parser.method.common.accbalance.ApiAccountBalanceRow;
import lv.odylab.eveapi.parser.method.common.industryjob.ApiIndustryJobResponse;
import lv.odylab.eveapi.parser.method.common.industryjob.ApiIndustryJobRow;
import lv.odylab.eveapi.parser.method.corporation.sheet.ApiCorporationSheetResponse;
import lv.odylab.eveapi.parser.method.corporation.sheet.ApiCorporationSheetResult;
import lv.odylab.eveapi.sender.ApiErrorException;
import lv.odylab.eveapi.sender.ApiIoException;
import lv.odylab.eveapi.sender.ApiParserException;
import lv.odylab.evemanage.application.EveManageDtoMapper;
import lv.odylab.evemanage.application.exception.ApiKeyShouldBeRemovedException;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.application.exception.UnableToImportJobsFromXmlException;
import lv.odylab.evemanage.integration.eveapi.dto.AccountBalanceDto;
import lv.odylab.evemanage.integration.eveapi.dto.AccountCharacterDto;
import lv.odylab.evemanage.integration.eveapi.dto.CharacterSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.CorporationSheetDto;
import lv.odylab.evemanage.integration.eveapi.dto.IndustryJobDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DataBindingException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.JAXB.unmarshal;

public class EveApiGatewayImpl implements EveApiGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageDtoMapper mapper;
    private final EveApiFacade facade;

    @Inject
    public EveApiGatewayImpl(EveManageDtoMapper mapper, EveApiFacade facade) {
        this.mapper = mapper;
        this.facade = facade;
    }

    @Override
    @Logging(logArguments = false)
    public List<IndustryJobDto> importFromIndustryJobXml(String importXml) throws UnableToImportJobsFromXmlException {
        try {
            ApiIndustryJobResponse apiResponse = unmarshal(new ByteArrayInputStream(importXml.getBytes("UTF-8")), ApiIndustryJobResponse.class);
            List<ApiIndustryJobRow> apiIndustryJobRows = apiResponse.getResult().getRowset().getRows();
            List<IndustryJobDto> industryJobDtos = new ArrayList<IndustryJobDto>();
            for (ApiIndustryJobRow apiIndustryJobRow : apiIndustryJobRows) {
                industryJobDtos.add(mapper.map(apiIndustryJobRow, IndustryJobDto.class));
            }
            return industryJobDtos;
        } catch (DataBindingException e) {
            logger.error("Caught DataBindingException", e);
            throw new UnableToImportJobsFromXmlException(e);
        } catch (UnsupportedEncodingException e) {
            logger.error("Caught UnsupportedEncodingException", e);
            throw new UnableToImportJobsFromXmlException(e);
        }
    }

    @Override
    @Logging(logArguments = false)
    @Caching(expiration = Caching.TEN_MINUTES, logArguments = false)
    public List<AccountCharacterDto> getApiKeyCharacters(String apiKeyString, Long apiKeyUserID) throws EveApiException, ApiKeyShouldBeRemovedException {
        try {
            ApiAccountCharacterResponse apiResponse = facade.getAccountCharacters(apiKeyString, apiKeyUserID);
            List<ApiAccountCharacterRow> apiAccountCharacterRows = apiResponse.getResult().getRowset().getRows();
            List<AccountCharacterDto> accountCharacterDtos = new ArrayList<AccountCharacterDto>();
            for (ApiAccountCharacterRow apiAccountCharacterRow : apiAccountCharacterRows) {
                accountCharacterDtos.add(mapper.map(apiAccountCharacterRow, AccountCharacterDto.class));
            }
            return accountCharacterDtos;
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            if (e.shouldRemoveApiKey() || e.shouldPostponeApiKey()) {
                throw new ApiKeyShouldBeRemovedException(e.getMessage());
            } else {
                throw new EveApiException(e);
            }
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }

    @Override
    @Logging(logArguments = false)
    @Caching(expiration = Caching.TEN_MINUTES, logArguments = false)
    public List<AccountBalanceDto> getAccountBalances(String apiKeyString, Long apiKeyUserID, Long characterID) throws EveApiException {
        try {
            ApiAccountBalanceResponse apiResponse = facade.getAccountBalance(apiKeyString, apiKeyUserID, characterID);
            List<ApiAccountBalanceRow> apiAccountBalanceRows = apiResponse.getResult().getRowset().getRows();
            List<AccountBalanceDto> accountBalanceDtos = new ArrayList<AccountBalanceDto>();
            for (ApiAccountBalanceRow apiAccountBalanceRow : apiAccountBalanceRows) {
                accountBalanceDtos.add(mapper.map(apiAccountBalanceRow, AccountBalanceDto.class));
            }
            return accountBalanceDtos;
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            throw new EveApiException(e);
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }

    @Override
    @Logging(logArguments = false)
    @Caching(expiration = Caching.TEN_MINUTES, logArguments = false)
    public CharacterSheetDto getCharacterSheet(String apiKeyString, Long apiKeyUserID, Long characterID) throws EveApiException {
        try {
            ApiCharacterSheetResponse apiResponse = facade.getCharacterSheet(apiKeyString, apiKeyUserID, characterID);
            ApiCharacterSheetResult apiCharacterSheetResult = apiResponse.getResult();
            return mapper.map(apiCharacterSheetResult, CharacterSheetDto.class);
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            throw new EveApiException(e);
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }

    @Override
    @Logging(logArguments = false)
    public List<IndustryJobDto> getCharacterIndustryJobs(String apiKey, Long userID, Long characterID) throws EveApiException {
        try {
            ApiIndustryJobResponse response = facade.getCharacterIndustryJobs(apiKey, userID, characterID);
            List<ApiIndustryJobRow> apiIndustryJobRows = response.getResult().getRowset().getRows();
            List<IndustryJobDto> industryJobDtos = new ArrayList<IndustryJobDto>();
            for (ApiIndustryJobRow apiIndustryJobRow : apiIndustryJobRows) {
                industryJobDtos.add(mapper.map(apiIndustryJobRow, IndustryJobDto.class));
            }
            return industryJobDtos;
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            throw new EveApiException(e);
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }

    @Override
    @Logging
    @Caching(expiration = Caching.TEN_MINUTES)
    public CorporationSheetDto getCorporationSheet(Long corporationID) throws EveApiException {
        try {
            ApiCorporationSheetResponse response = facade.getCorporationSheet(corporationID);
            ApiCorporationSheetResult apiCorporationSheetResult = response.getResult();
            return mapper.map(apiCorporationSheetResult, CorporationSheetDto.class);
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            throw new EveApiException(e);
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }

    @Override
    @Logging(logArguments = false)
    public List<IndustryJobDto> getCorporationIndustryJobs(String apiKey, Long userID, Long characterID) throws EveApiException {
        try {
            ApiIndustryJobResponse response = facade.getCorporationIndustryJobs(apiKey, userID, characterID);
            List<ApiIndustryJobRow> apiIndustryJobRows = response.getResult().getRowset().getRows();
            List<IndustryJobDto> industryJobDtos = new ArrayList<IndustryJobDto>();
            for (ApiIndustryJobRow apiIndustryJobRow : apiIndustryJobRows) {
                industryJobDtos.add(mapper.map(apiIndustryJobRow, IndustryJobDto.class));
            }
            return industryJobDtos;
        } catch (ApiErrorException e) {
            logger.warn("Caught ApiErrorException", e.getMessage());
            throw new EveApiException(e);
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e);
            throw new EveApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e);
            throw new EveApiException(e);
        }
    }
}
