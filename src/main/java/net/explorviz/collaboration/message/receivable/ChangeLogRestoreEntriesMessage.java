package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ChangeLogRestoreEntriesMessage extends ReceivableMessage {
  public static final String EVENT = "changelog_restore_entries";

  public ChangeLogRestoreEntriesMessage() {
    super(EVENT);
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleChangeLogRestoreEntriesMessage(this, arg);
  }
}
