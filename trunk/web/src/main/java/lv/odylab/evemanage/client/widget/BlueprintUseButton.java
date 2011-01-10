package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Button;
import lv.odylab.evemanage.client.EveManageResources;
import lv.odylab.evemanage.shared.eve.BlueprintUse;

public class BlueprintUseButton extends Button {
    private EveManageResources resources;

    public BlueprintUseButton(EveManageResources resources, BlueprintUse blueprintUse) {
        this.resources = resources;
        setBlueprintUse(blueprintUse);
    }

    public void setBlueprintUse(BlueprintUse blueprintUse) {
        if (BlueprintUse.ORIGINAL.equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintInventionLabel());
            removeStyleName(resources.css().blueprintCopyLabel());
            addStyleName(resources.css().blueprintOriginalLabel());
            setText("Original");
        } else if (BlueprintUse.COPY.equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintInventionLabel());
            removeStyleName(resources.css().blueprintOriginalLabel());
            addStyleName(resources.css().blueprintCopyLabel());
            setText("Copy");
        } else if (BlueprintUse.INVENTION.equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintOriginalLabel());
            removeStyleName(resources.css().blueprintCopyLabel());
            addStyleName(resources.css().blueprintInventionLabel());
            setText("Invention");
        } else {
            setText("UNKNOWN");
        }
    }
}
