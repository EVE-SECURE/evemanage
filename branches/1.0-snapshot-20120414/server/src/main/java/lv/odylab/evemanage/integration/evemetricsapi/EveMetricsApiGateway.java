package lv.odylab.evemanage.integration.evemetricsapi;

import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.integration.evemetricsapi.dto.ItemPriceDto;

import java.util.List;

public interface EveMetricsApiGateway {

    List<ItemPriceDto> getItemPrice(Long regionID, Long... typeIDs) throws EveMetricsApiException;

    List<ItemPriceDto> getSafeItemPrice(Long regionID, Long... typeIDs) throws EveMetricsApiException;

}
