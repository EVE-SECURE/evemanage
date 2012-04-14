package lv.odylab.evemanage.service.user;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.domain.user.User;

public interface UserSynchronizationService {

    void synchronizeMainCharacter(Key<User> userKey);

}
