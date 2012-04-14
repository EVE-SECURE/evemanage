package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.EveManageResources;

public class SkillBookImage extends Image {
    private EveManageResources resources;

    public SkillBookImage(EveManageResources resources, Integer level) {
        this.resources = resources;
        setLevel(level);
    }

    public void setLevel(Integer level) {
        if (level == 5) {
            setResource(resources.skill());
        } else {
            setResource(resources.skillNotMaxed());
        }
    }
}
