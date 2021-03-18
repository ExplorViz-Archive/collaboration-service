package net.explorviz.extension.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.event.Event;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.message.receivable.UserControllersMessage.Controllers;
import net.explorviz.extension.vr.model.ControllerModel;
import net.explorviz.extension.vr.model.UserModel;
import net.explorviz.extension.vr.model.UserModel.State;
import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.Room;

public class UserService {
    private final IdGenerationService idGenerationService;

    private final ColorAssignmentService colorAssignmentService;

    private final Event<UserConnectedEvent> userConnectedEvent;

    private final Event<UserDisconnectedEvent> userDisconnectedEvent;

    private final GrabService grabService;

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();

    public UserService(IdGenerationService idGenerationService, ColorAssignmentService colorAssignmentService,
            Event<UserConnectedEvent> userConnectedEvent, Event<UserDisconnectedEvent> userDisconnectedEvent,
            GrabService grabService) {
        super();
        this.idGenerationService = idGenerationService;
        this.colorAssignmentService = colorAssignmentService;
        this.userConnectedEvent = userConnectedEvent;
        this.userDisconnectedEvent = userDisconnectedEvent;
        this.grabService = grabService;
    }

    public void updateUserPosition() {
        // TODO implement
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

    public void addUser(UserModel userModel, Room room) {
        users.put(userModel.getId(), userModel);
        userConnectedEvent.fireAsync(new UserConnectedEvent(userModel, room));
    }

    public void removeUser(String userId, Room room) {
        UserModel userModel = users.remove(userId);
        if (userModel != null) {
            colorAssignmentService.unassignColor(userModel.getColor());
            userDisconnectedEvent.fireAsync(new UserDisconnectedEvent(userModel, room));
            grabService.releaseAllGrabbedObjectsByUser(userId);
        }
    }

    public Collection<UserModel> getUsers() {
        return this.users.values();
    }


}
