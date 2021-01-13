package net.explorviz.extension.vr.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.model.ControllerModel;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.model.UserModel.State;

@ApplicationScoped
public class UserService {

    @Inject
    IdGenerationService idGenerationService;

    @Inject
    ColorAssignmentService colorAssignmentService;

    @Inject
    Event<UserConnectedEvent> userConnectedEvent;

    @Inject
    Event<UserDisconnectedEvent> userDisconnectedEvent;
    
    @Inject
    EntityService entityService;
    

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();

    public void updateUserPosition() {

    }

    public void updateUserControllers(String userId, Controllers connect, Controllers disconnect) {
        UserModel user = users.get(userId);
        if (user != null) {
            if (connect != null) {
                if (connect.getController1() != null) {
                    user.getController1().setName(connect.getController1());
                }
                if (connect.getController2() != null) {
                    user.getController2().setName(connect.getController2());
                }
            }
            if (disconnect != null) {
                if (disconnect.getController1() != null) {
                    user.getController1().setName(null);
                }
                if (disconnect.getController2() != null) {
                    user.getController2().setName(null);
                }
            }
        }
    }

    public void updateSpectating(String userId, boolean isSpectating) {
        UserModel user = users.get(userId);
        if (user != null) {
            user.setState(isSpectating ? State.SPECTATING : State.CONNECTED);

        }
    }

    public void updateHighlighting(String userId, String appId, String entityId, String entityType,
            boolean isHighlighted) {
        UserModel user = users.get(userId);
        if (!isHighlighted) {
            user.setHighlighted(false);
            return;
        }

        // Overwrite highlighting of other users (if they highlighted same entity)
        for (final UserModel otherUser : this.users.values()) {
            if (otherUser.hasHighlightedEntity() && otherUser.getHighlightedEntity().getHighlightedApp().equals(appId)
                    && otherUser.getHighlightedEntity().getHighlightedEntity().equals(entityId)) {
                otherUser.setHighlighted(false);
            }
        }
        user.setHighlightedEntity(isHighlighted, appId, entityType, entityId);
    }

    public UserModel makeUserModel() {
        final var userId = idGenerationService.nextId();
        final var userName = "user" + userId;
        final var controller1 = makeControllerModel();
        final var controller2 = makeControllerModel();
        final var color = colorAssignmentService.assignColor();
        return new UserModel(userId, userName, controller1, controller2, color);
    }

    private ControllerModel makeControllerModel() {
        final var controllerId = idGenerationService.nextId();
        return new ControllerModel(controllerId);
    }

    public void addUser(UserModel userModel) {
        users.put(userModel.getId(), userModel);
        userConnectedEvent.fireAsync(new UserConnectedEvent(userModel));
    }

    public void removeUser(String userId) {
        UserModel userModel = users.remove(userId);
        if (userModel != null) {
            colorAssignmentService.unassignColor(userModel.getColor());
            userDisconnectedEvent.fireAsync(new UserDisconnectedEvent(userModel));
            releaseAllGrabbedObjects(userModel);
        }
    }
    

    public Collection<UserModel> getUsers() {
        return this.users.values();
    }
    
    private void releaseAllGrabbedObjects(UserModel userModel) {
        for (String objectId : userModel.getGrabbedObjects()) {
            entityService.releaseObject(userModel.getId(), objectId);
        }
    }

    public void userGrabbedObject(String userId, String objectId) {
        UserModel userModel = users.get(userId);
        if (userModel != null) {
            userModel.addGrabbedObject(objectId);
        }
    }

    public void userReleasedObject(String userId, String objectId) {
        UserModel userModel = users.get(userId);
        if (userModel != null) {
            userModel.removeGrabbedObject(objectId);
        }
    }

}
