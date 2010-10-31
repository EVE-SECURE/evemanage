package lv.odylab.evemanage.client.tracking;

public interface TrackingManager {

    void trackPageView(String googleAnalyticsAccount, String historyToken);

    void trackEvent(String googleAnalyticsAccount, String category, String action, String label, int msDuration);

}
