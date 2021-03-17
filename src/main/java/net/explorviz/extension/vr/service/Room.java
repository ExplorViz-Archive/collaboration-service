package net.explorviz.extension.vr.service;

import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;
import net.explorviz.extension.vr.service.room.EntityService;
import net.explorviz.extension.vr.service.room.UserService;

public class Room {

    private final UserService userService;
   
    private final EntityService entityService;
    
    private final BroadcastService broadcastService;
    
    private final ColorAssignmentService colorAssignmentService;

    public Room(UserService userService, EntityService entityService, BroadcastService broadcastService,
            ColorAssignmentService colorAssignmentService) {
        super();
        this.userService = userService;
        this.entityService = entityService;
        this.broadcastService = broadcastService;
        this.colorAssignmentService = colorAssignmentService;
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
    
}
