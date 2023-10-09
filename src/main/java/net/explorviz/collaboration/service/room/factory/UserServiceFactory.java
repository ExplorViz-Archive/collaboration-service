package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import net.explorviz.collaboration.event.UserConnectedEvent;
import net.explorviz.collaboration.event.UserDisconnectedEvent;
import net.explorviz.collaboration.service.IdGenerationService;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.UserService;

@ApplicationScoped
public class UserServiceFactory {
  @Inject
  /* default */IdGenerationService idGenerationService; // NOCS

  @Inject
  /* default */Event<UserConnectedEvent> userConnectedEvent; // NOCS

  @Inject
  /* default */Event<UserDisconnectedEvent> userDisconnectedEvent; // NOCS

  public UserService makeUserService(final Room room) {
    final var colorAssignmentService = room.getColorAssignmentService();
    final var grabService = room.getGrabService();
    return new UserService(room, this.idGenerationService, colorAssignmentService,
        this.userConnectedEvent, this.userDisconnectedEvent, grabService);
  }

}
