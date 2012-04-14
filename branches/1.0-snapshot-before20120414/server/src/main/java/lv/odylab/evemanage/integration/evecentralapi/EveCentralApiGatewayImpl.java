package lv.odylab.evemanage.integration.evecentralapi;

import com.google.inject.Inject;
import lv.odylab.appengine.aspect.Caching;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.evecentralapi.EveCentralApiFacade;
import lv.odylab.evecentralapi.parser.method.marketstat.MarketStatResponse;
import lv.odylab.evecentralapi.parser.method.marketstat.MarketStatResult;
import lv.odylab.evecentralapi.parser.method.marketstat.MarketStatType;
import lv.odylab.evecentralapi.sender.ApiIoException;
import lv.odylab.evecentralapi.sender.ApiParserException;
import lv.odylab.evemanage.application.EveManageDtoMapper;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.integration.evecentralapi.dto.MarketStatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EveCentralApiGatewayImpl implements EveCentralApiGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageDtoMapper mapper;
    private final EveCentralApiFacade facade;

    @Inject
    public EveCentralApiGatewayImpl(EveManageDtoMapper mapper, EveCentralApiFacade facade) {
        this.mapper = mapper;
        this.facade = facade;
    }

    @Override
    @Logging
    @Caching(expiration = Caching.TWO_HOURS)
    public List<MarketStatDto> getMarketStatInRegion(Long regionID, Long... typeIDs) throws EveCentralApiException {
        try {
            MarketStatResponse apiResponse = facade.getMarketStatInRegion(regionID, typeIDs);
            MarketStatResult marketStatResult = apiResponse.getMarketStatResult();
            List<MarketStatDto> marketStatDtos = new ArrayList<MarketStatDto>();
            for (MarketStatType marketStatType : marketStatResult.getTypes()) {
                marketStatDtos.add(mapper.map(marketStatType, MarketStatDto.class));
            }
            return marketStatDtos;
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e.getMessage());
            throw new EveCentralApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e.getMessage());
            throw new EveCentralApiException(e);
        }
    }
}
