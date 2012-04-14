package lv.odylab.evemanage.client.rpc.dto.user;

import java.util.Comparator;

public class SkillLevelDtoComparator implements Comparator<SkillLevelDto> {

    @Override
    public int compare(SkillLevelDto skillLevelDto1, SkillLevelDto skillLevelDto2) {
        return skillLevelDto1.getName().compareTo(skillLevelDto2.getName());
    }
}
