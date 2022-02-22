package net.explorviz.collaboration.message.respondable;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.explorviz.collaboration.message.RespondableMessage;

public class ObjectGrabbedResponse extends RespondableMessage {
  private static final String EVENT = "object_grabbed";

  private final boolean success;

  public ObjectGrabbedResponse(final boolean isSuccess) {
    super(EVENT);
    this.success = isSuccess;
  }

  @JsonProperty("isSuccess")
  public boolean isSuccess() {
    return this.success;
  }
}
