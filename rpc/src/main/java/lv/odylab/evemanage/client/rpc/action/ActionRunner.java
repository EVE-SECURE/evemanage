package lv.odylab.evemanage.client.rpc.action;

public interface ActionRunner<T extends Action<E>, E extends Response> {

    E execute(T action) throws Exception;

}
