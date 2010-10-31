package lv.odylab.evemanage.client;

import com.google.gwt.i18n.client.Messages;

public interface EveManageUrlMessages extends Messages {

    @Messages.DefaultMessage("{0}en/wiki/{1}")
    String eveWikiUrl(String base, String name);

    @Messages.DefaultMessage("{0}home/quicklook.html?typeid={1}")
    String eveCentralQuickLookUrl(String base, Long itemTypeID);

    @Messages.DefaultMessage("{0}market/{1}/items/{2}")
    String eveMetricsItemPriceUrl(String base, Long itemCategoryID, Long itemTypeID);

    @Messages.DefaultMessage("{0}icons/16_16/icon{1}.png")
    String imgIcon16Url(String base, String graphicName);

    @Messages.DefaultMessage("{0}icons/32_32/icon{1}.png")
    String imgIcon32Url(String base, String graphicName);

    @Messages.DefaultMessage("{0}icons/64_64/icon{1}.png")
    String imgIcon64Url(String base, String graphicName);

    @Messages.DefaultMessage("{0}blueprints/{1}.png")
    String imgBlueprint64Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/dronetypes_png/32_32/{1}.png")
    String imgDrone32Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/dronetypes_png/64_64/{1}.png")
    String imgDrone64Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/shiptypes_png/32_32/{1}.png")
    String imgShip32Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/shiptypes_png/64_64/{1}.png")
    String imgShip64Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/structuretypes_png/32_32/{1}.png")
    String imgStructures32Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/structuretypes_png/64_64/{1}.png")
    String imgStructures64Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/sovereigntystructurestypes_png/32_32/{1}.png")
    String imgSovereigntyStructures32Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}types/sovereigntystructurestypes_png/64_64/{1}.png")
    String imgSovereigntyStructures64Url(String base, Long itemCategoryID);

    @Messages.DefaultMessage("{0}Character/{1}_32.jpg")
    String imgEveCharacter32Url(String base, Long characterID);

    @Messages.DefaultMessage("{0}Character/{1}_50.jpg")
    String imgEveCharacter50Url(String base, Long characterID);

    @Messages.DefaultMessage("{0}Character/{1}_128.jpg")
    String imgEveCharacter128Url(String base, Long characterID);

    @Messages.DefaultMessage("{0}Corporation/{1}_32.png")
    String imgEveCorporation32Url(String base, Long corporationID);

    @Messages.DefaultMessage("{0}Corporation/{1}_50.png")
    String imgEveCorporation50Url(String base, Long corporationID);

    @Messages.DefaultMessage("{0}Alliance/{1}_32.png")
    String imgEveAlliance32Url(String base, Long allianceID);

    @Messages.DefaultMessage("{0}Alliance/{1}_50.png")
    String imgEveAlliance50Url(String base, Long allianceID);

    @Messages.DefaultMessage("{0}corp/{1}")
    String dotlanCorporationUrl(String base, Long corporationID);

    @Messages.DefaultMessage("{0}alliance/{1}")
    String dotlanAllianceUrl(String base, Long allianceID);

}
