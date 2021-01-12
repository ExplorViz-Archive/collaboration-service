package net.explorviz.extension.vr.event;

import net.explorviz.extension.vr.model.UserModel;

public class UserDisconnectedEvent {

    private UserModel userModel;

    public UserDisconnectedEvent(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

}
