package lv.odylab.evemanage.client.oracle;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SuggestOracle;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.client.rpc.dto.ItemTypeDto;
import lv.odylab.evemanage.client.util.EveImageUrlProvider;

public class TypeSuggestion implements SuggestOracle.Suggestion {
    private String displayString;
    private String replacementString;

    TypeSuggestion(EveManageResources resources, EveImageUrlProvider imageUrlProvider, ItemTypeDto itemTypeDto) {
        Image image = new Image(imageUrlProvider.getImage32Url(itemTypeDto.getItemCategoryID(), itemTypeDto.getItemTypeID(), itemTypeDto.getGraphicIcon()));
        image.addStyleName(resources.css().image32());
        this.displayString = image + itemTypeDto.getName();
        this.replacementString = itemTypeDto.getName();
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
