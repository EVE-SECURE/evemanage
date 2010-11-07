package lv.odylab.evemanage.domain.blueprint;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.evemanage.application.exception.validation.DifferentUserException;
import lv.odylab.evemanage.domain.SharingLevel;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlueprintDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectifyFactory objectifyFactory;

    @Inject
    public BlueprintDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public Blueprint get(Key<Blueprint> blueprintKey) {
        return objectifyFactory.begin().get(blueprintKey);
    }

    public Blueprint get(Long blueprintID, Key<User> userKey) {
        return objectifyFactory.begin().query(Blueprint.class)
                .filter("id", blueprintID)
                .filter("user", userKey).get();
    }

    public List<Blueprint> getAll(Key<User> userKey) {
        return objectifyFactory.begin().query(Blueprint.class)
                .filter("user", userKey)
                .order("itemTypeID").list();
    }

    public List<Blueprint> getAll(Key<User> userKey, Long attachedCharacterID) {
        return objectifyFactory.begin().query(Blueprint.class)
                .filter("user", userKey)
                .filter("attachedCharacterInfo.characterID", attachedCharacterID)
                .order("itemTypeID").list();
    }

    public Iterable<Key<Blueprint>> getAllKeys() {
        return objectifyFactory.begin().query(Blueprint.class).fetchKeys();
    }

    public List<Blueprint> getAllForCorporationID(Long corporationID) {
        return objectifyFactory.begin().query(Blueprint.class)
                .filter("attachedCharacterInfo.corporationID", corporationID)
                .filter("sharingLevel", SharingLevel.CORPORATION.toString())
                .order("attachedCharacterInfo.name")
                .order("itemTypeID").list();
    }

    public List<Blueprint> getAllForAllianceID(Long allianceID) {
        return objectifyFactory.begin().query(Blueprint.class)
                .filter("attachedCharacterInfo.allianceID", allianceID)
                .filter("sharingLevel", SharingLevel.ALLIANCE.toString())
                .order("attachedCharacterInfo.corporationName")
                .order("attachedCharacterInfo.name")
                .order("itemTypeID").list();
    }

    public void put(Blueprint blueprint, Key<User> userKey) {
        sameUser(blueprint, userKey);
        objectifyFactory.begin().put(blueprint);
    }

    public void putWithoutChecks(Blueprint blueprint) {
        objectifyFactory.begin().put(blueprint);
    }

    public void delete(Blueprint blueprint) {
        objectifyFactory.begin().delete(blueprint);
    }

    private void sameUser(Blueprint blueprint, Key<User> userKey) {
        if (!blueprint.getUser().equals(userKey)) {
            logger.error("User in blueprint and user invoking change are different, probably someone is hackng, blueprint user: {}, invoking user: {}", blueprint.getUser().getId(), userKey.getId());
            throw new DifferentUserException();
        }
    }
}
