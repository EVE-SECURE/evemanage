package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.EveManageUrlMessages;

public class EveMetricsItemPriceLink extends Anchor {
    public EveMetricsItemPriceLink(EveManageConstants constants, EveManageUrlMessages urlMessages, Image image, Long itemCategoryID, Long itemTypeID) {
        setTarget("_blank");
        getElement().appendChild(image.getElement());
        setHref(urlMessages.eveMetricsItemPriceUrl(constants.eveMetricsUrl(), itemCategoryID, itemTypeID));
    }
}