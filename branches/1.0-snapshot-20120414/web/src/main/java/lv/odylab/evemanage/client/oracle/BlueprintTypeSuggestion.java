package lv.odylab.evemanage.client.oracle;

import com.google.gwt.user.client.ui.SuggestOracle;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;

public class BlueprintTypeSuggestion implements SuggestOracle.Suggestion {
    private String displayString;
    private String replacementString;

    BlueprintTypeSuggestion(ItemTypeDto itemType) {
        this.displayString = itemType.getName();
        this.replacementString = itemType.getName();
    }

    @Override
    public String getDisplayString() {
        return displayString;
    }

    @Override
    public String getReplacementString() {
        return replacementString;
    }
}