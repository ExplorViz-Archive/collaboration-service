package net.explorviz.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.vr.model.ControllerModel;
import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.service.Room;

@ApplicationScoped
public class SelfConnectedMessageFactory {
	public SelfConnectedMessage makeMessage(UserModel userModel, Room room) {
		final var message = new SelfConnectedMessage();

		// Add entry for current user.
		final var user = new SelfConnectedMessage.User();
		user.setId(userModel.getId());
		user.setName(userModel.getUserName());
		user.setColor(userModel.getColor());
		message.setSelf(user);

		// Construct list of currently connected users.
		final var otherUserList = new ArrayList<>();
		for (UserModel otherModel : room.getUserService().getUsers()) {
			if (otherModel != userModel) {
				final var otherUser = new SelfConnectedMessage.OtherUser();
				otherUser.setId(otherModel.getId());
				otherUser.setName(otherModel.getUserName());
				otherUser.setColor(otherModel.getColor());

				// Set user pose.
				otherUser.setPosition(otherModel.getPosition());
				otherUser.setQuaternion(otherModel.getQuaternion());

				// Set user controllers.
				final var otherControllers = new ArrayList<SelfConnectedMessage.Controller>();
				for (ControllerModel controllerModel : otherModel.getControllers()) {
					if (controllerModel != null) {
						final var otherController = new SelfConnectedMessage.Controller();
						otherController.setControllerId(controllerModel.getControllerId());
						otherController.setAssetUrl(controllerModel.getAssetUrl());
						otherController.setPosition(controllerModel.getPosition());
						otherController.setQuaternion(controllerModel.getQuaternion());
						otherController.setIntersection(controllerModel.getIntersection());
						otherControllers.add(otherController);
					}
				}
				otherUser.setControllers(otherControllers.toArray((n) -> new SelfConnectedMessage.Controller[n]));

				otherUserList.add(otherUser);
			}
		}
		message.setUsers(otherUserList.toArray(new SelfConnectedMessage.OtherUser[otherUserList.size()]));

		return message;
	}
}
