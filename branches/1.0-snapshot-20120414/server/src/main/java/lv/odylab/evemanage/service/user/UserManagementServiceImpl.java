package lv.odylab.evemanage.service.user;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.system.SystemProperty;
import lv.odylab.evemanage.domain.system.SystemPropertyDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.SkillLevel;
import lv.odylab.evemanage.domain.user.UserDao;
import lv.odylab.evemanage.shared.eve.PriceFetchOption;
import lv.odylab.evemanage.shared.eve.Region;
import lv.odylab.evemanage.shared.eve.SkillForCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserManagementServiceImpl implements UserManagementService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GoogleAppEngineServices googleAppEngineServices;
    private final SystemPropertyDao systemPropertyDao;
    private final UserDao userDao;
    private final CharacterDao characterDao;

    @Inject
    public UserManagementServiceImpl(GoogleAppEngineServices googleAppEngineServices, SystemPropertyDao systemPropertyDao, UserDao userDao, CharacterDao characterDao) {
        this.googleAppEngineServices = googleAppEngineServices;
        this.systemPropertyDao = systemPropertyDao;
        this.userDao = userDao;
        this.characterDao = characterDao;
    }

    @Override
    public LoginDto login(String requestUri, String locale) {
        UserService userService = googleAppEngineServices.getUserService();
        User googleUser = userService.getCurrentUser();

        LoginDto loginDto = new LoginDto();
        if (googleUser != null) {
            lv.odylab.evemanage.domain.user.User user = ensureUserExists(googleUser);
            loginDto.setLoggedIn(Boolean.TRUE);
            loginDto.setUserID(user.getId());
            loginDto.setEmailAddress(googleUser.getEmail());
            loginDto.setNickname(googleUser.getNickname());
            String logoutUrl = userService.createLogoutURL(requestUri);
            loginDto.setLogoutUrl(createGwtLocaleAwareUrl(requestUri, logoutUrl, locale));
            loginDto.setAdmin(userService.isUserAdmin());
            loginDto.setRoles(user.getRoles());
        } else {
            loginDto.setLoggedIn(Boolean.FALSE);
            String loginUrl = userService.createLoginURL(requestUri);
            loginDto.setLoginUrl(createGwtLocaleAwareUrl(requestUri, loginUrl, locale));
        }
        if (isDevelopmentMode(requestUri)) {
            loginDto.setRussianUrl("?gwt.codesvr=127.0.0.1:9997&locale=ru");
            loginDto.setEnglishUrl("?gwt.codesvr=127.0.0.1:9997");
        } else {
            loginDto.setRussianUrl("?locale=ru_RU");
            loginDto.setEnglishUrl("/");
        }
        String bannerMessage = systemPropertyDao.getProperty(SystemProperty.SYSTEM_BANNER_MESSAGE);
        loginDto.setBannerMessage(bannerMessage);

        return loginDto;
    }

    @Override
    public lv.odylab.evemanage.domain.user.User getUser(Key<lv.odylab.evemanage.domain.user.User> userKey) {
        return userDao.get(userKey);
    }

    @Override
    public Key<lv.odylab.evemanage.domain.user.User> getCurrentUserKey() {
        UserService userService = googleAppEngineServices.getUserService();
        User googleUser = userService.getCurrentUser();
        if (googleUser != null) {
            return userDao.geKeyByUserAuthID(googleUser.getUserId());
        }
        return null;
    }

    @Override
    public Key<lv.odylab.evemanage.domain.user.User> getUserKeyByUserID(Long userID) {
        return userDao.getKey(userID);
    }

    @Override
    public void setMainCharacter(String characterName, Key<lv.odylab.evemanage.domain.user.User> userKey) {
        lv.odylab.evemanage.domain.user.User user = userDao.get(userKey);
        List<CharacterInfo> characterInfos = user.getCharacterInfos();
        for (CharacterInfo characterInfo : characterInfos) {
            if (characterName.equals(characterInfo.getName())) {
                Character character = characterDao.get(new Key<Character>(Character.class, characterInfo.getId()));
                user.setMainCharacterInfo(characterInfo);
                user.setMainCharacterCorporationTitles(character.getCorporationTitles());
                userDao.put(user);
                return;
            }
        }
    }

    @Override
    public List<SkillLevel> getSkillsForCalculation(lv.odylab.evemanage.domain.user.User user) {
        List<SkillLevel> skillLevels = new ArrayList<SkillLevel>();
        Map<Long, SkillLevel> skillLevelMap = new HashMap<Long, SkillLevel>();
        for (SkillForCalculation skillForCalculation : SkillForCalculation.values()) {
            Long typeID = skillForCalculation.getTypeID();
            SkillLevel skillLevel = new SkillLevel(typeID, 5);
            skillLevels.add(skillLevel);
            skillLevelMap.put(typeID, skillLevel);
        }

        List<SkillLevel> userSkillLevels = user.getSkillLevelsForCalculation();
        for (SkillLevel userSkillLevel : userSkillLevels) {
            SkillLevel skillLevel = skillLevelMap.get(userSkillLevel.getTypeID());
            skillLevel.setLevel(userSkillLevel.getLevel());
        }
        return skillLevels;
    }

    @Override
    public void saveSkillLevelsForCalculation(List<SkillLevel> skillLevelsForCalculation, lv.odylab.evemanage.domain.user.User user) {
        user.setSkillLevelsForCalculation(skillLevelsForCalculation);
        userDao.put(user);
    }

    @Override
    public Region getPreferredRegion(lv.odylab.evemanage.domain.user.User user) {
        if (user == null || user.getPreferredRegion() == null) {
            return Region.THE_FORGE;
        } else {
            return Region.valueOf(user.getPreferredRegion());
        }
    }

    @Override
    public PriceFetchOption getPreferredPriceFetchOption(lv.odylab.evemanage.domain.user.User user) {
        if (user == null || user.getPreferredPriceFetchOption() == null) {
            return PriceFetchOption.MEDIAN_BUY_SELL;
        } else {
            return PriceFetchOption.valueOf(user.getPreferredPriceFetchOption());
        }
    }

    @Override
    public void savePriceFetchConfiguration(Region preferredRegion, PriceFetchOption preferredPriceFetchOption, lv.odylab.evemanage.domain.user.User user) {
        user.setPreferredRegion(preferredRegion.toString());
        user.setPreferredPriceFetchOption(preferredPriceFetchOption.toString());
        userDao.put(user);
    }

    private lv.odylab.evemanage.domain.user.User ensureUserExists(User googleUser) {
        lv.odylab.evemanage.domain.user.User user = userDao.getByUserAuthID(googleUser.getUserId());
        if (user == null) {
            user = new lv.odylab.evemanage.domain.user.User();
            user.setUserAuthID(googleUser.getUserId());
            user.setAuthDomain(googleUser.getAuthDomain());
            user.setEmail(googleUser.getEmail());
            user.setNickname(googleUser.getNickname());
            user.setCreatedDate(new Date());
            user.setLastLoginDate(new Date());
            Set<String> roles = new HashSet<String>();
            user.setRoles(roles);
        } else {
            user.setLastLoginDate(new Date());
        }
        userDao.put(user);
        return user;
    }

    private boolean isDevelopmentMode(String requestUri) {
        return "http://127.0.0.1:8888/".equals(requestUri);
    }

    private String createGwtLocaleAwareUrl(String requestUri, String url, String locale) {
        StringBuilder stringBuilder = new StringBuilder(url);
        if (isDevelopmentMode(requestUri)) {
            logger.debug("Running in GWT Development mode");
            stringBuilder.append("?gwt.codesvr=127.0.0.1:9997");
            if ("ru".equals(locale)) {
                stringBuilder.append("%26locale=ru");
            }
        } else {
            if ("ru".equals(locale)) {
                stringBuilder.append("?locale=ru");
            }
        }
        return stringBuilder.toString();
    }
}