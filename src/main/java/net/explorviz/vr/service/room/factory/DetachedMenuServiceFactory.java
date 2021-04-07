package net.explorviz.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.vr.service.IdGenerationService;
import net.explorviz.vr.service.Room;
import net.explorviz.vr.service.room.DetachedMenuService;

@ApplicationScoped
public class DetachedMenuServiceFactory {

	@Inject
	IdGenerationService idGenerationService;

	public DetachedMenuService makeDetachedMenuService(Room room) {
		final var grabService = room.getGrabService();
		return new DetachedMenuService(idGenerationService, grabService);
	}
}
