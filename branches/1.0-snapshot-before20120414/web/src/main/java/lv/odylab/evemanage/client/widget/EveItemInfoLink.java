package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.CcpJsMessages;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;
import lv.odylab.evemanage.client.util.IgbChecker;

public class EveItemInfoLink extends Anchor {
    private Long itemTypeID;

    public EveItemInfoLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, String name, Long itemTypeID, Long itemID) {
        this.itemTypeID = itemTypeID;
        setHTML(name);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showItemInfoUrl(itemTypeID, itemID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.eveWikiUrl(constants.eveOnlineWikiUrl(), name));
        }
    }

    public EveItemInfoLink(EveManageConstants constants, EveManageUrlMessages urlMessages, CcpJsMessages ccpJsMessages, String name, Long itemTypeID) {
        this.itemTypeID = itemTypeID;
        setHTML(name);
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showInfoUrl(itemTypeID));
        } else {
            setTarget("_blank");
            setHref(urlMessages.eveWikiUrl(constants.eveOnlineWikiUrl(), name));
        }
    }

    public EveItemInfoLink(CcpJsMessages ccpJsMessages, Image image, Long itemTypeID) {
        this.itemTypeID = itemTypeID;
        getElement().appendChild(image.getElement());
        if (IgbChecker.isInIgb()) {
            setHref(ccpJsMessages.showInfoUrl(itemTypeID));
        }
    }

    public EveItemInfoLink(CcpJsMessages ccpJsMessages, Image image, Long itemTypeID, Long itemID) {
        this.itemTypeID = itemTypeID;
        getElement().appendChild(image.getElement());
        if (IgbChecker.isInIgb()) {
            if (itemID != null) {
                setHref(ccpJsMessages.showItemInfoUrl(itemTypeID, itemID));
            } else {
                setHref(ccpJsMessages.showInfoUrl(itemTypeID));
            }
        }
    }

    public Long getItemTypeID() {
        return itemTypeID;
    }
}