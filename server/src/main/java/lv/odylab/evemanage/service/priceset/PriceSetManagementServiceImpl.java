package lv.odylab.evemanage.service.priceset;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveCentralApiException;
import lv.odylab.evemanage.application.exception.EveMetricsApiException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.client.rpc.ErrorCode;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.priceset.PriceSetItem;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import lv.odylab.evemanage.integration.evecentralapi.EveCentralApiGateway;
import lv.odylab.evemanage.integration.evecentralapi.dto.MarketStatDto;
import lv.odylab.evemanage.integration.evemetricsapi.EveMetricsApiGateway;
import lv.odylab.evemanage.integration.evemetricsapi.dto.ItemPriceDto;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PriceSetManagementServiceImpl implements PriceSetManagementService {
    private final EveCentralApiGateway eveCentralApiGateway;
    private final EveMetricsApiGateway eveMetricsApiGateway;
    private final UserDao userDao;
    private final CharacterDao characterDao;
    private final PriceSetDao priceSetDao;

    @Inject
    public PriceSetManagementServiceImpl(EveCentralApiGateway eveCentralApiGateway, EveMetricsApiGateway eveMetricsApiGateway, UserDao userDao, CharacterDao characterDao, PriceSetDao priceSetDao) {
        this.eveCentralApiGateway = eveCentralApiGateway;
        this.eveMetricsApiGateway = eveMetricsApiGateway;
        this.userDao = userDao;
        this.characterDao = characterDao;
        this.priceSetDao = priceSetDao;
    }

    @Override
    public List<PriceSet> getPriceSets(Key<User> userKey) {
        return priceSetDao.getAll(userKey);
    }

    @Override
    public List<PriceSet> getCorporationPriceSets(Key<User> userKey) {
        User user = userDao.get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo == null) {
            return Collections.emptyList();
        }

        List<PriceSet> priceSets = priceSetDao.getAllForCorporationID(mainCharacterInfo.getCorporationID());
        for (PriceSet priceSet : priceSets) {
            StringBuilder stringBuilder = new StringBuilder(priceSet.getName());
            stringBuilder.append(" (").append(priceSet.getAttachedCharacterInfo().getName()).append(")");
            priceSet.setName(stringBuilder.toString());
        }
        return priceSets;
    }

    @Override
    public List<PriceSet> getAlliancePriceSets(Key<User> userKey) {
        User user = userDao.get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo == null) {
            return Collections.emptyList();
        }

        List<PriceSet> priceSets = priceSetDao.getAllForAllianceID(mainCharacterInfo.getAllianceID());
        for (PriceSet priceSet : priceSets) {
            StringBuilder stringBuilder = new StringBuilder(priceSet.getName());
            CharacterInfo attachedCharacterInfo = priceSet.getAttachedCharacterInfo();
            stringBuilder.append(" (").append(attachedCharacterInfo.getCorporationTicker()).append(", ").append(attachedCharacterInfo.getName()).append(")");
            priceSet.setName(stringBuilder.toString());
        }
        return priceSets;
    }

    @Override
    public PriceSet getPriceSet(Long priceSetID, Key<User> userKey) {
        return priceSetDao.get(priceSetID, userKey);
    }

    @Override
    public PriceSet getCorporationPriceSet(Long priceSetID, Key<User> userKey) {
        return priceSetDao.getForCorporationByPriceSetID(priceSetID, userKey);
    }

    @Override
    public PriceSet getAlliancePriceSet(Long priceSetID, Key<User> userKey) {
        return priceSetDao.getForAllianceByPriceSetID(priceSetID, userKey);
    }

    @Override
    public PriceSet createPriceSet(String priceSetName, Key<User> userKey) throws InvalidNameException {
        if (priceSetName.length() == 0) {
            throw new InvalidNameException(priceSetName, ErrorCode.NAME_CANNOT_BE_EMPTY);
        }
        if (priceSetNameAlreadyExists(priceSetName, userKey)) {
            throw new InvalidNameException(priceSetName, ErrorCode.NAME_MUST_BE_UNIQUE);
        }
        PriceSet priceSet = new PriceSet();
        priceSet.setUser(userKey);
        priceSet.setName(priceSetName);
        priceSet.setAttachedCharacterInfo(null);
        priceSet.setSharingLevel(SharingLevel.PERSONAL.toString());
        priceSet.setCreatedDate(new Date());
        priceSet.setUpdatedDate(new Date());
        priceSetDao.put(priceSet, userKey);
        return priceSet;
    }

    @Override
    public void renamePriceSet(Long priceSetID, String priceSetName, Key<User> userKey) throws InvalidNameException {
        if (priceSetName.length() == 0) {
            throw new InvalidNameException(priceSetName, ErrorCode.NAME_CANNOT_BE_EMPTY);
        }
        if (priceSetNameAlreadyExists(priceSetName, userKey)) {
            throw new InvalidNameException(priceSetName, ErrorCode.NAME_MUST_BE_UNIQUE);
        }
        PriceSet priceSet = priceSetDao.get(priceSetID, userKey);
        priceSet.setName(priceSetName);
        priceSet.setUpdatedDate(new Date());
        priceSetDao.put(priceSet, userKey);
    }

    @Override
    public void savePriceSet(Long priceSetID, Set<PriceSetItem> priceSetItems, SharingLevel sharingLevel, Long attachedCharacterID, Key<User> userKey) {
        PriceSet priceSet = priceSetDao.get(priceSetID, userKey);
        priceSet.setItems(priceSetItems);
        priceSet.setSharingLevel(sharingLevel.toString());
        if (attachedCharacterID != null) {
            Character character = characterDao.getByCharacterID(attachedCharacterID, userKey);
            CharacterInfo characterInfo = new CharacterInfo();
            characterInfo.setId(character.getId());
            characterInfo.setCharacterID(character.getCharacterID());
            characterInfo.setName(character.getName());
            characterInfo.setCorporationID(character.getCorporationID());
            characterInfo.setCorporationName(character.getCorporationName());
            characterInfo.setCorporationTicker(character.getCorporationTicker());
            characterInfo.setAllianceID(character.getAllianceID());
            characterInfo.setAllianceName(character.getAllianceName());
            priceSet.setAttachedCharacterInfo(characterInfo);
        } else {
            priceSet.setAttachedCharacterInfo(null);
        }
        priceSet.setUpdatedDate(new Date());
        priceSetDao.put(priceSet, userKey);
    }

    @Override
    public void savePriceSet(PriceSet priceSet, Key<User> userKey) {
        priceSetDao.put(priceSet, userKey);
    }

    @Override
    public List<PriceSetItem> fetchPricesFromEveCentral(List<PriceSetItem> priceSetItems) throws EveCentralApiException {
        List<Long> typeIDs = new ArrayList<Long>();
        for (PriceSetItem priceSetItem : priceSetItems) {
            typeIDs.add(priceSetItem.getItemTypeID());
        }
        List<MarketStatDto> marketStatDtos = eveCentralApiGateway.getMarketStatInRegion(10000002L, typeIDs.toArray(new Long[0]));
        Map<Long, MarketStatDto> typeIdToMarketStatMap = new HashMap<Long, MarketStatDto>();
        for (MarketStatDto marketStatDto : marketStatDtos) {
            typeIdToMarketStatMap.put(marketStatDto.getTypeID(), marketStatDto);
        }
        for (PriceSetItem priceSetItem : priceSetItems) {
            MarketStatDto marketStatDto = typeIdToMarketStatMap.get(priceSetItem.getItemTypeID());
            priceSetItem.setPrice(String.valueOf(marketStatDto.getMedianBuySell()));
        }
        return priceSetItems;
    }

    @Override
    public Map<Long, BigDecimal> fetchPricesFromEveCentralForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveCentralApiException {
        List<MarketStatDto> marketStatDtos = eveCentralApiGateway.getMarketStatInRegion(regionID, typeIDs.toArray(new Long[0]));
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (MarketStatDto marketStatDto : marketStatDtos) {
            BigDecimal price = BigDecimal.ZERO;
            if (PriceFetchOption.MEDIAN_BUY_SELL.equals(priceFetchOption)) {
                price = marketStatDto.getMedianBuySell();
            } else if (PriceFetchOption.MEDIAN_BUY.equals(priceFetchOption)) {
                price = marketStatDto.getMedianBuy();
            } else if (PriceFetchOption.MEDIAN_SELL.equals(priceFetchOption)) {
                price = marketStatDto.getMedianSell();
            }
            typeIdToPriceMap.put(marketStatDto.getTypeID(), price);
        }
        return typeIdToPriceMap;
    }

    @Override
    public List<PriceSetItem> fetchPricesFromEveMetrics(List<PriceSetItem> priceSetItems) throws EveMetricsApiException {
        List<Long> typeIDs = new ArrayList<Long>();
        for (PriceSetItem priceSetItem : priceSetItems) {
            typeIDs.add(priceSetItem.getItemTypeID());
        }
        List<ItemPriceDto> itemPriceDtos = eveMetricsApiGateway.getSafeItemPrice(10000002L, typeIDs.toArray(new Long[0]));
        Map<Long, ItemPriceDto> typeIdToPriceMap = new HashMap<Long, ItemPriceDto>();
        for (ItemPriceDto itemPriceDto : itemPriceDtos) {
            typeIdToPriceMap.put(itemPriceDto.getTypeID(), itemPriceDto);
        }
        for (PriceSetItem priceSetItem : priceSetItems) {
            ItemPriceDto itemPriceDto = typeIdToPriceMap.get(priceSetItem.getItemTypeID());
            priceSetItem.setPrice(String.valueOf(itemPriceDto.getMedianBuySell()));
        }
        return priceSetItems;
    }

    @Override
    public Map<Long, BigDecimal> fetchPricesFromEveMetricsForTypeIDs(List<Long> typeIDs, Long regionID, PriceFetchOption priceFetchOption) throws EveMetricsApiException {
        List<ItemPriceDto> itemPriceDtos = eveMetricsApiGateway.getSafeItemPrice(regionID, typeIDs.toArray(new Long[0]));
        Map<Long, BigDecimal> typeIdToPriceMap = new HashMap<Long, BigDecimal>();
        for (ItemPriceDto itemPriceDto : itemPriceDtos) {
            BigDecimal price = BigDecimal.ZERO;
            if (PriceFetchOption.MEDIAN_BUY_SELL.equals(priceFetchOption)) {
                price = itemPriceDto.getMedianBuySell();
            } else if (PriceFetchOption.MEDIAN_BUY.equals(priceFetchOption)) {
                price = itemPriceDto.getMedianBuy();
            } else if (PriceFetchOption.MEDIAN_SELL.equals(priceFetchOption)) {
                price = itemPriceDto.getMedianSell();
            }
            typeIdToPriceMap.put(itemPriceDto.getTypeID(), price);
        }
        return typeIdToPriceMap;
    }

    @Override
    public void deletePriceSet(Long priceSetID, Key<User> userKey) {
        PriceSet priceSet = priceSetDao.get(priceSetID, userKey);
        priceSetDao.delete(priceSet);
    }

    @Override
    public void attachedCharacterDeleted(Key<lv.odylab.evemanage.domain.eve.Character> characterKey) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void attachedCharacterUpdated(Key<Character> characterKey, Character character) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Boolean priceSetNameAlreadyExists(String priceSetName, Key<User> userKey) {
        return priceSetDao.getByPriceSetName(priceSetName, userKey) != null;
    }
}