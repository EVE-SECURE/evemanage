package lv.odylab.evemanage.client.rpc.dto.user;

import java.io.Serializable;

public class SkillLevelDto implements Serializable {
    private Long typeID;
    private String name;
    private Integer level;

    public Long getTypeID() {
        return typeID;
    }

    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
