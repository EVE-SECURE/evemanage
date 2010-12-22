package lv.odylab.evemanage.domain.user;

import com.googlecode.objectify.annotation.Unindexed;

import java.io.Serializable;

@Unindexed
public class SkillLevel implements Serializable {
    private Long typeID;
    private Integer level;

    public SkillLevel() {
    }

    public SkillLevel(Long typeID, Integer level) {
        this.typeID = typeID;
        this.level = level;
    }

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
