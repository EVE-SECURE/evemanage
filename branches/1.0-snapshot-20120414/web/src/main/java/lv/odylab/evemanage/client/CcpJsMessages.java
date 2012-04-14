package lv.odylab.evemanage.client;

import com.google.gwt.i18n.client.Messages;

public interface CcpJsMessages extends Messages {

    @DefaultMessage("javascript:CCPEVE.showInfo({0})")
    String showInfoUrl(Long itemTypeID);

    @DefaultMessage("javascript:CCPEVE.showInfo({0}, {1})")
    String showItemInfoUrl(Long itemTypeID, Long itemID);

    @DefaultMessage("javascript:CCPEVE.showInfo(1377, {0})")
    String showCharacterInfoUrl(Long characterID);

    @DefaultMessage("javascript:CCPEVE.showInfo(2, {0})")
    String showCorporationInfoUrl(Long corporationID);

    @DefaultMessage("javascript:CCPEVE.showInfo(16159, {0})")
    String showAllianceInfoUrl(Long allianceID);

    @DefaultMessage("javascript:CCPEVE.showMarketDetails({0})")
    String showMarketDetailsUrl(Long itemTypeID);

}
