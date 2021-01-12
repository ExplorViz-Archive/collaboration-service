package net.explorviz.extension.vr.message.sendable.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.message.sendable.UserDisconnectedMessage;
import net.explorviz.extension.vr.model.UserModel;

@ApplicationScoped
public class UserDisconnectedMessageFactory {
    public UserDisconnectedMessage makeMessage(UserModel userModel) {
        final var message = new UserDisconnectedMessage();
        message.setId(userModel.getId());
        return message;
    }
}
