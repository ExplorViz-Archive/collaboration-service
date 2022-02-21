package net.explorviz.collaboration.message.sendable.factory;

import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.message.sendable.SelfConnectedMessage;
import net.explorviz.collaboration.model.ControllerModel;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.service.Room;

@ApplicationScoped
public class SelfConnectedMessageFactory {
  public SelfConnectedMessage makeMessage(final UserModel userModel, final Room room) {
    final var message = new SelfConnectedMessage();

    // Add entry for current user.
    final var user = new SelfConnectedMessage.User();
    user.setId(userModel.getId());
    user.setName(userModel.getUserName());
    user.setColor(userModel.getColor());
    message.setSelf(user);

    // Construct list of currently connected users.
    final var otherUserList = new ArrayList<>();
    for (final UserModel otherModel : room.getUserService().getUsers()) {
      if (!otherModel.equals(userModel)) {
        final var otherUser = new SelfConnectedMessage.OtherUser(); // NOPMD
        otherUser.setId(otherModel.getId());
        otherUser.setName(otherModel.getUserName());
        otherUser.setColor(otherModel.getColor());

        // Set user pose.
        otherUser.setPosition(otherModel.getPosition());
        otherUser.setQuaternion(otherModel.getQuaternion());

        // Set user controllers.
        final var otherControllers = new ArrayList<SelfConnectedMessage.Controller>(); // NOPMD
        for (final ControllerModel controllerModel : otherModel.getControllers()) {
          if (controllerModel != null) {
            final var otherController = new SelfConnectedMessage.Controller(); // NOPMD
            otherController.setControllerId(controllerModel.getControllerId());
            otherController.setAssetUrl(controllerModel.getAssetUrl());
            otherController.setPosition(controllerModel.getPosition());
            otherController.setQuaternion(controllerModel.getQuaternion());
            otherController.setIntersection(controllerModel.getIntersection());
            otherControllers.add(otherController);
          }
        }
        otherUser.setControllers(
            otherControllers.toArray((n) -> new SelfConnectedMessage.Controller[n])); // NOPMD

        otherUserList.add(otherUser);
      }
    }
    message.setUsers(otherUserList.toArray(new SelfConnectedMessage.OtherUser[0]));

    return message;
  }
}
