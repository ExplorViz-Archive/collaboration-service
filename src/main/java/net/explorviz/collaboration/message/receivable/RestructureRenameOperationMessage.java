package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class RestructureRenameOperationMessage extends ReceivableMessage {
  public static final String EVENT = "restructure_rename_operation";

  private String commId;
  private String newName;
  private boolean undo;

  public RestructureRenameOperationMessage() {
    super(EVENT);
  }

  public String getCommId() {
    return commId;
  }

  public void setCommId(final String commId) {
    this.commId = commId;
  }

  public String getNewName() {
    return newName;
  }

  public void setNewName(final String newName) {
    this.newName = newName;
  }

  public boolean isUndo() {
    return this.undo;
  }

  public void setUndo(final boolean undo) {
    this.undo = undo;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleRestructureRenameOperationMessage(this, arg);
  }
}
