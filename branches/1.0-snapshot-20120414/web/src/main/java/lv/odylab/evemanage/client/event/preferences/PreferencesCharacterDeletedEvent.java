package lv.odylab.evemanage.client.event.preferences;

import lv.odylab.evemanage.client.EveManageConstants;
import lv.odylab.evemanage.client.rpc.action.preferences.PreferencesDeleteCharacterActionResponse;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;
import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.client.tracking.TrackingManager;

import java.util.List;

public class PreferencesCharacterDeletedEvent extends PreferencesTabEvent<PreferencesCharacterDeletedEventHandler> {
    public static final Type<PreferencesCharacterDeletedEventHandler> TYPE = new Type<PreferencesCharacterDeletedEventHandler>();

    private List<CharacterDto> characters;
    private CharacterDto mainCharacter;
    private List<CharacterNameDto> newCharacterNames;
    private List<CharacterNameDto> characterNames;

    public PreferencesCharacterDeletedEvent(TrackingManager trackingManager, EveManageConstants constants, PreferencesDeleteCharacterActionResponse response, Long msDuration) {
        super(trackingManager, constants, msDuration);

        this.characters = response.getCharacters();
        this.mainCharacter = response.getMainCharacter();
        this.newCharacterNames = response.getNewCharacterNames();
        this.characterNames = response.getCharacterNames();
    }

    @Override
    public Type<PreferencesCharacterDeletedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PreferencesCharacterDeletedEventHandler handler) {
        handler.onCharacterDeleted(this);

        trackEvent();
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public List<CharacterDto> getCharacters() {
        return characters;
    }

    public List<CharacterNameDto> getNewCharacterNames() {
        return newCharacterNames;
    }

    public List<CharacterNameDto> getCharacterNames() {
        return characterNames;
    }
}