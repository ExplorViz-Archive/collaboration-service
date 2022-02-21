package net.explorviz.collaboration.message.sendable;

import net.explorviz.collaboration.message.BroadcastableMessage;

public class UserDisconnectedMessage extends BroadcastableMessage {
  public static final String EVENT = "user_disconnect";

  private String id;

  public UserDisconnectedMessage() {
    super(EVENT);
  }

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }
}
