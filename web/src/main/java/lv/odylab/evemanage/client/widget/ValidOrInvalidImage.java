package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.EveManageResources;

public class ValidOrInvalidImage extends Image {
    private EveManageResources resources;

    public ValidOrInvalidImage(EveManageResources resources, Boolean isValid) {
        this.resources = resources;
        setValid(isValid);
    }

    private void setValid(Boolean valid) {
        if (Boolean.TRUE.equals(valid)) {
            setResource(resources.okIcon());
        } else {
            setResource(resources.nokIcon());
        }
    }
}

