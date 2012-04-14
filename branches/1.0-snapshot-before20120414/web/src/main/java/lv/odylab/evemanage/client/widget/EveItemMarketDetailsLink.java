package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.util.IgbChecker;

public class EveItemMarketDetailsLink extends Anchor {
    public EveItemMarketDetailsLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, String name, Long itemTypeID) {
        setHTML(name);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showMarketDetailsUrl(itemTypeID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.eveWikiUrl(constants.eveOnlineWikiUrl(), name));
        }
    }
}