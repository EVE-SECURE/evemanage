package lv.odylab.evemanage.client.rpc.dto.priceset;

import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;

import java.io.Serializable;
import java.util.List;

public class PriceSetDto implements Serializable {
    private Long id;
    private String name;
    private CharacterNameDto attachedCharacterName;
    private String sharingLevel;
    private String createdDate;
    private String updatedDate;
    private List<PriceSetItemDto> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterNameDto getAttachedCharacterName() {
        return attachedCharacterName;
    }

    public void setAttachedCharacterName(CharacterNameDto attachedCharacterName) {
        this.attachedCharacterName = attachedCharacterName;
    }

    public String getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(String sharingLevel) {
        this.sharingLevel = sharingLevel;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<PriceSetItemDto> getItems() {
        return items;
    }

    public void setItems(List<PriceSetItemDto> items) {
        this.items = items;
    }
}