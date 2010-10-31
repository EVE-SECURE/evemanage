package lv.odylab.evemanage.client.rpc;

public class RpcException extends Exception {
    private static final long serialVersionUID = -3814476997699532471L;

    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}
