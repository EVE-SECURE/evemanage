package lv.odylab.evemanage.client.rpc;

public class RpcException extends Exception {
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
