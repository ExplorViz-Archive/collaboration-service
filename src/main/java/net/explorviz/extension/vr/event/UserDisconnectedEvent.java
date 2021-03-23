package net.explorviz.extension.vr.event;

import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.service.Room;

public class UserDisconnectedEvent {

    private UserModel userModel;

    private Room room;

    public UserDisconnectedEvent(UserModel userModel, Room room) {
        this.userModel = userModel;
        this.room = room;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public Room getRoom() {
        return room;
    }
}
