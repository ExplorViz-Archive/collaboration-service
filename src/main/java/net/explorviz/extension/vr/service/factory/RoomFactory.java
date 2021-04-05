package net.explorviz.extension.vr.service.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.ApplicationService;
import net.explorviz.extension.vr.service.room.BroadcastService;
import net.explorviz.extension.vr.service.room.ColorAssignmentService;
import net.explorviz.extension.vr.service.room.DetachedMenuService;
import net.explorviz.extension.vr.service.room.GrabService;
import net.explorviz.extension.vr.service.room.LandscapeService;
import net.explorviz.extension.vr.service.room.UserService;
import net.explorviz.extension.vr.service.room.factory.ApplicationServiceFactory;
import net.explorviz.extension.vr.service.room.factory.BroadcastServiceFactory;
import net.explorviz.extension.vr.service.room.factory.ColorAssignmentServiceFactory;
import net.explorviz.extension.vr.service.room.factory.DetachedMenuServiceFactory;
import net.explorviz.extension.vr.service.room.factory.GrabServiceFactory;
import net.explorviz.extension.vr.service.room.factory.LandscapeServiceFactory;
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

    public Room makeRoom(String roomId, String roomName) { 
        return new Room(roomId, roomName) {
        	private UserService userService;
        	private GrabService grabService;
        	private LandscapeService landscapeService;
        	private ApplicationService applicationService;
        	private DetachedMenuService detachedMenuService;
        	private BroadcastService broadcastService;
        	private ColorAssignmentService colorAssignmentService;

        	public UserService getUserService() {
        		if (userService == null) userService = userServiceFactory.makeUserService(this);
        		return userService;
        	}

        	public GrabService getGrabService() {
        		if (grabService == null) grabService = grabServiceFactory.makeGrabService(this);
        		return grabService;
        	}

        	public LandscapeService getLandscapeService() {
        		if (landscapeService == null) landscapeService = landscapeServiceFactory.makeLandscapeService(this);
        		return landscapeService;
        	}

        	public ApplicationService getApplicationService() {
        		if (applicationService == null) applicationService = applicationServiceFactory.makeApplicationService(this);
        		return applicationService;
        	}

        	public DetachedMenuService getDetachedMenuService() {
        		if (detachedMenuService == null) detachedMenuService = detachedMenuServiceFactory.makeDetachedMenuService(this);
        		return detachedMenuService;
        	}

        	public BroadcastService getBroadcastService() {
        		if (broadcastService == null) broadcastService = broadcastServiceFactory.makeBroadcastService(this);
        		return broadcastService;
        	}

        	public ColorAssignmentService getColorAssignmentService() {
        		if (colorAssignmentService == null) colorAssignmentService = colorAssignmentServiceFactory.makeColorAssignmentService(this);
        		return colorAssignmentService;
        	}
        };
    }
}
