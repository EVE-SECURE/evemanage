package lv.odylab.evemanage.client.util;

public class IgbChecker {
    public native static boolean isInIgb() /*-{
        return (typeof CCPEVE == 'object');
    }-*/;
}
