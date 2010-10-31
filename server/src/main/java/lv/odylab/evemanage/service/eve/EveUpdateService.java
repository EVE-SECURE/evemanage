package lv.odylab.evemanage.service.eve;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.user.User;

public interface EveUpdateService {

    void updateApiKeysForUser(Key<User> userKey) throws EveApiException;

}
