package lv.odylab.evemanage.client.rpc.action.login;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.user.LoginDto;

public class LoginActionRunnerImpl implements LoginActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public LoginActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public LoginActionResponse execute(LoginAction action) {
        LoginDto loginDto = clientFacade.login(action.getRequestUri(), action.getLocale());

        LoginActionResponse response = new LoginActionResponse();
        response.setLoginDto(loginDto);
        return response;
    }
}
