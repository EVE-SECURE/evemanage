package lv.odylab.evemanage.application.background.blueprint;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.application.exception.validation.InvalidNameException;
import lv.odylab.evemanage.domain.user.User;
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

    private BlueprintManagementService blueprintManagementService;

    @Inject
    public AddBlueprintTaskServlet(BlueprintManagementService blueprintManagementService) {
        this.blueprintManagementService = blueprintManagementService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userID = Long.valueOf(req.getParameter("userID"));
            Long blueprintTypeID = Long.valueOf(req.getParameter("blueprintTypeID"));
            Long itemID = Long.valueOf(req.getParameter("itemID"));
            Integer meLevel = Integer.valueOf(req.getParameter("meLevel"));
            Integer peLevel = Integer.valueOf(req.getParameter("peLevel"));
            String attachedCharacterIdString = req.getParameter("attachedCharacterID");
            Long attachedCharacterID = attachedCharacterIdString != null ? Long.valueOf(attachedCharacterIdString) : null;
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
