package lv.odylab.evemanage.service.priceset;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.PriceFetchOption;
import lv.odylab.evemanage.domain.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PriceSetManagementService {

    List<PriceSet> getPriceSets(Key<User> userKey);

    List<PriceSet> getCorporationPriceSets(Key<User> userKey);

    List<PriceSet> getAlliancePriceSets(Key<User> userKey);

    PriceSet getPriceSet(Long priceSetID, Key<User> userKey);

    PriceSet getCorporationPriceSet(Long priceSetID, Key<User> userKey);

    PriceSet getAlliancePriceSet(Long priceSetID, Key<User> userKey);

    PriceSet createPriceSet(String priceSetName, Key<User> userKey) throws InvalidNameException;

    void renamePriceSet(Long priceSetID, String priceSetName, Key<User> userKey) throws InvalidNameException;

    void savePriceSet(Long priceSetID, Set<PriceSetItem> priceSetItems, String sharingLevel, Long attachedCharacterID, Key<User> userKey);

    void savePriceSet(PriceSet priceSet, Key<User> userKey);

    List<PriceSetItem> fetchPricesFromEveCentral(List<PriceSetItem> priceSetItems) throws EveCentralApiException;

    Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveCentralApiException;

    List<PriceSetItem> fetchPricesFromEveMetrics(List<PriceSetItem> priceSetItems) throws EveMetricsApiException;

    Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveMetricsApiException;

    void deletePriceSet(Long priceSetID, Key<User> userKey);

    void attachedCharacterDeleted(Key<Character> characterKey);

    void attachedCharacterUpdated(Key<Character> characterKey, Character character);

}
