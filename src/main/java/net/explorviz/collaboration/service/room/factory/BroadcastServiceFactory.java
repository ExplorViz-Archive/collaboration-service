package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.SessionRegistry;
import net.explorviz.collaboration.service.room.BroadcastService;

@ApplicationScoped
public class BroadcastServiceFactory {

  @Inject
  /* default */ SessionRegistry sessionRegistry; // NOCS

  public BroadcastService makeBroadcastService(final Room room) {
    return new BroadcastService(this.sessionRegistry, (session) -> session.getRoom() == room);
  }
}
