package net.explorviz.collaboration.message.receivable;

import java.util.Arrays;
import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;



public class ChangeLogRemoveEntryMessage extends ReceivableMessage {
  public static final String EVENT = "changelog_remove_entry";

  private String[] entryIds;

  public ChangeLogRemoveEntryMessage() {
    super(EVENT);
  }

  public String[] getEntryIds() {
    return Arrays.copyOf(entryIds, entryIds.length);
  }

  public void setEntryIds(final String[] entryIds) {
    this.entryIds = Arrays.copyOf(entryIds, entryIds.length);
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleChangeLogRemoveEntryMessage(this, arg);
  }
}
