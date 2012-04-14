package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.rpc.dto.priceset.PriceSetNameDto;

import java.util.List;

public class PriceSetListBox extends ListBox {
    private final EveManageMessages messages;

    public PriceSetListBox(EveManageMessages messages) {
        this.messages = messages;
    }

    public Long getCurrentPriceSetID() {
        return Long.valueOf(getValue(getSelectedIndex()));
    }

    public void setPriceSetNames(List<PriceSetNameDto> priceSetNames) {
        boolean restoreState = getItemCount() > 0 && getSelectedIndex() != -1;
        String previousState = null;
        if (restoreState) {
            previousState = getValue(getSelectedIndex());
        }
        clear();
        addItem(messages.none(), "-1");
        for (PriceSetNameDto priceSetName : priceSetNames) {
            addItem(priceSetName.getPriceSetName(), String.valueOf(priceSetName.getPriceSetID()));
        }
        if (restoreState) {
            tryToRestoreState(previousState);
        }
    }

    public void tryToRestoreState(String state) {
        for (int i = 0; i < getItemCount(); i++) {
            if (state.equals(getValue(i))) {
                setSelectedIndex(i);
                return;
            }
        }
        setSelectedIndex(0);
    }
}
