package lv.odylab.evemanage.client.event.footer;

import com.google.gwt.event.shared.GwtEvent;
import lv.odylab.evemanage.client.rpc.action.footer.GetVersionsActionResponse;

public class GotVersionsEvent extends GwtEvent<GotVersionsEventHandler> {
    public static final Type<GotVersionsEventHandler> TYPE = new Type<GotVersionsEventHandler>();

    private String eveManageVersion;
    private String eveDbVersion;

    public GotVersionsEvent(GetVersionsActionResponse response) {
        this.eveManageVersion = response.getEveManageVersion();
        this.eveDbVersion = response.getEveDbVersion();
    }

    public String getEveManageVersion() {
        return eveManageVersion;
    }

    public String getEveDbVersion() {
        return eveDbVersion;
    }

    @Override
    public Type<GotVersionsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(GotVersionsEventHandler handler) {
        handler.onGotVersions(this);
    }
}
