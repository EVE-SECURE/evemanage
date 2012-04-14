package lv.odylab.evemanage.client.rpc.action.preferences;

import com.google.inject.Inject;
import lv.odylab.evemanage.application.EveManageClientFacade;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.user.UserDto;

public class PreferencesSetMainCharacterActionRunnerImpl implements PreferencesSetMainCharacterActionRunner {
    private EveManageClientFacade clientFacade;

    @Inject
    public PreferencesSetMainCharacterActionRunnerImpl(EveManageClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public PreferencesSetMainCharacterActionResponse execute(PreferencesSetMainCharacterAction action) throws Exception {
        clientFacade.setMainCharacter(action.getCharacterName());

        UserDto user = clientFacade.getCurrentUser();
        CharacterDto mainCharacter = user.getMainCharacter();

        PreferencesSetMainCharacterActionResponse response = new PreferencesSetMainCharacterActionResponse();
        response.setMainCharacter(mainCharacter);
        return response;
    }
}