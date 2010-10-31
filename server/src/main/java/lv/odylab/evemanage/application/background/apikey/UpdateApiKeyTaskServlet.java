package lv.odylab.evemanage.application.background.apikey;

import com.google.inject.Inject;
import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.eve.EveUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateApiKeyTaskServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final EveUpdateService eveUpdateService;

    @Inject
    public UpdateApiKeyTaskServlet(EveUpdateService eveUpdateService) {
        this.eveUpdateService = eveUpdateService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userID = Long.valueOf(req.getParameter("userID"));
            eveUpdateService.updateApiKeysForUser(new Key<User>(User.class, userID));
        } catch (EveApiException e) {
            logger.error("Api key update failed for userID: {}", req.getParameterMap());
            logger.error("Caught EveApiException", e);
        }
    }
}
