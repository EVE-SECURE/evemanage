package lv.odylab.evemanage.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.Response;

public interface EveManageRemoteServiceAsync {

    <T extends Response> void execute(Action<T> action, AsyncCallback<T> async);

}
