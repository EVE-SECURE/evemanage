package lv.odylab.evemanage.application.background.apikey;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartApiKeyUpdateCronServlet extends HttpServlet implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UpdateApiKeyTaskLauncher updateApiKeyTaskLauncher;

    @Inject
    public StartApiKeyUpdateCronServlet(UpdateApiKeyTaskLauncher updateApiKeyTaskLauncher) {
        this.updateApiKeyTaskLauncher = updateApiKeyTaskLauncher;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received request to start api key update (from cron or administrator)");
        updateApiKeyTaskLauncher.launchForAll();
    }
}
