package lv.odylab.evemanage.service.user;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.eve.Region;
import lv.odylab.evemanage.domain.user.PriceFetchOption;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.User;

import java.util.List;

public interface UserManagementService {

    LoginDto login(String requestUri, String locale);

    User getUser(Key<User> userKey);

    Key<User> getCurrentUserKey();

    Key<User> getUserKeyByUserID(Long userID);

    void setMainCharacter(String characterName, Key<User> userKey);

    List<SkillLevel> getSkillsForCalculation(User user);

    void saveSkillLevelsForCalculation(List<SkillLevel> skillLevelsForCalculation, User user);

    Region getPreferredRegion(User user);

    PriceFetchOption getPreferredPriceFetchOption(User user);

    void savePriceFetchConfiguration(Region preferredRegion, PriceFetchOption preferredPriceFetchOption, User user);

}
