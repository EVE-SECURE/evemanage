package lv.odylab.evemanage.domain.user;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;

public class UserDao {
    private final ObjectifyFactory objectifyFactory;

    @Inject
    public UserDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public User get(Long userID) {
        return objectifyFactory.begin().query(User.class)
                .filter("id", userID).get();
    }

    public User get(Key<User> userKey) {
        return objectifyFactory.begin().get(userKey);
    }

    public Key<User> getKey(Long userID) {
        return objectifyFactory.begin().query(User.class)
                .filter("id", userID).getKey();
    }

    public Iterable<Key<User>> getAllKeys() {
        return objectifyFactory.begin().query(User.class).fetchKeys();
    }

    public User getByUserAuthID(String userAuthID) {
        return objectifyFactory.begin().query(User.class)
                .filter("userAuthID", userAuthID).get();
    }

    public Key<User> geKeyByUserAuthID(String userAuthID) {
        return objectifyFactory.begin().query(User.class)
                .filter("userAuthID", userAuthID).getKey();
    }

    public void put(User user) {
        objectifyFactory.begin().put(user);
    }
}