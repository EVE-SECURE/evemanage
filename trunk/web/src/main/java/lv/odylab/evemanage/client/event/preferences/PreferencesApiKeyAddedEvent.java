package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddApiKeyActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.ApiKeyDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesApiKeyAddedEvent extends PreferencesTabEvent<PreferencesApiKeyAddedEventHandler> {
    public static final Type<PreferencesApiKeyAddedEventHandler> TYPE = new Type<PreferencesApiKeyAddedEventHandler>();

    private List<ApiKeyDto> apiKeys;
    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;

    public PreferencesApiKeyAddedEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesAddApiKeyActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.apiKeys = response.getApiKeys();
        this.characters = response.getCharacters();
        this.mainCharacter = response.getMainCharacter();
        this.newCharacterNames = response.getNewCharacterNames();
    }

    @Override
    public Type<PreferencesApiKeyAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesApiKeyAddedEventHandler handler) {
        handler.onApiKeyAdded(this);

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