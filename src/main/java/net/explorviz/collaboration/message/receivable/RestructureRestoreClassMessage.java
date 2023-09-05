package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureRestoreClassMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_restore_class";

    private String appId;
    private String clazzId;
    private boolean undoCutOperation;

    public RestructureRestoreClassMessage() {
        super(EVENT);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(final String appId) {
        this.appId = appId;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(final String clazzId) {
        this.clazzId = clazzId;
    }

    public boolean isUndoCutOperation() {
        return undoCutOperation;
    }

    public void setUndoCutOperation(final boolean undoCutOperation) {
        this.undoCutOperation = undoCutOperation;
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureRestoreClassMessage(this, arg);
    }
}