package lv.odylab.evemanage.application.background.blueprint;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.integration.evedb.EveDbGateway;
import lv.odylab.evemanage.service.blueprint.BlueprintManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBlueprintTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveDbGateway eveDbGateway;
    private final BlueprintManagementService blueprintManagementService;

    @Inject
    public AddBlueprintTaskServlet(EveDbGateway eveDbGateway, BlueprintManagementService blueprintManagementService) {
        this.eveDbGateway = eveDbGateway;
        this.blueprintManagementService = blueprintManagementService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userID = Long.valueOf(req.getParameter("userID"));
            String blueprintTypeIdString = req.getParameter("blueprintTypeID");
            Long blueprintTypeID = blueprintTypeIdString == null ? null : Long.valueOf(blueprintTypeIdString);
            String blueprintTypeName = req.getParameter("blueprintTypeName");
            if (blueprintTypeName != null) {
                blueprintTypeID = eveDbGateway.getTypeID(blueprintTypeName);
            }
            String itemIdString = req.getParameter("itemID");
            Long itemID = itemIdString == null ? null : Long.valueOf(itemIdString);
            Integer meLevel = Integer.valueOf(req.getParameter("meLevel"));
            Integer peLevel = Integer.valueOf(req.getParameter("peLevel"));
            String attachedCharacterIdString = req.getParameter("attachedCharacterID");
            Long attachedCharacterID = attachedCharacterIdString == null ? null : Long.valueOf(attachedCharacterIdString);
            String sharingLevel = req.getParameter("sharingLevel");
            blueprintManagementService.createBlueprint(blueprintTypeID, itemID, meLevel, peLevel, attachedCharacterID, sharingLevel, new Key<User>(User.class, userID));
        } catch (EveDbException e) {
            logger.error("Blueprint add failed for userID: {}", req.getParameterMap());
            logger.error("Caught EveDbException", e);
        } catch (InvalidNameException e) {
            logger.error("Blueprint add failed for userID: {}", req.getParameterMap());
            logger.error("Caught InvalidNameException", e);
        } catch (Throwable t) {
            logger.error("Caught Throwable", t);
        }
    }
}
