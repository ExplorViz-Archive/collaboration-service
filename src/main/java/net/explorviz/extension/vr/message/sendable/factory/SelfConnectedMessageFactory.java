package net.explorviz.extension.vr.message.sendable.factory;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.explorviz.extension.vr.message.sendable.SelfConnectedMessage;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.service.UserService;

@Singleton
public class SelfConnectedMessageFactory {
    @Inject
    UserService userService;
    
    public SelfConnectedMessage makeMessage(UserModel userModel) {
        final var message = new SelfConnectedMessage();
        
        // Construct list of currently connected users.
        final var userList = new ArrayList<>();
        for (UserModel otherModel : userService.getUserModels()) {
            final var otherUser = new SelfConnectedMessage.ConnectedUser();
            otherUser.setId(otherModel.getId());
            otherUser.setName(otherModel.getUserName());
            otherUser.setColor(otherModel.getColor());
            
            final var otherControllers = new SelfConnectedMessage.Controllers();
            otherControllers.setController1(otherModel.getController1().getName());
            otherControllers.setController1(otherModel.getController1().getName());
            otherUser.setControllers(otherControllers);
            
            userList.add(otherUser);
        }
        message.setUsers(userList.toArray(new SelfConnectedMessage.ConnectedUser[userList.size()]));
        
        return message;
    }
}
