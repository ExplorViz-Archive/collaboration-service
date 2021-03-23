package net.explorviz.extension.vr.service;

import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;
import net.explorviz.extension.vr.service.room.EntityService;
import net.explorviz.extension.vr.service.room.SessionRegistry;
import net.explorviz.extension.vr.service.room.UserService;

public class Room {

    private final String roomId;

    private final UserService userService;

    private final EntityService entityService;

    private final BroadcastService broadcastService;

    private final ColorAssignmentService colorAssignmentService;

    private final SessionRegistry sessionRegistry;

    public Room(String roomId, UserService userService, EntityService entityService, BroadcastService broadcastService,
            ColorAssignmentService colorAssignmentService, SessionRegistry sessionRegistry) {
        super();
        this.roomId = roomId;
        this.userService = userService;
        this.entityService = entityService;
        this.broadcastService = broadcastService;
        this.colorAssignmentService = colorAssignmentService;
        this.sessionRegistry = sessionRegistry;
    }

    public UserService getUserService() {
        return userService;
    }

    public EntityService getEntityService() {
        return entityService;
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

    public String getRoomId() {
        return roomId;
    }
}
