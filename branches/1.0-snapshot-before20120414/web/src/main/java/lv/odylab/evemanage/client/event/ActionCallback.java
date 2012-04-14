package lv.odylab.evemanage.client.event;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ActionCallback<T> implements AsyncCallback<T> {
    private Long executionStart;

    protected ActionCallback() {
        this.executionStart = System.currentTimeMillis();
    }

    public Long getExecutionDuration() {
        return System.currentTimeMillis() - executionStart;
    }
}
