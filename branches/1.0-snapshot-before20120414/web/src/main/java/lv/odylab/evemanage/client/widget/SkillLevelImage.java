package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.Image;
import lv.odylab.evemanage.client.EveManageResources;

public class SkillLevelImage extends Image {
    private EveManageResources resources;

    public SkillLevelImage(EveManageResources resources, Integer level) {
        this.resources = resources;
        setLevel(level);
    }

    public void setLevel(Integer level) {
        if (level == 0) {
            setResource(resources.level0());
        } else if (level == 1) {
            setResource(resources.level1());
        } else if (level == 2) {
            setResource(resources.level2());
        } else if (level == 3) {
            setResource(resources.level3());
        } else if (level == 4) {
            setResource(resources.level4());
        } else if (level == 5) {
            setResource(resources.level5());
        }
    }
}
