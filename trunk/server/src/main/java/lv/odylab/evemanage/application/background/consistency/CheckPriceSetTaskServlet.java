package lv.odylab.evemanage.application.background.consistency;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckPriceSetTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PriceSetDao priceSetDao;
    private final CharacterDao characterDao;

    @Inject
    public CheckPriceSetTaskServlet(PriceSetDao priceSetDao, CharacterDao characterDao) {
        this.priceSetDao = priceSetDao;
        this.characterDao = characterDao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long priceSetID = Long.valueOf(req.getParameter("priceSetID"));
            logger.debug("Checking price set for consistency: {}", priceSetID);
            PriceSet priceSet = priceSetDao.get(new Key<PriceSet>(PriceSet.class, priceSetID));
            CharacterInfo attachedCharacterInfo = priceSet.getAttachedCharacterInfo();
            if (attachedCharacterInfo == null) {
                logger.debug("Price set does not have attached character, skipping");
                return;
            }
            try {
                Character character = characterDao.get(new Key<Character>(Character.class, attachedCharacterInfo.getId()));
                logger.debug("Updating price set attached character information: {} ({})", character.getName(), character.getCharacterID());
                attachedCharacterInfo.setCharacterID(character.getCharacterID());
                attachedCharacterInfo.setName(character.getName());
                attachedCharacterInfo.setCorporationID(character.getCorporationID());
                attachedCharacterInfo.setCorporationName(character.getCorporationName());
                attachedCharacterInfo.setCorporationTicker(character.getCorporationTicker());
                attachedCharacterInfo.setAllianceID(character.getAllianceID());
                attachedCharacterInfo.setAllianceName(character.getAllianceName());
            } catch (NotFoundException e) {
                logger.warn("Attached character does not exist: {}", attachedCharacterInfo.getId());
                priceSet.setAttachedCharacterInfo(null);
            }
            priceSetDao.putWithoutChecks(priceSet);
        } catch (Throwable t) {
            logger.error("Caught Throwable", t);
        }
    }
}
