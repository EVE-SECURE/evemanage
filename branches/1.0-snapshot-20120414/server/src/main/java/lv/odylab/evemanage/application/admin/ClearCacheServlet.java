package lv.odylab.evemanage.application.admin;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.memcache.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearCacheServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
        Stats stats = memcacheService.getStatistics();
        long itemCount = stats.getItemCount();
        logger.info("Clearing memcache, item count in cache: {}", itemCount);
        memcacheService.clearAll();
        resp.getWriter().write("OK, " + itemCount);
    }
}
