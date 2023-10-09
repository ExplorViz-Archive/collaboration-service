package net.explorviz.collaboration.message.sendable.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.message.sendable.UserDisconnectedMessage;
import net.explorviz.collaboration.model.HighlightingModel;
import net.explorviz.collaboration.model.UserModel;

@ApplicationScoped
public class UserDisconnectedMessageFactory {
  public UserDisconnectedMessage makeMessage(final UserModel userModel) {
    final var message = new UserDisconnectedMessage();
    message.setId(userModel.getId());
    message.setHighlightedComponents(userModel.getHighlightedEntities()
        .toArray(n -> new HighlightingModel[n]));
    return message;
  }
}
