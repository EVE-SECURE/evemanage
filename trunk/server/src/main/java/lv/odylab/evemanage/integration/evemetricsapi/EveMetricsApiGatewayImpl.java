package lv.odylab.evemanage.integration.evemetricsapi;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lv.odylab.appengine.aspect.Caching;
import lv.odylab.appengine.aspect.Logging;
import lv.odylab.evemanage.application.EveManageDtoMapper;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.integration.evemetricsapi.dto.ItemPriceDto;
import lv.odylab.evemetricsapi.EveMetricsApiFacade;
import lv.odylab.evemetricsapi.parser.method.itemprice.ItemPriceResponse;
import lv.odylab.evemetricsapi.parser.method.itemprice.ItemPriceType;
import lv.odylab.evemetricsapi.sender.ApiIoException;
import lv.odylab.evemetricsapi.sender.ApiParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EveMetricsApiGatewayImpl implements EveMetricsApiGateway {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveManageDtoMapper mapper;
    private final EveMetricsApiFacade facade;
    private final String eveMetricsDeveloperKey;

    @Inject
    public EveMetricsApiGatewayImpl(EveManageDtoMapper mapper, EveMetricsApiFacade facade,
                                    @Named("eveMetrics.developerKey") String eveMetricsDeveloperKey) {
        this.mapper = mapper;
        this.facade = facade;
        this.eveMetricsDeveloperKey = eveMetricsDeveloperKey;
    }

    @Override
    @Logging
    @Caching(expiration = Caching.TWO_HOURS)
    public List<ItemPriceDto> getItemPrice(Long regionID, Long... typeIDs) throws EveMetricsApiException {
        try {
            ItemPriceResponse apiResponse = facade.getItemPriceInRegion(eveMetricsDeveloperKey, regionID, typeIDs);
            List<ItemPriceDto> itemPriceDtos = new ArrayList<ItemPriceDto>();
            for (ItemPriceType itemPriceType : apiResponse.getTypes()) {
                itemPriceDtos.add(mapper.map(itemPriceType, ItemPriceDto.class));
            }
            return itemPriceDtos;
        } catch (ApiParserException e) {
            logger.error("Caught ApiParserException", e.getMessage());
            throw new EveMetricsApiException(e);
        } catch (ApiIoException e) {
            logger.error("Caught ApiIoException", e.getMessage());
            throw new EveMetricsApiException(e);
        }
    }

    @Override
    public List<ItemPriceDto> getSafeItemPrice(Long regionID, Long... typeIDs) throws EveMetricsApiException {
        if (typeIDs.length > 24) {
            List<ItemPriceDto> result = new ArrayList<ItemPriceDto>();
            List<Long[]> portions = new ArrayList<Long[]>();
            List<Long> currentPortion = new ArrayList<Long>();
            for (Long typeID : typeIDs) {
                if (currentPortion.size() > 24) {
                    portions.add(currentPortion.toArray(new Long[0]));
                    currentPortion = new ArrayList<Long>();
                }
                currentPortion.add(typeID);
            }
            if (currentPortion.size() > 0) {
                portions.add(currentPortion.toArray(new Long[0]));
            }
            for (Long[] portion : portions) {
                result.addAll(getItemPrice(regionID, portion));
            }
            return result;
        } else {
            return getItemPrice(regionID, typeIDs);
        }
    }
}
