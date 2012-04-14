package lv.odylab.evemanage.application.background.priceset;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import lv.odylab.evemanage.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdatePriceSetTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PriceSetDao priceSetDao;

    @Inject
    public UpdatePriceSetTaskServlet(PriceSetDao priceSetDao) {
        this.priceSetDao = priceSetDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userID = Long.valueOf(req.getParameter("userID"));
            Long priceSetID = Long.valueOf(req.getParameter("priceSetID"));
            Long characterID = req.getParameter("characterID") == null ? null : Long.valueOf(req.getParameter("characterID"));
            PriceSet priceSet = priceSetDao.get(priceSetID, new Key<User>(User.class, userID));
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
                priceSet.setAttachedCharacterInfo(characterInfo);
            } else {
                priceSet.setAttachedCharacterInfo(null);
            }
            priceSetDao.putWithoutChecks(priceSet);
        } catch (Throwable t) {
            logger.error("Caught Throwable", t);
        }
    }
}
