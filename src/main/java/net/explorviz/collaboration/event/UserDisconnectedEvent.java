package net.explorviz.collaboration.event;

import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

public class UserDisconnectedEvent {

  private final UserModel userModel;

  private final Room room;

  public UserDisconnectedEvent(final UserModel userModel, final Room room) {
    this.userModel = userModel;
    this.room = room;
  }

  public UserModel getUserModel() {
    return this.userModel;
  }

  public Room getRoom() {
    return this.room;
  }
}
