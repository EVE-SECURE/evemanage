package lv.odylab.evemanage.service.user;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.user.User;

public interface UserManagementService {

    LoginDto login(String requestUri, String locale);

    User getUser(Key<User> userKey);

    Key<User> getCurrentUserKey();

    Key<User> getUserKeyByUserID(Long userID);

    void setMainCharacter(String characterName, Key<User> userKey);

}
