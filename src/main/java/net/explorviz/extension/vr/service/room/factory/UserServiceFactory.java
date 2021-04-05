package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import net.explorviz.extension.vr.event.UserConnectedEvent;
import net.explorviz.extension.vr.event.UserDisconnectedEvent;
import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.UserService;

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
