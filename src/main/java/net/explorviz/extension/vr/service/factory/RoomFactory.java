package net.explorviz.extension.vr.service.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.factory.ApplicationServiceFactory;
import net.explorviz.extension.vr.service.room.factory.BroadcastServiceFactory;
import net.explorviz.extension.vr.service.room.factory.ColorAssignmentServiceFactory;
import net.explorviz.extension.vr.service.room.factory.DetachedMenuServiceFactory;
import net.explorviz.extension.vr.service.room.factory.GrabServiceFactory;
import net.explorviz.extension.vr.service.room.factory.LandscapeServiceFactory;
import net.explorviz.extension.vr.service.room.factory.SessionRegistryFactory;
import net.explorviz.extension.vr.service.room.factory.UserServiceFactory;

@ApplicationScoped
public class RoomFactory {

    @Inject
    UserServiceFactory userServiceFactory;

    @Inject
    LandscapeServiceFactory landscapeServiceFactory;

    @Inject
    ApplicationServiceFactory applicationServiceFactory;

    @Inject
    DetachedMenuServiceFactory detachedMenuServiceFactory;

    @Inject
    BroadcastServiceFactory broadcastServiceFactory;

    @Inject
    ColorAssignmentServiceFactory colorAssignmentServiceFactory;

    @Inject
    GrabServiceFactory grabServiceFactory;

    @Inject
    SessionRegistryFactory sessionRegistryFactory;

    public Room makeRoom(String roomId) {
        var grabService = grabServiceFactory.makeGrabService();
        var landscapeService = landscapeServiceFactory.makeLandscapeService(grabService);
        var applicationService = applicationServiceFactory.makeApplicationService(grabService);
        var detachedMenuService = detachedMenuServiceFactory.makeDetachedMenuService(grabService);
        var colorAssignmentService = colorAssignmentServiceFactory.makeColorAssignmentService();
        var userService = userServiceFactory.makeUserService(colorAssignmentService, grabService);
        var sessionRegistry = sessionRegistryFactory.makeSessionRegistry();
        var broadcastService = broadcastServiceFactory.makeBroadcastService(sessionRegistry);
        return new Room(roomId, userService, grabService, landscapeService, applicationService, detachedMenuService, broadcastService, colorAssignmentService, sessionRegistry);
    }

}
