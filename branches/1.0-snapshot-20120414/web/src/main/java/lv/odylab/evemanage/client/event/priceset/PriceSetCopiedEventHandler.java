package lv.odylab.evemanage.client.event.priceset;

import com.google.gwt.event.shared.EventHandler;

public interface PriceSetCopiedEventHandler extends EventHandler {

    void onPriceSetCopied(PriceSetCopiedEvent event);

}