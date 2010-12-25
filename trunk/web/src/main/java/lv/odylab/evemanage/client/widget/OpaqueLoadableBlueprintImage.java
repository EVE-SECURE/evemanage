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
        super(resources.blueprintNotUsedIcon());
        this.resources = resources;
        this.messages = messages;
        this.titleWithOpacity = titleWithOpacity;
        this.titleWithoutOpacity = titleWithoutOpacity;
    }

    public void setOpacity() {
        hasOpacity = true;
        setResource(resources.blueprintNotUsedIcon());
        setTitle(titleWithOpacity);
    }

    public void removeOpacity() {
        hasOpacity = false;
        setResource(resources.blueprintIcon());
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
        setResource(resources.blueprintIcon());
        setTitle(titleWithoutOpacity);
        addStyleName(resources.css().cursorHand());
    }
}
