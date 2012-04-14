package lv.odylab.evemanage.client.rpc.dto.user;

import lv.odylab.evemanage.client.rpc.dto.eve.CharacterDto;

import java.io.Serializable;

public class UserDto implements Serializable {
    private Long id;
    private CharacterDto mainCharacter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacterDto getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(CharacterDto mainCharacter) {
        this.mainCharacter = mainCharacter;
    }
}
