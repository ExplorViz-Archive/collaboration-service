package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.explorviz.collaboration.service.IdGenerationService;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.DetachedMenuService;

@ApplicationScoped
public class DetachedMenuServiceFactory {

  @Inject
  /* default */ IdGenerationService idGenerationService;// NOCS

  public DetachedMenuService makeDetachedMenuService(final Room room) {
    final var grabService = room.getGrabService();
    return new DetachedMenuService(this.idGenerationService, grabService);
  }
}
