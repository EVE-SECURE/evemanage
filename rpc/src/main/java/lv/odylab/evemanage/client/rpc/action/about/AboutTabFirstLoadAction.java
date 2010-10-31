package lv.odylab.evemanage.client.rpc.action.about;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(AboutTabFirstLoadActionRunner.class)
public class AboutTabFirstLoadAction implements Action<AboutTabFirstLoadActionResponse> {
    private static final long serialVersionUID = -5176957936819242367L;
}