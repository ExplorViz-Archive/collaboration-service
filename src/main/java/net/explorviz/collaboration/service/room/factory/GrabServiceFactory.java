package net.explorviz.collaboration.service.room.factory;

import javax.enterprise.context.ApplicationScoped;
import net.explorviz.collaboration.service.Room;
import net.explorviz.collaboration.service.room.GrabService;

@ApplicationScoped
public class GrabServiceFactory {

  public GrabService makeGrabService(final Room room) {
    return new GrabService();
  }
}
