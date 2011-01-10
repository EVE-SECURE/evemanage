package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;

import java.util.List;

public class PriceFetchOptionListBox extends ListBox {
    private EveManageMessages messages;

    public PriceFetchOptionListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setPriceFetchOptions(List<PriceFetchOption> priceFetchOptions) {
        for (PriceFetchOption priceFetchOption : priceFetchOptions) {
            addItem(priceFetchOption);
        }
    }

    public void addItem(PriceFetchOption item) {
        if (PriceFetchOption.MEDIAN_BUY_SELL.equals(item)) {
            super.addItem(messages.medianBuySell(), item.toString());
        } else if (PriceFetchOption.MEDIAN_BUY.equals(item)) {
            super.addItem(messages.medianBuy(), item.toString());
        } else if (PriceFetchOption.MEDIAN_SELL.equals(item)) {
            super.addItem(messages.medianSell(), item.toString());
        } else {
            super.addItem("UNKNOWN");
        }
    }

    public void selectFetchOptions(PriceFetchOption preferredPriceFetchOption) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getValue(i).equals(preferredPriceFetchOption.toString())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public String getPriceFetchOption() {
        return getValue(getSelectedIndex());
    }
}
