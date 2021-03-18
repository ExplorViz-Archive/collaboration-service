package net.explorviz.extension.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.service.Room;

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

                final var otherControllers = new SelfConnectedMessage.Controllers();
                otherControllers.setController1(otherModel.getController1().getName());
                otherControllers.setController2(otherModel.getController2().getName());
                otherUser.setControllers(otherControllers);

                otherUserList.add(otherUser);
            }
        }
        message.setUsers(otherUserList.toArray(new SelfConnectedMessage.OtherUser[otherUserList.size()]));

        return message;
    }
}
