package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;

import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.ApplicationService;

@ApplicationScoped
public class ApplicationServiceFactory {
	public ApplicationService makeApplicationService(Room room) {
		final var grabService = room.getGrabService();
		return new ApplicationService(grabService);
	}
}
