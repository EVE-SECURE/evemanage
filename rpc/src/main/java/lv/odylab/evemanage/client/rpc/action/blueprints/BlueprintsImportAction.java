package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(BlueprintsImportActionRunner.class)
public class BlueprintsImportAction implements Action<BlueprintsImportActionResponse> {
    private String importXml;
    private Long attachedCharacterID;
    private String sharingLevel;

    public String getImportXml() {
        return importXml;
    }

    public void setImportXml(String importXml) {
        this.importXml = importXml;
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