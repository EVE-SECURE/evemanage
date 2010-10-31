package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteApiKeyActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesApiKeyDeletedEvent extends PreferencesTabEvent<PreferencesApiKeyDeletedEventHandler> {
    public static final Type<PreferencesApiKeyDeletedEventHandler> TYPE = new Type<PreferencesApiKeyDeletedEventHandler>();

    private List<ApiKeyDto> apiKeys;
    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;

    public PreferencesApiKeyDeletedEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesDeleteApiKeyActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.apiKeys = response.getApiKeys();
        this.characters = response.getCharacters();
        this.mainCharacter = response.getMainCharacter();
        this.newCharacterNames = response.getNewCharacterNames();
    }

    @Override
    public Type<PreferencesApiKeyDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesApiKeyDeletedEventHandler handler) {
        handler.onApiKeyDeleted(this);

        trackEvent();
    }

    public List<ApiKeyDto> getApiKeys() {
        return apiKeys;
    }

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public List<CharacterNameDto> getNewCharacterNames() {
        return newCharacterNames;
    }
}