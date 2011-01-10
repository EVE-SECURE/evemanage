package lv.odylab.evemanage.client.rpc.dto.priceset;

import lv.odylab.evemanage.client.rpc.dto.eve.CharacterNameDto;
import lv.odylab.evemanage.shared.eve.SharingLevel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PriceSetDto implements Serializable {
    private Long id;
    private String name;
    private CharacterNameDto attachedCharacterName;
    private SharingLevel sharingLevel;
    private Date createdDate;
    private Date updatedDate;
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

    public SharingLevel getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(SharingLevel sharingLevel) {
        this.sharingLevel = sharingLevel;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<PriceSetItemDto> getItems() {
        return items;
    }

    public void setItems(List<PriceSetItemDto> items) {
        this.items = items;
    }
}