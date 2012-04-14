package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;
import lv.odylab.evemanage.client.rpc.dto.eve.RegionDto;

import java.util.List;

public class RegionListBox extends ListBox {
    public void setRegions(List<RegionDto> regions) {
        for (RegionDto region : regions) {
            addItem(region.getRegionName(), String.valueOf(region.getRegionID()));
        }
    }

    public void selectRegion(RegionDto preferredRegion) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getItemText(i).equals(preferredRegion.getRegionName())) {
                setSelectedIndex(i);
                return;
            }
        }
    }

    public Long getRegionID() {
        return Long.valueOf(getValue(getSelectedIndex()));
    }
}
