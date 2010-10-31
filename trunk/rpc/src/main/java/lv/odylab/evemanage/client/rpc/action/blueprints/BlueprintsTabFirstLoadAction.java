package lv.odylab.evemanage.client.rpc.action.blueprints;

import lv.odylab.evemanage.client.rpc.action.Action;
import lv.odylab.evemanage.client.rpc.action.RunnedBy;

@RunnedBy(BlueprintsTabFirstLoadActionRunner.class)
public class BlueprintsTabFirstLoadAction implements Action<BlueprintsTabFirstLoadActionResponse> {
    private static final long serialVersionUID = -8668378153370406217L;
}