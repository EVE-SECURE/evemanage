package lv.odylab.evemanage.integration.evecentralapi;

import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.integration.evecentralapi.dto.MarketStatDto;

import java.util.List;

public interface EveCentralApiGateway {

    List<MarketStatDto> getMarketStatInRegion(Long regionID, Long... typeIDs) throws EveCentralApiException;

}
