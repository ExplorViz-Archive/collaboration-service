package net.explorviz.vr.message.sendable.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.vr.message.sendable.UserConnectedMessage;
import net.explorviz.vr.model.UserModel;

@ApplicationScoped
public class UserConnectedMessageFactory {
    public UserConnectedMessage makeMessage(UserModel userModel) {
        final var message = new UserConnectedMessage();
        message.setId(userModel.getId());
        message.setName(userModel.getUserName());
        message.setColor(userModel.getColor());
        message.setPosition(userModel.getPosition());
        message.setQuaternion(userModel.getQuaternion());
        return message;
    }
}
