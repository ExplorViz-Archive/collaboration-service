package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.RespondableMessage;

public class MenuDetachedResponse extends RespondableMessage {
  private static final String EVENT = "menu_detached";

  private final String objectId;

  public MenuDetachedResponse(final String objectId) {
    super(EVENT);
    this.objectId = objectId;
  }

  public String getObjectId() {
    return this.objectId;
  }

}
