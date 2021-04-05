package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.Room;
import net.explorviz.extension.vr.service.room.LandscapeService;

@ApplicationScoped
public class LandscapeServiceFactory {
    @Inject
    IdGenerationService idGenerationService;
	
	public LandscapeService makeLandscapeService(Room room) {
		final var grabService = room.getGrabService();
		return new LandscapeService(idGenerationService, grabService);
	}
}
