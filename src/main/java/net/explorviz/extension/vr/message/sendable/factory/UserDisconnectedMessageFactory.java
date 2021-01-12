package net.explorviz.extension.vr.message.sendable.factory;

import net.explorviz.extension.vr.message.sendable.UserDisconnectedMessage;
import net.explorviz.extension.vr.model.UserModel;

public class UserDisconnectedMessageFactory {
    public UserDisconnectedMessage makeMessage(UserModel userModel) {
        final var message = new UserDisconnectedMessage();
        message.setId(userModel.getId());
        return message;
    }
}
