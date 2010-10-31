package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesAddCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesCharacterAddedEvent extends PreferencesTabEvent<PreferencesCharacterAddedEventHandler> {
    public static final Type<PreferencesCharacterAddedEventHandler> TYPE = new Type<PreferencesCharacterAddedEventHandler>();

    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<CharacterNameDto> characterNames;

    public PreferencesCharacterAddedEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesAddCharacterActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.characters = response.getCharacters();
        this.mainCharacter = response.getMainCharacter();
        this.newCharacterNames = response.getNewCharacterNames();
        this.characterNames = response.getCharacterNames();
    }

    @Override
    public Type<PreferencesCharacterAddedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesCharacterAddedEventHandler handler) {
        handler.onCharacterAdded(this);

        trackEvent();
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

    public List<CharacterNameDto> getCharacterNames() {
        return characterNames;
    }
}