package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Button;
import lv.odylab.evemanage.client.EveManageResources;

public class BlueprintUseButton extends Button {
    private EveManageResources resources;

    public BlueprintUseButton(EveManageResources resources, String blueprintUse) {
        this.resources = resources;
        setBlueprintUse(blueprintUse);
    }

    // TODO remove string constant usage
    public void setBlueprintUse(String blueprintUse) {
        if ("ORIGINAL".equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintInventionLabel());
            removeStyleName(resources.css().blueprintCopyLabel());
            addStyleName(resources.css().blueprintOriginalLabel());
            setText("Original");
        } else if ("COPY".equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintInventionLabel());
            removeStyleName(resources.css().blueprintOriginalLabel());
            addStyleName(resources.css().blueprintCopyLabel());
            setText("Copy");
        } else if ("INVENTION".equals(blueprintUse)) {
            removeStyleName(resources.css().blueprintOriginalLabel());
            removeStyleName(resources.css().blueprintCopyLabel());
            addStyleName(resources.css().blueprintInventionLabel());
            setText("Invention");
        } else {
            setText("UNKNOWN");
        }
    }
}
