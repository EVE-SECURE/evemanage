package lv.odylab.evemanage.client.tracking;

public class GoogleAnalyticsTrackingManagerImpl implements TrackingManager {

    @Override
    public native void trackPageView(String googleAnalyticsAccount, String historyToken) /*-{
    try {
        var _gaq = $wnd._gaq || [];
        _gaq.push(['_setAccount', googleAnalyticsAccount]);
        _gaq.push(['_trackPageview', historyToken]);
    } catch(err) {
        alert('Failed to execute trackPageview: ' + err);
    }}-*/;

    @Override
    public native void trackEvent(String googleAnalyticsAccount, String category, String action, String label, int msDuration) /*-{
    try {
        var _gaq = $wnd._gaq || [];
        _gaq.push(['_setAccount', googleAnalyticsAccount]);
        _gaq.push(['_trackEvent', category, action, label, msDuration]);
    } catch(err) {
        alert('Failed to execute trackEvent: ' + err);
    }}-*/;
}
