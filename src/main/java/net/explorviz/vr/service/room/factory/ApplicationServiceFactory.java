package net.explorviz.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.vr.service.Room;
import net.explorviz.vr.service.room.ApplicationService;

@ApplicationScoped
public class ApplicationServiceFactory {
	public ApplicationService makeApplicationService(Room room) {
		final var grabService = room.getGrabService();
		return new ApplicationService(grabService);
	}
}
