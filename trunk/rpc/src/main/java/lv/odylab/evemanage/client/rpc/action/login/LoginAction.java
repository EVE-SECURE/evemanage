package lv.odylab.evemanage.client.rpc.action.login;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(LoginActionRunner.class)
public class LoginAction implements Action<LoginActionResponse> {
    private String requestUri;
    private String locale;

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}