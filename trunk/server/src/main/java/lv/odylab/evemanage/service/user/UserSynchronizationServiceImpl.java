package lv.odylab.evemanage.service.user;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.domain.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserSynchronizationServiceImpl implements UserSynchronizationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserDao userDao;
    private CharacterDao characterDao;

    @Inject
    public UserSynchronizationServiceImpl(UserDao userDao, CharacterDao characterDao) {
        this.userDao = userDao;
        this.characterDao = characterDao;
    }

    @Override
    public void synchronizeMainCharacter(Key<User> userKey) {
        logger.debug("Synchronizing main character");
        lv.odylab.evemanage.domain.user.User user = userDao.get(userKey);
        List<CharacterInfo> characterInfos = createCharacterInfos(userKey);
        user.setCharacterInfos(characterInfos);
        if (characterInfos.size() == 0) {
            logger.debug("User does not have any characters, setting main character to null, main character corporation titles to null and character infos to null");
            user.setCharacterInfos(null);
            user.setMainCharacterInfo(null);
            user.setMainCharacterCorporationTitles(null);
        } else if (user.getMainCharacterInfo() == null) {
            CharacterInfo characterInfo = characterInfos.get(0);
            logger.debug("User does not have main character, will set to first random: {} ({})", characterInfo.getName(), characterInfo.getCharacterID());
            user.setMainCharacterInfo(characterInfo);
        } else {
            CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
            Long mainCharacterID = mainCharacterInfo.getCharacterID();
            logger.debug("Checking whether current main character still exists: {} ({})", mainCharacterInfo.getName(), mainCharacterID);
            for (CharacterInfo characterInfo : characterInfos) {
                if (characterInfo.getCharacterID().equals(mainCharacterID)) {
                    logger.debug("Current main character exists, updating only list of character infos");
                    user.setCharacterInfos(characterInfos);
                    userDao.put(user);
                    return;
                }
            }
            CharacterInfo characterInfo = characterInfos.get(0);
            logger.debug("Current main character does not exist, will set to first random: {} ({})", characterInfo.getName(), characterInfo.getCharacterID());
            user.setMainCharacterInfo(characterInfo);
        }
        CharacterInfo mainCharacterInfo = user.getMainCharacterInfo();
        if (mainCharacterInfo != null) {
            Character character = characterDao.get(new Key<Character>(Character.class, mainCharacterInfo.getId()));
            List<String> corporationTitles = character.getCorporationTitles();
            logger.debug("Setting main character corporation titles: {}", corporationTitles);
            user.setMainCharacterCorporationTitles(corporationTitles);
        }
        userDao.put(user);
    }

    private List<CharacterInfo> createCharacterInfos(Key<lv.odylab.evemanage.domain.user.User> userKey) {
        List<CharacterInfo> characterInfos = new ArrayList<CharacterInfo>();
        List<lv.odylab.evemanage.domain.eve.Character> characters = characterDao.getAll(userKey);
        for (lv.odylab.evemanage.domain.eve.Character character : characters) {
            CharacterInfo characterInfo = new CharacterInfo();
            characterInfo.setId(character.getId());
            characterInfo.setCharacterID(character.getCharacterID());
            characterInfo.setName(character.getName());
            characterInfo.setCorporationID(character.getCorporationID());
            characterInfo.setCorporationName(character.getCorporationName());
            characterInfo.setCorporationTicker(character.getCorporationTicker());
            characterInfo.setAllianceID(character.getAllianceID());
            characterInfo.setAllianceName(character.getAllianceName());
            characterInfos.add(characterInfo);
        }
        return characterInfos;
    }
}
