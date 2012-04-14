package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;
import lv.odylab.evemanage.shared.eve.SharingLevel;

@RunnedBy(BlueprintSaveActionRunner.class)
public class BlueprintSaveAction implements Action<BlueprintSaveActionResponse> {
    private Long blueprintID;
    private Integer meLevel;
    private Integer peLevel;
    private Long attachedCharacterID;
    private SharingLevel sharingLevel;
    private Long itemID;

    public Long getBlueprintID() {
        return blueprintID;
    }

    public void setBlueprintID(Long blueprintID) {
        this.blueprintID = blueprintID;
    }

    public Integer getMeLevel() {
        return meLevel;
    }

    public void setMeLevel(Integer meLevel) {
        this.meLevel = meLevel;
    }

    public Integer getPeLevel() {
        return peLevel;
    }

    public void setPeLevel(Integer peLevel) {
        this.peLevel = peLevel;
    }

    public Long getAttachedCharacterID() {
        return attachedCharacterID;
    }

    public void setAttachedCharacterID(Long attachedCharacterID) {
        this.attachedCharacterID = attachedCharacterID;
    }

    public SharingLevel getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(SharingLevel sharingLevel) {
        this.sharingLevel = sharingLevel;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }
}