package lv.odylab.evemanage.service.eve;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.background.blueprint.UpdateBlueprintTaskLauncher;
import lv.odylab.evemanage.application.background.priceset.UpdatePriceSetTaskLauncher;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.user.UserSynchronizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CharacterSynchronizationServiceImpl implements CharacterSynchronizationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserSynchronizationService userSynchronizationService;
    private final UpdateBlueprintTaskLauncher updateBlueprintTaskLauncher;
    private final UpdatePriceSetTaskLauncher updatePriceSetTaskLauncher;

    @Inject
    public CharacterSynchronizationServiceImpl(UserSynchronizationService userSynchronizationService, UpdateBlueprintTaskLauncher updateBlueprintTaskLauncher, UpdatePriceSetTaskLauncher updatePriceSetTaskLauncher) {
        this.userSynchronizationService = userSynchronizationService;
        this.updateBlueprintTaskLauncher = updateBlueprintTaskLauncher;
        this.updatePriceSetTaskLauncher = updatePriceSetTaskLauncher;
    }

    @Override
    public void synchronizeCreateCharacter(Character character, Key<User> userKey) {
        logger.debug("Going to invoke main character synchronization, since user might not have main character");
        userSynchronizationService.synchronizeMainCharacter(userKey);
    }

    @Override
    public void synchronizeDeleteCharacter(Long characterID, Key<User> userKey) {
        logger.debug("Invoking blueprint synchronization task launcher for deleted character: ({})", characterID);
        updateBlueprintTaskLauncher.launchForDelete(userKey.getId(), characterID);
        logger.debug("Invoking price set synchronization task launcher for deleted character: ({})", characterID);
        updatePriceSetTaskLauncher.launchForDelete(userKey.getId(), characterID);

        logger.debug("Going to invoke main character synchronization, since main character might be deleted");
        userSynchronizationService.synchronizeMainCharacter(userKey);
    }

    @Override
    public void synchronizeDetachCharacters(List<Character> detachedCharacters, Key<User> userKey) {
        logger.debug("Going to invoke blueprint and price set synchronization task launchers for each character");
        for (Character character : detachedCharacters) {
            logger.debug("Invoking blueprint synchronization task launcher for detached character: {} ({})", character.getName(), character.getCharacterID());
            updateBlueprintTaskLauncher.launchForDetach(userKey.getId(), character);
            logger.debug("Invoking price set synchronization task launcher for detached character: {} ({})", character.getName(), character.getCharacterID());
            updatePriceSetTaskLauncher.launchForDetach(userKey.getId(), character);
        }

        logger.debug("Going to invoke main character synchronization, characters infos must be updated");
        userSynchronizationService.synchronizeMainCharacter(userKey);
    }

    @Override
    public void synchronizeUpdateCharacters(List<Character> updatedCharacters, Key<User> userKey) {
        logger.debug("Going to invoke blueprint and price set synchronization task launchers for each character");
        for (Character character : updatedCharacters) {
            logger.debug("Invoking blueprint synchronization task launcher for updated character: {} ({})", character.getName(), character.getCharacterID());
            updateBlueprintTaskLauncher.launchForUpdate(userKey.getId(), character);
            logger.debug("Invoking price set synchronization task launcher for updated character: {} ({})", character.getName(), character.getCharacterID());
            updatePriceSetTaskLauncher.launchForUpdate(userKey.getId(), character);
        }

        logger.debug("Going to invoke main character synchronization, characters infos must be updated");
        userSynchronizationService.synchronizeMainCharacter(userKey);
    }
}
