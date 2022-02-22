package net.explorviz.collaboration.message.respondable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.RespondableMessage;

public class ObjectClosedResponse extends RespondableMessage {
  private static final String EVENT = "object_closed";

  private final boolean success;

  public ObjectClosedResponse(final boolean isSuccess) {
    super(EVENT);
    this.success = isSuccess;
  }

  @JsonProperty("isSuccess")
  public boolean isSuccess() {
    return this.success;
  }
}
