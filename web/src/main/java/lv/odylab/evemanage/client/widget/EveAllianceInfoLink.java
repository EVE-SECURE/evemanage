package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.util.IgbChecker;

public class EveAllianceInfoLink extends Anchor {
    public EveAllianceInfoLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, String name, Long allianceID) {
        setHTML(name);
        setWordWrap(false);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showAllianceInfoUrl(allianceID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.dotlanAllianceUrl(constants.dotlanUrl(), allianceID));
        }
    }
}