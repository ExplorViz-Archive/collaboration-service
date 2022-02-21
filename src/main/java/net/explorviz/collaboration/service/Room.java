package net.explorviz.collaboration.service;

import net.explorviz.collaboration.service.room.ApplicationService;
import net.explorviz.collaboration.service.room.BroadcastService;
import net.explorviz.collaboration.service.room.ColorAssignmentService;
import net.explorviz.collaboration.service.room.DetachedMenuService;
import net.explorviz.collaboration.service.room.GrabService;
import net.explorviz.collaboration.service.room.LandscapeService;
import net.explorviz.collaboration.service.room.UserService;

/**
 * A room is modeled by a collection of services that each manage one particular aspect of the room.
 */
public abstract class Room {
  private final String roomId;
  private final String roomName;

  public Room(final String roomId, final String roomName) {
    this.roomId = roomId;
    this.roomName = roomName;
  }

  public String getRoomId() {
    return this.roomId;
  }

  public String getName() {
    return this.roomName;
  }

  public abstract GrabService getGrabService();

  public abstract LandscapeService getLandscapeService();

  public abstract ApplicationService getApplicationService();

  public abstract DetachedMenuService getDetachedMenuService();

  public abstract UserService getUserService();

  public abstract BroadcastService getBroadcastService();

  public abstract ColorAssignmentService getColorAssignmentService();
}
