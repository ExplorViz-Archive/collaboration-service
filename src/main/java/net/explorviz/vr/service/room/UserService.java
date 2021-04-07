package net.explorviz.vr.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.event.Event;

import net.explorviz.vr.event.UserConnectedEvent;
import net.explorviz.vr.event.UserDisconnectedEvent;
import net.explorviz.vr.message.receivable.UserControllerConnectMessage;
import net.explorviz.vr.message.receivable.UserPositionsMessage.ControllerPose;
import net.explorviz.vr.message.receivable.UserPositionsMessage.Pose;
import net.explorviz.vr.model.ControllerModel;
import net.explorviz.vr.model.UserModel;
import net.explorviz.vr.model.UserModel.State;
import net.explorviz.vr.service.IdGenerationService;
import net.explorviz.vr.service.Room;

public class UserService {
	private final Room room;
	
    private final IdGenerationService idGenerationService;

    private final ColorAssignmentService colorAssignmentService;

    private final Event<UserConnectedEvent> userConnectedEvent;

    private final Event<UserDisconnectedEvent> userDisconnectedEvent;

    private final GrabService grabService;

    private final Map<String, UserModel> users = new ConcurrentHashMap<>();

    public UserService(Room room, IdGenerationService idGenerationService, ColorAssignmentService colorAssignmentService,
            Event<UserConnectedEvent> userConnectedEvent, Event<UserDisconnectedEvent> userDisconnectedEvent,
            GrabService grabService) {
    	this.room = room;
        this.idGenerationService = idGenerationService;
        this.colorAssignmentService = colorAssignmentService;
        this.userConnectedEvent = userConnectedEvent;
        this.userDisconnectedEvent = userDisconnectedEvent;
        this.grabService = grabService;
    }

    public void updateUserPose(UserModel user, Pose pose) {
    	if (user != null && pose != null) {
	        user.setPosition(pose.getPosition());
	        user.setQuaternion(pose.getQuaternion());
    	}
    }

    public void updateControllerPose(ControllerModel controller, ControllerPose pose) {
        if (controller != null && pose != null) {
        	controller.setPosition(pose.getPosition());
        	controller.setQuaternion(pose.getQuaternion());
        	controller.setIntersection(pose.getIntersection());
        }
    }
    
    public void connectController(UserModel user, UserControllerConnectMessage.Controller controller) {
    	ControllerModel controllerModel = makeControllerModel(controller.getControllerId(), controller.getAssetUrl());
    	controllerModel.setPosition(controller.getPosition());
    	controllerModel.setQuaternion(controller.getQuaternion());
    	controllerModel.setIntersection(controller.getIntersection());
    	user.addController(controllerModel);
    }
    
    public void disconnectController(UserModel user, int controllerId) {
      	user.removeController(controllerId);
    }
    
    public void updateSpectating(UserModel user, boolean isSpectating) {
    	user.setState(isSpectating ? State.SPECTATING : State.CONNECTED);
    }

    public void updateHighlighting(UserModel user, String appId, String entityId, String entityType,
            boolean isHighlighted) {
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

    public UserModel makeUserModel(String userName) {
        final var userId = idGenerationService.nextId();
        final var color = colorAssignmentService.assignColor();
        return new UserModel(userId, userName, color);
    }

    public ControllerModel makeControllerModel(int controllerId, String assetUrl) {
        final var id = idGenerationService.nextId();
        return new ControllerModel(id, controllerId, assetUrl);
    }

    public void addUser(UserModel user) {
        users.put(user.getId(), user);
        userConnectedEvent.fireAsync(new UserConnectedEvent(user, room));
    }

    public void removeUser(UserModel user) {
    	colorAssignmentService.unassignColor(user.getColor());
        grabService.releaseAllGrabbedObjectsByUser(user.getId());
        
        if (users.containsKey(user.getId())) {
        	users.remove(user.getId());
        	userDisconnectedEvent.fireAsync(new UserDisconnectedEvent(user, room));
        }
    }
    
    public Collection<UserModel> getUsers() {
        return this.users.values();
    }
}
