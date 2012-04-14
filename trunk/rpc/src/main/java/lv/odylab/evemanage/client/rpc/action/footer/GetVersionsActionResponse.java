package lv.odylab.evemanage.client.rpc.action.footer;

import lv.odylab.evemanage.client.rpc.action.Response;

public class GetVersionsActionResponse implements Response {
    private String eveManageVersion;
    private String eveDbVersion;

    public String getEveManageVersion() {
        return eveManageVersion;
    }

    public void setEveManageVersion(String eveManageVersion) {
        this.eveManageVersion = eveManageVersion;
    }

    public String getEveDbVersion() {
        return eveDbVersion;
    }

    public void setEveDbVersion(String eveDbVersion) {
        this.eveDbVersion = eveDbVersion;
    }
}
