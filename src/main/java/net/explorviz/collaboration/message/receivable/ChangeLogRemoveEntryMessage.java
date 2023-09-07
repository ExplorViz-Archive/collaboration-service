package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class ChangeLogRemoveEntryMessage extends ReceivableMessage {
  public static final String EVENT = "changelog_remove_entry";

  private String[] entryIds;

  public ChangeLogRemoveEntryMessage() {
    super(EVENT);
  }

  public String[] getEntryIds() {
    return entryIds;
  }

  public void setEntryIds(final String[] entryIds) {
    this.entryIds = entryIds;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleChangeLogRemoveEntryMessage(this, arg);
  }
}
