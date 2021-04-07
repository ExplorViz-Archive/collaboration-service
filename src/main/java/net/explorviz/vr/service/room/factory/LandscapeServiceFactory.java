package net.explorviz.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.vr.service.IdGenerationService;
import net.explorviz.vr.service.Room;
import net.explorviz.vr.service.room.LandscapeService;

@ApplicationScoped
public class LandscapeServiceFactory {
    @Inject
    IdGenerationService idGenerationService;
	
	public LandscapeService makeLandscapeService(Room room) {
		final var grabService = room.getGrabService();
		return new LandscapeService(idGenerationService, grabService);
	}
}
