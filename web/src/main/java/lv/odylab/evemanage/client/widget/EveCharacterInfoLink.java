package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.util.IgbChecker;

public class EveCharacterInfoLink extends Anchor {
    public EveCharacterInfoLink(CcpJsMessages ccpJsMessages, String name, Long characterID) {
        setHTML(name);
        setWordWrap(false);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showCharacterInfoUrl(characterID));
        }
    }

    public EveCharacterInfoLink(CcpJsMessages ccpJsMessages, Image image, Long characterID) {
        getElement().appendChild(image.getElement());
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showCharacterInfoUrl(characterID));
        }
    }
}