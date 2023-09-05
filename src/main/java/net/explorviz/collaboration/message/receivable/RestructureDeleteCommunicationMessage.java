package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureDeleteCommunicationMessage extends ReceivableMessage {
    public static final String EVENT = "restructure_delete_communication";

    private boolean undo;

    public RestructureDeleteCommunicationMessage() {
        super(EVENT);
    }

    public boolean getUndo() {
        return this.undo;
    }

    public void setUndo(final boolean undo) {
        this.undo = undo; 
    }

    @Override
    public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
        return handler.handleRestructureDeleteCommunicationMessage(this, arg);
  }
}