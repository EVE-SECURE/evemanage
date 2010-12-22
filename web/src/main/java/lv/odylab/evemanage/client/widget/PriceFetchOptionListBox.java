package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.rpc.dto.user.PriceFetchOptionDto;

import java.util.List;

public class PriceFetchOptionListBox extends ListBox {
    private EveManageMessages messages;

    public PriceFetchOptionListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    public void setPriceFetchOptions(List<PriceFetchOptionDto> priceFetchOptions) {
        for (PriceFetchOptionDto priceFetchOption : priceFetchOptions) {
            addItem(priceFetchOption.getName());
        }
    }

    @Override
    public void addItem(String item) {
        if (item.equals("BUY_SELL_AVG")) {
            super.addItem(messages.averageBuySell(), item);
        } else if (item.equals("BUY_AVG")) {
            super.addItem(messages.averageBuy(), item);
        } else if (item.equals("SELL_AVG")) {
            super.addItem(messages.averageSell(), item);
        } else {
            super.addItem("UNKNOWN");
        }
    }

    public void selectFetchOptions(PriceFetchOptionDto preferredPriceFetchOption) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getItemText(i).equals(preferredPriceFetchOption.getName())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public String getPriceFetchOption() {
        return getValue(getSelectedIndex());
    }
}
