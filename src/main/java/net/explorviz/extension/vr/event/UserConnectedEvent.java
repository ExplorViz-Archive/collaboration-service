package net.explorviz.extension.vr.event;

import net.explorviz.extension.vr.model.UserModel;

public class UserConnectedEvent {

    private UserModel userModel;
    
    public UserConnectedEvent(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

}
