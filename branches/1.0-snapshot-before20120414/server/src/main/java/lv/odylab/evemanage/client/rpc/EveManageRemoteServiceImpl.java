package lv.odylab.evemanage.client.rpc;

import com.google.appengine.api.users.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.ActionRunner;
import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EveManageRemoteServiceImpl extends RemoteServiceServlet implements EveManageRemoteService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Injector injector;
    private GoogleAppEngineServices appEngineServices;

    @Inject
    public EveManageRemoteServiceImpl(Injector injector, GoogleAppEngineServices appEngineServices) {
        this.injector = injector;
        this.appEngineServices = appEngineServices;
    }

    @Override
    public <T extends Response> T execute(Action<T> action) throws Exception {
        RunnedBy runnedBy = action.getClass().getAnnotation(RunnedBy.class);
        Class<? extends ActionRunner> clazz = runnedBy.value();
        ActionRunner actionRunner = injector.getInstance(clazz);

        User user = appEngineServices.getUserService().getCurrentUser();
        String userNickname = "anonymous";
        if (user != null) {
            userNickname = user.getNickname();
        }
        String ipAddress = getThreadLocalRequest().getRemoteAddr();
        String className = action.getClass().getSimpleName();
        logger.info("User {} (ip: {}) executing action: {}", new Object[]{userNickname, ipAddress, className});

        try {
            return (T) actionRunner.execute(action);
        } catch (Exception e) {
            logger.error("Caught Exception", e);
            throw new RpcException(e.getMessage());
        }
    }
}
