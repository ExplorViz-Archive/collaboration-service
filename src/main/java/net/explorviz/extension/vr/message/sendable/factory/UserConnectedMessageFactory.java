package net.explorviz.extension.vr.message.sendable.factory;

import javax.inject.Singleton;

import net.explorviz.extension.vr.message.sendable.UserConnectedMessage;
import net.explorviz.extension.vr.model.UserModel;

@Singleton
public class UserConnectedMessageFactory {
    public UserConnectedMessage makeMessage(UserModel userModel) {
        final var message = new UserConnectedMessage();
        message.setId(userModel.getId());
        message.setName(userModel.getUserName());
        message.setColor(userModel.getColor());
        return message;
    }
}
