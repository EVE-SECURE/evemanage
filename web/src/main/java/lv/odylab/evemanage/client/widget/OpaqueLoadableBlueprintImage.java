package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.EveManageMessages;
import lv.odylab.evemanage.client.EveManageResources;

public class OpaqueLoadableBlueprintImage extends Image {
    private EveManageResources resources;
    private EveManageMessages messages;
    private Boolean hasOpacity = false;
    private String titleWithOpacity;
    private String titleWithoutOpacity;

    public OpaqueLoadableBlueprintImage(EveManageResources resources, EveManageMessages messages, String titleWithOpacity, String titleWithoutOpacity) {
        super(resources.blueprintIcon16());
        this.resources = resources;
        this.messages = messages;
        this.titleWithOpacity = titleWithOpacity;
        this.titleWithoutOpacity = titleWithoutOpacity;
    }

    public void setOpacity() {
        hasOpacity = true;
        addStyleName(resources.css().imageOpacity05());
        setTitle(titleWithOpacity);
    }

    public void removeOpacity() {
        hasOpacity = false;
        removeStyleName(resources.css().imageOpacity05());
        setTitle(titleWithoutOpacity);
    }

    public Boolean hasOpacity() {
        return hasOpacity;
    }

    public void startLoading() {
        setResource(resources.spinnerIcon());
        setTitle(messages.loading());
        removeStyleName(resources.css().cursorHand());
    }

    public void stopLoading() {
        setResource(resources.blueprintIcon16());
        setTitle(titleWithoutOpacity);
        addStyleName(resources.css().cursorHand());
    }
}
