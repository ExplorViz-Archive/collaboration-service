package net.explorviz.extension.vr.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import net.explorviz.extension.vr.service.IdGenerationService;
import net.explorviz.extension.vr.service.room.GrabService;
import net.explorviz.extension.vr.service.room.LandscapeService;

@ApplicationScoped
public class LandscapeServiceFactory {
    @Inject
    IdGenerationService idGenerationService;
	
	public LandscapeService makeLandscapeService(GrabService grabService) {
		return new LandscapeService(idGenerationService, grabService);
	}
}
