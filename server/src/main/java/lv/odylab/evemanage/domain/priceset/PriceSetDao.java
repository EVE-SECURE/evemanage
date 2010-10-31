package lv.odylab.evemanage.domain.priceset;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.evemanage.application.exception.validation.DifferentUserException;
import lv.odylab.evemanage.domain.SharingLevel;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PriceSetDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectifyFactory objectifyFactory;

    @Inject
    public PriceSetDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public List<PriceSet> getAll(Key<User> userKey) {
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("user", userKey)
                .order("name").list();
    }

    public List<PriceSet> getAllForCorporationID(Long corporationID) {
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("attachedCharacterInfo.corporationID", corporationID)
                .filter("sharingLevel", SharingLevel.CORPORATION.toString())
                .order("attachedCharacterInfo.name")
                .order("name").list();
    }

    public List<PriceSet> getAllForAllianceID(Long allianceID) {
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("attachedCharacterInfo.allianceID", allianceID)
                .filter("sharingLevel", SharingLevel.ALLIANCE.toString())
                .order("attachedCharacterInfo.corporationName")
                .order("attachedCharacterInfo.name")
                .order("name").list();
    }

    public PriceSet getByPriceSetID(Long priceSetID, Key<User> userKey) {
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("user", userKey)
                .filter("id", priceSetID).get();
    }

    public PriceSet getForCorporationByPriceSetID(Long priceSetID, Key<User> userKey) {
        User user = objectifyFactory.begin().get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("attachedCharacterInfo.corporationID", mainCharacterInfo.getCorporationID())
                .filter("id", priceSetID).get();
    }

    public PriceSet getForAllianceByPriceSetID(Long priceSetID, Key<User> userKey) {
        User user = objectifyFactory.begin().get(userKey);
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("attachedCharacterInfo.allianceID", mainCharacterInfo.getAllianceID())
                .filter("id", priceSetID).get();
    }

    public PriceSet getByPriceSetName(String priceSetName, Key<User> userKey) {
        return objectifyFactory.begin().query(PriceSet.class)
                .filter("user", userKey)
                .filter("name", priceSetName).get();
    }

    public void put(PriceSet priceSet, Key<User> userKey) {
        sameUser(priceSet, userKey);
        objectifyFactory.begin().put(priceSet);
    }

    public void delete(PriceSet priceSet) {
        objectifyFactory.begin().delete(priceSet);
    }

    private void sameUser(PriceSet priceSet, Key<User> userKey) {
        if (!priceSet.getUser().equals(userKey)) {
            logger.error("User in priceSet and user invoking change are different, probably someone is hackng, priceSet user: {}, invoking user: {}", priceSet.getUser().getId(), userKey.getId());
            throw new DifferentUserException();
        }
    }
}