package net.explorviz.collaboration.message.receivable;

import net.explorviz.collaboration.message.ReceivableMessage;
import net.explorviz.collaboration.message.ReceivableMessageHandler;

public class TimestampUpdateMessage extends ReceivableMessage {
  public static final String EVENT = "timestamp_update";

  private long timestamp;

  public TimestampUpdateMessage() {
    super(EVENT);
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(final long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public <R, A> R handleWith(final ReceivableMessageHandler<R, A> handler, final A arg) {
    return handler.handleTimestampUpdateMessage(this, arg);
  }
}
