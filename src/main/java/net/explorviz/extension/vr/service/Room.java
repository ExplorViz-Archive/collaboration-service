package net.explorviz.extension.vr.service;

import net.explorviz.extension.vr.service.room.ApplicationService;
import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;
import net.explorviz.extension.vr.service.room.DetachedMenuService;
import net.explorviz.extension.vr.service.room.GrabService;
import net.explorviz.extension.vr.service.room.LandscapeService;
import net.explorviz.extension.vr.service.room.SessionRegistry;
import net.explorviz.extension.vr.service.room.UserService;

public class Room {

	private final String roomId;

	private final UserService userService;

	private final GrabService grabService;

	private final LandscapeService landscapeService;

	private final ApplicationService applicationService;

	private final DetachedMenuService detachedMenuService;

	private final BroadcastService broadcastService;

	private final ColorAssignmentService colorAssignmentService;

	private final SessionRegistry sessionRegistry;

	public Room(String roomId, UserService userService, GrabService grabService, LandscapeService landscapeService,
			ApplicationService applicationService, DetachedMenuService detachedMenuService,
			BroadcastService broadcastService, ColorAssignmentService colorAssignmentService,
			SessionRegistry sessionRegistry) {
		super();
		this.roomId = roomId;
		this.userService = userService;
		this.grabService = grabService;
		this.landscapeService = landscapeService;
		this.applicationService = applicationService;
		this.detachedMenuService = detachedMenuService;
		this.broadcastService = broadcastService;
		this.colorAssignmentService = colorAssignmentService;
		this.sessionRegistry = sessionRegistry;
	}

	public String getRoomId() {
		return roomId;
	}

	public UserService getUserService() {
		return userService;
	}

	public GrabService getGrabService() {
		return grabService;
	}

	public LandscapeService getLandscapeService() {
		return landscapeService;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public DetachedMenuService getDetachedMenuService() {
		return detachedMenuService;
	}

	public BroadcastService getBroadcastService() {
		return broadcastService;
	}

	public ColorAssignmentService getColorAssignmentService() {
		return colorAssignmentService;
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}
}
