package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureRenameOperationMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_rename_operation";

    private String clazzId;
    private String originalName;
    private String newName;

    public RestructureRenameOperationMessage() {
        super(EVENT);
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(final String clazzId) {
        this.clazzId = clazzId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(final String originalName) {
        this.originalName = originalName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(final String newName) {
        this.newName = newName;
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureRenameOperationMessage(this, arg);
  }
}