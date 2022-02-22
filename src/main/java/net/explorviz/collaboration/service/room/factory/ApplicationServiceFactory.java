package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.ApplicationService;

@ApplicationScoped
public class ApplicationServiceFactory {
  public ApplicationService makeApplicationService(final Room room) {
    final var grabService = room.getGrabService();
    return new ApplicationService(grabService);
  }
}
