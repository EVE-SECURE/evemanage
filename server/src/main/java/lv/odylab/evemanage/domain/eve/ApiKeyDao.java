package lv.odylab.evemanage.domain.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import lv.odylab.evemanage.application.exception.validation.DifferentUserException;
import lv.odylab.evemanage.application.exception.validation.DuplicateApiKeyException;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.shared.eve.ApiKeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ApiKeyDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectifyFactory objectifyFactory;

    @Inject
    public ApiKeyDao(ObjectifyFactory objectifyFactory) {
        this.objectifyFactory = objectifyFactory;
    }

    public ApiKey get(Key<ApiKey> apiKeyKey) {
        return objectifyFactory.begin().get(apiKeyKey);
    }

    public List<ApiKey> getAll(Key<User> userKey) {
        return objectifyFactory.begin().query(ApiKey.class)
                .filter("user", userKey)
                .order("apiKeyUserID").list();
    }

    public Key<ApiKey> getKeyWithCharacterID(Long characterID, Key<User> userKey) {
        return objectifyFactory.begin().query(ApiKey.class)
                .filter("user", userKey)
                .filter("characterInfos.characterID", characterID).getKey();
    }

    public ApiKey getWithCharacterID(Long characterID, Key<User> userKey) {
        return objectifyFactory.begin().query(ApiKey.class)
                .filter("user", userKey)
                .filter("characterInfos.characterID", characterID).get();
    }

    public List<ApiKey> getAllFull(Key<User> userKey) {
        return objectifyFactory.begin().query(ApiKey.class)
                .filter("user", userKey)
                .filter("keyType", ApiKeyType.FULL.toString())
                .order("apiKeyUserID").list();
    }

    public ApiKey getFullForCharacterID(Long characterID, Key<User> userKey) {
        return objectifyFactory.begin().query(ApiKey.class)
                .filter("user", userKey)
                .filter("keyType", ApiKeyType.FULL.toString())
                .filter("characterInfos.characterID", characterID).get();
    }

    public void put(ApiKey apiKey, Key<User> userKey) {
        uniqueApiKey(apiKey);
        sameUser(apiKey, userKey);
        objectifyFactory.begin().put(apiKey);
    }

    public void putWithoutChecks(ApiKey apiKey) {
        objectifyFactory.begin().put(apiKey);
    }

    public void delete(Key<ApiKey> apiKeyKey, Key<User> userKey) {
        sameUser(apiKeyKey, userKey);
        objectifyFactory.begin().delete(apiKeyKey);
    }

    public void deleteWithoutChecks(ApiKey apiKey) {
        objectifyFactory.begin().delete(apiKey);
    }

    private void sameUser(ApiKey apiKey, Key<User> userKey) {
        if (!apiKey.getUser().equals(userKey)) {
            logger.error("User in apiKey and user invoking change are different, probably someone is hackng, apiKey user: {}, invoking user: {}", apiKey.getUser().getId(), userKey.getId());
            throw new DifferentUserException();
        }
    }

    private void sameUser(Key<ApiKey> apiKeyKey, Key<User> userKey) {
        sameUser(get(apiKeyKey), userKey);
    }

    private void uniqueApiKey(ApiKey apiKey) {
        Key<ApiKey> existingApiKeyKey = objectifyFactory.begin().query(ApiKey.class)
                .filter("apiKeyHash", apiKey.getApiKeyHash()).getKey();
        if (apiKey.getId() == null &&
                existingApiKeyKey != null) {
            logger.error("Trying to add api key that is already added, apiKeyUserID: {}", apiKey.getApiKeyUserID());
            throw new DuplicateApiKeyException();
        }
    }
}