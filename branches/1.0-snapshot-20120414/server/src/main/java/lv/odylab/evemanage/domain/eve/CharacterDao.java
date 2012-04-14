package lv.odylab.evemanage.domain.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.evemanage.application.exception.validation.DifferentUserException;
import lv.odylab.evemanage.application.exception.validation.DuplicateCharacterException;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CharacterDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectifyFactory objectifyFactory;

    @Inject
    public CharacterDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public Character get(Key<Character> characterKey) {
        return objectifyFactory.begin().get(characterKey);
    }

    public Character getByCharacterID(Long characterID, Key<User> userKey) {
        return objectifyFactory.begin().query(Character.class)
                .filter("characterID", characterID)
                .filter("user", userKey).get();
    }

    public List<Character> getAll(Key<User> userKey) {
        return objectifyFactory.begin().query(Character.class)
                .filter("user", userKey)
                .order("name").list();
    }

    public List<Character> getAll(Key<ApiKey> apiKeyKey, Key<User> userKey) {
        return objectifyFactory.begin().query(Character.class)
                .filter("apiKey", apiKeyKey)
                .filter("user", userKey)
                .order("name").list();
    }

    public List<Character> getAllWithoutApiKey(Key<User> userKey) {
        return objectifyFactory.begin().query(Character.class)
                .filter("apiKey", null)
                .filter("user", userKey)
                .order("name").list();
    }

    public void put(Character character, Key<User> userKey) {
        uniqueCharacter(character);
        sameUser(character, userKey);
        objectifyFactory.begin().put(character);
    }

    public void putWithoutChecks(Character character) {
        objectifyFactory.begin().put(character);
    }

    public void delete(Key<Character> characterKey, Key<User> userKey) {
        sameUser(characterKey, userKey);
        objectifyFactory.begin().delete(characterKey);
    }

    private void uniqueCharacter(Character character) {
        Key<Character> existingCharacter = objectifyFactory.begin().query(Character.class)
                .filter("characterID", character.getCharacterID()).getKey();
        if (character.getId() == null &&
                existingCharacter != null) {
            logger.error("Trying to add character that is already added: {} ({})", character.getName(), character.getCharacterID());
            throw new DuplicateCharacterException();
        }
    }

    private void sameUser(Character character, Key<User> userKey) {
        if (!character.getUser().equals(userKey)) {
            logger.error("User in character and user invoking change are different, probably someone is hackng, character user: {}, invoking user: {}", character.getUser().getId(), userKey.getId());
            throw new DifferentUserException();
        }
    }

    private void sameUser(Key<Character> characterKey, Key<User> userKey) {
        sameUser(get(characterKey), userKey);
    }
}