package lv.odylab.evemanage.client.widget;

import com.google.gwt.user.client.ui.ListBox;

public class SkillLevelListBox extends ListBox {
    public SkillLevelListBox(Integer level) {
        addItem("0");
        addItem("1");
        addItem("2");
        addItem("3");
        addItem("4");
        addItem("5");
        setLevel(level);
    }

    public void setLevel(Integer level) {
        setSelectedIndex(level);
    }

    public Integer getSelectedLevel() {
        return Integer.valueOf(getValue(getSelectedIndex()));
    }
}
