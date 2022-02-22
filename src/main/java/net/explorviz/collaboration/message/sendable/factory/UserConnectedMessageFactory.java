package net.explorviz.collaboration.message.sendable.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.message.sendable.UserConnectedMessage;
import net.explorviz.collaboration.model.UserModel;

@ApplicationScoped
public class UserConnectedMessageFactory {
  public UserConnectedMessage makeMessage(final UserModel userModel) {
    final var message = new UserConnectedMessage();
    message.setId(userModel.getId());
    message.setName(userModel.getUserName());
    message.setColor(userModel.getColor());
    message.setPosition(userModel.getPosition());
    message.setQuaternion(userModel.getQuaternion());
    return message;
  }
}
