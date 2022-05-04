package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.BroadcastableMessage;

public class TimestampUpdateTimerMessage extends BroadcastableMessage {
  public static final String EVENT = "timestamp_update_timer";

  private long timestamp;

  public TimestampUpdateTimerMessage() {
    super(EVENT);
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(final long timestamp) {
    this.timestamp = timestamp;
  }
}
