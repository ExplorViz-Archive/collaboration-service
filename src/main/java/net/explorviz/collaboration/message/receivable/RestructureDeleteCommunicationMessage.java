package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureDeleteCommunicationMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_delete_communication";

  private boolean undo;
  private String commId;

  public RestructureDeleteCommunicationMessage() {
    super(EVENT);
  }

  public boolean isUndo() {
    return this.undo;
  }

  public void setUndo(final boolean undo) {
    this.undo = undo;
  }

  public String getCommId() {
    return this.commId;
  }

  public void setCommId(final String commId) {
    this.commId = commId;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureDeleteCommunicationMessage(this, arg);
  }
}
