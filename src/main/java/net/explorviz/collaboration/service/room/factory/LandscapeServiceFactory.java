package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.explorviz.collaboration.service.IdGenerationService;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.LandscapeService;

@ApplicationScoped
public class LandscapeServiceFactory {
  @Inject
  /* default */IdGenerationService idGenerationService;// NOCS

  public LandscapeService makeLandscapeService(final Room room) {
    final var grabService = room.getGrabService();
    return new LandscapeService(this.idGenerationService, grabService);
  }
}
