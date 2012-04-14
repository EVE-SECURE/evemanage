package lv.odylab.evemanage.application.background.consistency;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class CheckBlueprintTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BlueprintDao blueprintDao;
    private final CharacterDao characterDao;

    @Inject
    public CheckBlueprintTaskServlet(BlueprintDao blueprintDao, CharacterDao characterDao) {
        this.blueprintDao = blueprintDao;
        this.characterDao = characterDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long blueprintID = Long.valueOf(req.getParameter("blueprintID"));
            logger.debug("Checking blueprint for consistency: {}", blueprintID);
            Blueprint blueprint = blueprintDao.get(new Key<Blueprint>(Blueprint.class, blueprintID));
            CharacterInfo attachedCharacterInfo = blueprint.getAttachedCharacterInfo();
            if (attachedCharacterInfo == null) {
                logger.debug("Blueprint does not have attached character, skipping");
                return;
            }
            try {
                Character character = characterDao.get(new Key<Character>(Character.class, attachedCharacterInfo.getId()));
                logger.debug("Updating blueprint attached character information: {} ({})", character.getName(), character.getCharacterID());
                attachedCharacterInfo.setCharacterID(character.getCharacterID());
                attachedCharacterInfo.setName(character.getName());
                attachedCharacterInfo.setCorporationID(character.getCorporationID());
                attachedCharacterInfo.setCorporationName(character.getCorporationName());
                attachedCharacterInfo.setCorporationTicker(character.getCorporationTicker());
                attachedCharacterInfo.setAllianceID(character.getAllianceID());
                attachedCharacterInfo.setAllianceName(character.getAllianceName());
            } catch (NotFoundException e) {
                logger.warn("Attached character does not exist: {}", attachedCharacterInfo.getId());
                blueprint.setAttachedCharacterInfo(null);
            }
            blueprint.setUpdatedDate(new Date());
            blueprintDao.putWithoutChecks(blueprint);
        } catch (Throwable t) {
            logger.error("Caught Throwable", t);
        }
    }
}
