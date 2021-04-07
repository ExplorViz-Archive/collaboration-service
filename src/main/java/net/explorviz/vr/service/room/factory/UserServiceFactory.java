package net.explorviz.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import net.explorviz.vr.event.UserConnectedEvent;
import net.explorviz.vr.event.UserDisconnectedEvent;
import net.explorviz.vr.service.IdGenerationService;
import net.explorviz.vr.service.Room;
import net.explorviz.vr.service.room.UserService;

@ApplicationScoped
public class UserServiceFactory {
	@Inject
	IdGenerationService idGenerationService;

	@Inject
	Event<UserConnectedEvent> userConnectedEvent;

	@Inject
	Event<UserDisconnectedEvent> userDisconnectedEvent;

	public UserService makeUserService(Room room) {
		final var colorAssignmentService = room.getColorAssignmentService();
		final var grabService = room.getGrabService();
		return new UserService(room, idGenerationService, colorAssignmentService, userConnectedEvent,
				userDisconnectedEvent, grabService);
	}

}
