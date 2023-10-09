package net.explorviz.collaboration.service.room;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.event.Event;
import net.explorviz.collaboration.event.UserConnectedEvent;
import net.explorviz.collaboration.event.UserDisconnectedEvent;
import net.explorviz.collaboration.message.receivable.UserControllerConnectMessage;
import net.explorviz.collaboration.message.receivable.UserPositionsMessage.ControllerPose;
import net.explorviz.collaboration.message.receivable.UserPositionsMessage.Pose;
import net.explorviz.collaboration.model.ControllerModel;
import net.explorviz.collaboration.model.HighlightingModel;
import net.explorviz.collaboration.model.UserModel;
import net.explorviz.collaboration.model.UserModel.State;
import net.explorviz.collaboration.service.IdGenerationService;
import net.explorviz.collaboration.service.Room;

public class UserService { // NOPMD
  private final Room room;

  private final IdGenerationService idGenerationService;

  private final ColorAssignmentService colorAssignmentService;

  private final Event<UserConnectedEvent> userConnectedEvent;

  private final Event<UserDisconnectedEvent> userDisconnectedEvent;

  private final GrabService grabService;

  private final Map<String, UserModel> users = new ConcurrentHashMap<>();

  public UserService(final Room room, final IdGenerationService idGenerationService,
      final ColorAssignmentService colorAssignmentService,
      final Event<UserConnectedEvent> userConnectedEvent,
      final Event<UserDisconnectedEvent> userDisconnectedEvent, final GrabService grabService) {
    this.room = room;
    this.idGenerationService = idGenerationService;
    this.colorAssignmentService = colorAssignmentService;
    this.userConnectedEvent = userConnectedEvent;
    this.userDisconnectedEvent = userDisconnectedEvent;
    this.grabService = grabService;
  }

  public void updateUserPose(final UserModel user, final Pose pose) {
    if (user != null && pose != null) {
      user.setPosition(pose.getPosition());
      user.setQuaternion(pose.getQuaternion());
    }
  }

  public void updateControllerPose(final ControllerModel controller, final ControllerPose pose) {
    if (controller != null && pose != null) {
      controller.setPosition(pose.getPosition());
      controller.setQuaternion(pose.getQuaternion());
      controller.setIntersection(pose.getIntersection());
    }
  }

  public void connectController(final UserModel user,
      final UserControllerConnectMessage.Controller controller) {
    final ControllerModel controllerModel =
        this.makeControllerModel(controller.getControllerId(), controller.getAssetUrl());
    controllerModel.setPosition(controller.getPosition());
    controllerModel.setQuaternion(controller.getQuaternion());
    controllerModel.setIntersection(controller.getIntersection());
    user.addController(controllerModel);
  }

  public void disconnectController(final UserModel user, final int controllerId) {
    user.removeController(controllerId);
  }

  public void updateSpectating(final UserModel user, final boolean isSpectating) {
    user.setState(isSpectating ? State.SPECTATING : State.CONNECTED);
  }

  public void updateHighlighting(final UserModel user, final String appId, final String entityId,
      final String entityType, final boolean isHighlighted, final boolean isMultiSelected) {

    if (!isHighlighted && !isMultiSelected) { // NOPMD
      //user.removeHighlightedEntity(entityId); // not necessary because own user is in otherUser
      for (final UserModel otherUser : this.users.values()) {
        otherUser.removeHighlightedEntity(entityId);
      }
    } else if (!isHighlighted && isMultiSelected) {

      for (final HighlightingModel highlightingModel : user.getHighlightedEntities()) {
        for (final UserModel otherUser : this.users.values()) {
          if (!otherUser.getId().equals(user.getId())) {
            // we are not allowed to modify the object we are iterating through
            otherUser.removeHighlightedEntity(highlightingModel.getEntityId());
          }
        }
      }
      user.removeAllHighlightedEntities();
    } else {
      // Overwrite highlighting of other users (if they highlighted same entity)
      user.setHighlightedEntity(appId, entityType, entityId);
    }
  }


  public UserModel makeUserModel(final String userName) {
    final var userId = this.idGenerationService.nextId();
    final var color = this.colorAssignmentService.assignColor();
    return new UserModel(userId, userName, color);
  }

  public ControllerModel makeControllerModel(final int controllerId, final String assetUrl) {
    final var id = this.idGenerationService.nextId();
    return new ControllerModel(id, controllerId, assetUrl);
  }

  public void addUser(final UserModel user) {
    this.users.put(user.getId(), user);
    this.userConnectedEvent.fireAsync(new UserConnectedEvent(user, this.room));
  }

  public void removeUser(final UserModel user) {
    this.colorAssignmentService.unassignColor(user.getColor());
    this.grabService.releaseAllGrabbedObjectsByUser(user.getId());

    if (this.users.containsKey(user.getId())) {
      this.users.remove(user.getId());
      this.userDisconnectedEvent.fireAsync(new UserDisconnectedEvent(user, this.room));
    }
  }

  public void resetAllHighlights(final Room room) {
    for (final UserModel otherUser : this.users.values()) {
      otherUser.removeAllHighlightedEntities();
    }
  }

  public Collection<UserModel> getUsers() {
    return this.users.values();
  }
}
