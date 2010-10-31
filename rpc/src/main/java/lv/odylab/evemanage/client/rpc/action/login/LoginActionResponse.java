package lv.odylab.evemanage.client.rpc.action.login;

import lv.odylab.evemanage.client.rpc.action.Response;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;

public class LoginActionResponse implements Response {
    private static final long serialVersionUID = -6027123900008995995L;

    private LoginDto loginDto;

    public LoginDto getLoginDto() {
        return loginDto;
    }

    public void setLoginDto(LoginDto loginDto) {
        this.loginDto = loginDto;
    }
}