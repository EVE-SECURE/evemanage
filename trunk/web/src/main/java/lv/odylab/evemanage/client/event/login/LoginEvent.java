package lv.odylab.evemanage.client.event.login;

import com.google.gwt.event.shared.GwtEvent;
import lv.odylab.evemanage.client.rpc.action.login.LoginActionResponse;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
    public static final Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

    private LoginDto loginDto;

    public LoginEvent(LoginActionResponse response) {
        this.loginDto = response.getLoginDto();
    }

    public LoginDto getLoginDto() {
        return loginDto;
    }

    @Override
    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginEventHandler handler) {
        handler.onLogin(this);
    }
}
