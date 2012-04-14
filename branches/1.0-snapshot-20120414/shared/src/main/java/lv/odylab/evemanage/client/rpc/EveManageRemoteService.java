package lv.odylab.evemanage.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.Response;

@RemoteServiceRelativePath("remoteService")
public interface EveManageRemoteService extends RemoteService {

    <T extends Response> T execute(Action<T> action) throws Exception;

}
