package net.explorviz.extension.vr.service;

import net.explorviz.extension.vr.service.room.ApplicationService;
import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;
import net.explorviz.extension.vr.service.room.DetachedMenuService;
import net.explorviz.extension.vr.service.room.GrabService;
import net.explorviz.extension.vr.service.room.LandscapeService;
import net.explorviz.extension.vr.service.room.SessionRegistry;
import net.explorviz.extension.vr.service.room.UserService;

/**
 * A room is modelled by a collection of services that each manage one particular aspect of the room.
 */
public abstract class Room {
	private final String roomId;

	public Room(String roomId) {
		this.roomId = roomId;
	}
	
	public String getRoomId() {
		return roomId;
	}

	
	public abstract GrabService getGrabService();
	public abstract LandscapeService getLandscapeService();
	public abstract ApplicationService getApplicationService();
	public abstract DetachedMenuService getDetachedMenuService();

	public abstract UserService getUserService();	
	public abstract SessionRegistry getSessionRegistry();
	public abstract BroadcastService getBroadcastService();
	
	public abstract ColorAssignmentService getColorAssignmentService();
}
