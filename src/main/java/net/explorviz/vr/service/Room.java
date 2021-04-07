package net.explorviz.vr.service;

import net.explorviz.vr.service.room.ApplicationService;
import net.explorviz.vr.service.room.BroadcastService;
import net.explorviz.vr.service.room.ColorAssignmentService;
import net.explorviz.vr.service.room.DetachedMenuService;
import net.explorviz.vr.service.room.GrabService;
import net.explorviz.vr.service.room.LandscapeService;
import net.explorviz.vr.service.room.UserService;

/**
 * A room is modeled by a collection of services that each manage one particular
 * aspect of the room.
 */
public abstract class Room {
	private final String roomId;
	private final String roomName;

	public Room(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getName() {
		return roomName;
	}

	public abstract GrabService getGrabService();

	public abstract LandscapeService getLandscapeService();

	public abstract ApplicationService getApplicationService();

	public abstract DetachedMenuService getDetachedMenuService();

	public abstract UserService getUserService();

	public abstract BroadcastService getBroadcastService();

	public abstract ColorAssignmentService getColorAssignmentService();
}
