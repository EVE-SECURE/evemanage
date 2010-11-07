package lv.odylab.evemanage.application.background.consistency;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageServletModuleMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartConsistencyCheckServlet extends HttpServlet implements EveManageServletModuleMapping {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CheckBlueprintTaskLauncher checkBlueprintTaskLauncher;
    private final CheckPriceSetTaskLauncher checkPriceSetTaskLauncher;

    @Inject
    public StartConsistencyCheckServlet(CheckBlueprintTaskLauncher checkBlueprintTaskLauncher, CheckPriceSetTaskLauncher checkPriceSetTaskLauncher) {
        this.checkBlueprintTaskLauncher = checkBlueprintTaskLauncher;
        this.checkPriceSetTaskLauncher = checkPriceSetTaskLauncher;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received request to start consistency check (from administrator)");
        checkBlueprintTaskLauncher.launchForAll();
        checkPriceSetTaskLauncher.launchForAll();
    }
}
