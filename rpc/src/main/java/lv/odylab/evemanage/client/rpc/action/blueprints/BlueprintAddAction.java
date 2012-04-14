package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(BlueprintAddActionRunner.class)
public class BlueprintAddAction implements Action<BlueprintAddActionResponse> {
    private String blueprintTypeName;
    private Integer meLevel;
    private Integer peLevel;
    private Long attachedCharacterID;
    private String sharingLevel;

    public String getBlueprintTypeName() {
        return blueprintTypeName;
    }

    public void setBlueprintTypeName(String blueprintTypeName) {
        this.blueprintTypeName = blueprintTypeName;
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

    public String getSharingLevel() {
        return sharingLevel;
    }

    public void setSharingLevel(String sharingLevel) {
        this.sharingLevel = sharingLevel;
    }
}