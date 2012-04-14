package lv.odylab.evemanage.client;

import com.google.gwt.i18n.client.Constants;

public interface EveManageConstants extends Constants {

    @DefaultStringValue("EVE Online")
    String eveOnline();

    @DefaultStringValue("CCP Games")
    String ccpGames();

    @DefaultStringValue("Google App Engine")
    String googleAppEngine();

    @DefaultStringValue("UA-16456306-2")
    String googleAnalyticsAccount();

    @DefaultStringValue("https://odylab-eveimages.appspot.com/")
    String eveImagesUrl();

    @DefaultStringValue("http://code.google.com/p/evemanage/wiki/Help")
    String helpUrl();

    @DefaultStringValue("http://code.google.com/p/evemanage/wiki/ReleaseNotes")
    String eveManageReleaseNotesUrl();

    @DefaultStringValue("http://code.google.com/p/evedb/wiki/ReleaseNotes")
    String eveDbReleaseNotesUrl();

    @DefaultStringValue("https://image.eveonline.com/")
    String eveGateImagesUrl();

    @DefaultStringValue("http://www.eveonline.com/")
    String eveOnlineUrl();

    @DefaultStringValue("http://wiki.eveonline.com/")
    String eveOnlineWikiUrl();

    @DefaultStringValue("http://www.ccpgames.com/")
    String ccpGamesUrl();

    @DefaultStringValue("http://evemaps.dotlan.net/")
    String dotlanUrl();

    @DefaultStringValue("http://www.eve-central.com/")
    String eveCentralUrl();

    @DefaultStringValue("http://www.eve-metrics.com/")
    String eveMetricsUrl();

    @DefaultStringValue("http://code.google.com/appengine/")
    String googleAppEngineUrl();

    @DefaultStringValue("/char/IndustryJobs.xml.aspx")
    String charIndustryJobsTitle();

    @DefaultStringValue("/corp/IndustryJobs.xml.aspx")
    String corpIndustryJobsTitle();

    @DefaultStringValue("http://api.eveonline.com/char/IndustryJobs.xml.aspx?userID=USERID&characterID=CHARID&apiKey=FULLAPIKEY")
    String charIndustryJobsUrl();

    @DefaultStringValue("http://api.eveonline.com/corp/IndustryJobs.xml.aspx?userID=USERID&characterID=CHARID&apiKey=FULLAPIKEY")
    String corpIndustryJobsUrl();

    @DefaultStringValue("http://www.eveonline.com/api/default.asp")
    String eveApiKeyManagementPageUrl();

    @DefaultStringValue("Dashboard")
    String dashboardToken();

    @DefaultStringValue("Blueprints")
    String blueprintsToken();

    @DefaultStringValue("PriceSet")
    String priceSetToken();

    @DefaultStringValue("QuickCalculator")
    String quickCalculatorToken();

    @DefaultStringValue("Preferences")
    String preferencesToken();

    @DefaultStringValue("About")
    String aboutToken();

    @DefaultStringValue("Error")
    String errorToken();

}
