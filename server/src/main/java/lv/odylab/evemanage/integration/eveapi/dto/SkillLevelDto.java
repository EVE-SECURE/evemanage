package lv.odylab.evemanage.integration.eveapi.dto;

import java.io.Serializable;

public class SkillLevelDto implements Serializable {
    private Long typeID;
    private Integer level;

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
