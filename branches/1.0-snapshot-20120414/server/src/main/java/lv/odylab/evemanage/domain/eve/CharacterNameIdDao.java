package lv.odylab.evemanage.domain.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.ObjectifyFactory;

import java.util.Date;

public class CharacterNameIdDao {
    private final ObjectifyFactory objectifyFactory;

    @Inject
    public CharacterNameIdDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public CharacterNameID getByCharacterID(Long characterID) {
        return objectifyFactory.begin().query(CharacterNameID.class)
                .filter("characterID", characterID).get();
    }

    public CharacterNameID getByCharacterName(String characterName) {
        return objectifyFactory.begin().query(CharacterNameID.class)
                .filter("characterName", characterName).get();
    }

    public void put(CharacterNameID characterNameID) {
        if (characterNameID.getCreatedDate() == null) {
            characterNameID.setCreatedDate(new Date());
        }
        characterNameID.setUpdatedDate(new Date());
        objectifyFactory.begin().put(characterNameID);
    }
}