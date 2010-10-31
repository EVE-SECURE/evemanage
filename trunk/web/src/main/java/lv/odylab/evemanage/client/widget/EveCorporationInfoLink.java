package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.util.IgbChecker;

public class EveCorporationInfoLink extends Anchor {
    public EveCorporationInfoLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, String name, Long corporationID) {
        setHTML(name);
        setWordWrap(false);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showCorporationInfoUrl(corporationID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.dotlanCorporationUrl(constants.dotlanUrl(), corporationID));
        }
    }

    public EveCorporationInfoLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, Image image, Long corporationID) {
        getElement().appendChild(image.getElement());
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showCorporationInfoUrl(corporationID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.dotlanCorporationUrl(constants.dotlanUrl(), corporationID));
        }
    }
}