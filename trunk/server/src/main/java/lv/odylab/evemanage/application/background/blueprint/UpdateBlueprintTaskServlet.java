package lv.odylab.evemanage.application.background.blueprint;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateBlueprintTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BlueprintDao blueprintDao;

    @Inject
    public UpdateBlueprintTaskServlet(BlueprintDao blueprintDao) {
        this.blueprintDao = blueprintDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userID = Long.valueOf(req.getParameter("userID"));
            Long blueprintID = Long.valueOf(req.getParameter("blueprintID"));
            Long characterID = req.getParameter("characterID") == null ? null : Long.valueOf(req.getParameter("characterID"));
            Blueprint blueprint = blueprintDao.get(blueprintID, new Key<User>(User.class, userID));
            if (characterID != null) {
                CharacterInfo characterInfo = new CharacterInfo();
                characterInfo.setCharacterID(characterID);
                characterInfo.setName(req.getParameter("characterName"));
                String corporationID = req.getParameter("corporationID");
                characterInfo.setCorporationID(corporationID == null ? null : Long.valueOf(corporationID));
                characterInfo.setCorporationName(req.getParameter("corporationName"));
                characterInfo.setCorporationTicker(req.getParameter("corporationTicker"));
                String allianceID = req.getParameter("allianceID");
                characterInfo.setAllianceID(allianceID == null ? null : Long.valueOf(allianceID));
                characterInfo.setAllianceName(req.getParameter("allianceName"));
                blueprint.setAttachedCharacterInfo(characterInfo);
            } else {
                blueprint.setAttachedCharacterInfo(null);
            }
            blueprintDao.putWithoutChecks(blueprint);
        } catch (Throwable t) {
            logger.error("Caught Throwable", t);
        }
    }
}
