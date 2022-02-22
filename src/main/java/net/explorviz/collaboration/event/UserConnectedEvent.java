package net.explorviz.collaboration.event;

import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

public class UserConnectedEvent {

  private final UserModel userModel;

  private final Room room;

  public UserConnectedEvent(final UserModel userModel, final Room room) {
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
