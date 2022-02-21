package net.explorviz.collaboration.message.respondable;

import net.explorviz.collaboration.message.RespondableMessage;

public class ObjectClosedResponse extends RespondableMessage {
  private static final String EVENT = "object_closed";

  private final boolean success;

  public ObjectClosedResponse(final boolean isSuccess) {
    super(EVENT);
    this.success = isSuccess;
  }

  public boolean isSuccess() {
    return this.success;
  }
}
