package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureRestorePackageMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_restore_pckg";

    private String pckgId;
    private boolean undoCutOperation;

    public RestructureRestorePackageMessage() {
        super(EVENT);
    }

    public String getPckgId() {
        return pckgId;
    }

    public void setPckgId(final String pckgId) {
        this.pckgId = pckgId;
    }

    public boolean isUndoCutOperation() {
        return undoCutOperation;
    }

    public void setUndoCutOperation(final boolean undoCutOperation) {
        this.undoCutOperation = undoCutOperation;
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureRestorePackageMessage(this, arg);
    }
}